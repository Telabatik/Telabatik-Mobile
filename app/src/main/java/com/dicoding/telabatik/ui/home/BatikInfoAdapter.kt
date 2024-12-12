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
import com.dicoding.telabatik.data.api.BatikInfo
import com.dicoding.telabatik.data.api.PredictData
import com.dicoding.telabatik.databinding.ItemReferensiVertBinding
import com.dicoding.telabatik.getRelativeTimeString
import com.dicoding.telabatik.view.result.BatikInfoActivity
import com.dicoding.telabatik.view.result.ResultActivity
import java.text.SimpleDateFormat


class BatikInfoAdapter : ListAdapter<BatikInfo, BatikInfoAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReferensiVertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(private val binding: ItemReferensiVertBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(predictData: BatikInfo){
            binding.tvItemVertTitle.text = predictData.name
            binding.tvItemVertDescription.text = "Batik dari Indonesia"
            Glide.with(itemView.context)
                .load(predictData.image)
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
                val intent = Intent(context, BatikInfoActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_PREDICT_DATA, predictData)

                context.startActivity(intent)
            }

        }

        private val toDp: (Float) -> Int = {
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, it, itemView.context.resources.displayMetrics).toInt()
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BatikInfo>() {
            override fun areItemsTheSame(oldItem: BatikInfo, newItem: BatikInfo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BatikInfo, newItem: BatikInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}

