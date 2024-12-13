package dev.bangkit.telabatik.ui.reference

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
import dev.bangkit.telabatik.R
import dev.bangkit.telabatik.data.api.BatikInfo
import dev.bangkit.telabatik.databinding.ItemReferensiVertBinding
import dev.bangkit.telabatik.view.result.BatikInfoActivity


class ReferenceBatikInfoAdapter : ListAdapter<BatikInfo, ReferenceBatikInfoAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReferensiVertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(private val binding: ItemReferensiVertBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(batikInfo: BatikInfo){
            binding.tvItemVertTitle.text = batikInfo.name
            binding.tvItemVertDescription.text = itemView.context.getString(R.string.batik_origin, batikInfo.origin)
            Glide.with(itemView.context)
                .load(batikInfo.image)
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

//            binding.root.layoutParams.width = toDp(128f)
            binding.ivItemVertImg.layoutParams.height = toDp(128f)
            val marginLayoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
            val gridGap = toDp(4f)
            marginLayoutParams.marginStart = gridGap
            marginLayoutParams.marginEnd = gridGap
            marginLayoutParams.topMargin = gridGap
            marginLayoutParams.bottomMargin = gridGap

            binding.root.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, BatikInfoActivity::class.java)
                intent.putExtra(BatikInfoActivity.EXTRA_BATIK_INFO, batikInfo)

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

