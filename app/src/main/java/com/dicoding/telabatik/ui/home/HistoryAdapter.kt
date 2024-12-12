package com.dicoding.telabatik.ui.home

import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginEnd
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.telabatik.data.model.ScanResult
import com.dicoding.telabatik.databinding.ItemReferensiVertBinding
import com.dicoding.telabatik.view.result.ResultActivity


class HistoryAdapter : ListAdapter<ScanResult, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReferensiVertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(private val binding: ItemReferensiVertBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scanResult: ScanResult){
            binding.tvItemVertTitle.text = scanResult.name
            binding.tvItemVertDescription.text = "1 menit yang lalu"
//            Glide.with(itemView.context)
//                .load(scanResult.photoUrl)
//                .listener(object : RequestListener<Drawable> {
//                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
//                        binding.progressBarItem.visibility = View.GONE
//                        return false
//                    }
//
//                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
//                        binding.progressBarItem.visibility = View.GONE
//                        return false
//                    }
//                })
//                .into(binding.ivItemPhoto)

            binding.root.layoutParams.width = toDp(128f)
            binding.ivItemVertImg.layoutParams.height = toDp(128f)
            val marginLayoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
            marginLayoutParams.marginEnd = toDp(8f)

            binding.root.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_STORY, scanResult)

                context.startActivity(intent)
            }

        }

        private val toDp: (Float) -> Int = {
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, it.toFloat(), itemView.context.resources.displayMetrics).toInt()
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ScanResult>() {
            override fun areItemsTheSame(oldItem: ScanResult, newItem: ScanResult): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ScanResult, newItem: ScanResult): Boolean {
                return oldItem == newItem
            }
        }
    }
}

