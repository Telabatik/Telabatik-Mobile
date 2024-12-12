package com.dicoding.telabatik.ui.scan

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.dicoding.telabatik.data.ResultState
import com.dicoding.telabatik.databinding.FragmentScanBinding
import com.dicoding.telabatik.view.ViewModelFactory
import com.dicoding.telabatik.view.result.BatikInfoActivity
import com.dicoding.telabatik.view.result.BatikInfoActivity.Companion.EXTRA_BATIK_INFO
import com.dicoding.telabatik.view.result.ResultActivity
import com.dicoding.telabatik.view.result.ResultActivity.Companion.EXTRA_PREDICT_DATA
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ScanViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnScan.setOnClickListener {
            takePhoto()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        startCamera()
        orientationEventListener.enable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        stopCamera()
        orientationEventListener.disable()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun startCamera() {

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun stopCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll()
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        Log.d(TAG, "takePhoto: ")

        val imageCapture = imageCapture ?: return
        Log.d(TAG, "takePhoto: ${imageCapture}")

        val photoFile = createCustomTempFile(requireContext())
        Log.d(TAG, "takePhoto: ${photoFile.absolutePath}")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        Log.d(TAG, "takePhoto: ${photoFile.absolutePath}")

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Log.d(TAG, "onImageSaved: ${output.savedUri}")
                    viewModel.predict(photoFile).observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                val intent = Intent(requireContext(), ResultActivity::class.java)
                                intent.putExtra(EXTRA_PREDICT_DATA, result.data.data)
                                startActivity(intent)
//                                requireActivity().finish()
                                showToast(result.data.message)
                                showLoading(false)
//                                finish()
                            }

                            is ResultState.Error -> {
                                showToast(result.error)
                                showLoading(false)
                            }
                        }
                    }
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        requireContext(),
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }

    private val orientationEventListener by lazy {
        object : OrientationEventListener(requireContext()) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = when (orientation) {
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }

                imageCapture?.targetRotation = rotation
            }
        }
    }

    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }

    override fun onStop() {
        super.onStop()
        orientationEventListener.disable()
    }

    companion object {
        private const val TAG = "CameraActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
        private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    private fun createCustomTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        return File.createTempFile(timeStamp, ".jpg", filesDir)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbScan.visibility = View.VISIBLE
            binding.overlayView.visibility = View.VISIBLE
        } else {
            binding.pbScan.visibility = View.GONE
            binding.overlayView.visibility = View.GONE
        }
    }
}