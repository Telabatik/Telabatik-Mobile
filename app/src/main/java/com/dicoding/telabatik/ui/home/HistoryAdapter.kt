package com.dicoding.telabatik.ui.home

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dicoding.telabatik.data.api.PredictData
import com.dicoding.telabatik.databinding.ItemReferensiVertBinding
import com.dicoding.telabatik.getRelativeTimeString
import com.dicoding.telabatik.view.result.BatikInfoActivity
import com.dicoding.telabatik.view.result.ResultActivity
import java.text.SimpleDateFormat


class HistoryAdapter : ListAdapter<PredictData, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReferensiVertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(private val binding: ItemReferensiVertBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(predictData: PredictData){
            binding.tvItemVertTitle.text = predictData.label
//            val date = SimpleDateFormat iso8601
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(predictData.predictedAt)
            binding.tvItemVertDescription.text = getRelativeTimeString(date)
            Glide.with(itemView.context)
                .load(predictData.imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
//                        binding.progressBarItem.visibility = View.GONE
//                        binding.ivItemVertImg.setImageResource(android.R.drawable)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
//                        binding.progressBarItem.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.ivItemVertImg)

            binding.root.layoutParams.width = toDp(128f)
            binding.ivItemVertImg.layoutParams.height = toDp(128f)
            val marginLayoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
            marginLayoutParams.marginEnd = toDp(8f)

            binding.root.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_PREDICT_DATA, predictData)

                context.startActivity(intent)
            }

        }

        private val toDp: (Float) -> Int = {
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, it, itemView.context.resources.displayMetrics).toInt()
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PredictData>() {
            override fun areItemsTheSame(oldItem: PredictData, newItem: PredictData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PredictData, newItem: PredictData): Boolean {
                return oldItem == newItem
            }
        }
    }
}

