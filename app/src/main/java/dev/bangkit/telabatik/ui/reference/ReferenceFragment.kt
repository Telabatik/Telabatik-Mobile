package dev.bangkit.telabatik.ui.reference

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dev.bangkit.telabatik.data.ResultState
import dev.bangkit.telabatik.databinding.FragmentReferenceBinding
import dev.bangkit.telabatik.view.ViewModelFactory


class ReferenceFragment : Fragment() {

    private val viewModel by viewModels<ReferenceViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentReferenceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReferenceBinding.inflate(inflater, container, false)

        viewModel.getAllBatikInfo().observe(viewLifecycleOwner, { result ->
            when (result) {
                is ResultState.Loading -> {
//                    showLoading(true)
                }

                is ResultState.Success -> {
                    val batikInfo = result.data
                    binding.rvRefBatikInfo.layoutManager = GridLayoutManager(activity, 2)
                    val adapter = ReferenceBatikInfoAdapter()
                    adapter.submitList(batikInfo)
                    binding.rvRefBatikInfo.adapter = adapter
                }

                is ResultState.Error -> {
//                    showToast(result.error)
//                    showLoading(false)
                }
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
