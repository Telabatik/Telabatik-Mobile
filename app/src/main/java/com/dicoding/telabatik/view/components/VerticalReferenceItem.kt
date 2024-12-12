package com.dicoding.telabatik.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dicoding.telabatik.R
import com.google.android.material.card.MaterialCardView

class VerticalReferenceItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val titleTextView: TextView
    private val descriptionTextView: TextView
    val imageView: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_referensi_vert, this, true)

        titleTextView = findViewById(R.id.tv_item_vert_title)
        descriptionTextView = findViewById(R.id.tv_item_vert_description)
        imageView = findViewById(R.id.iv_item_vert_img)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ReferenceItem,
            0, 0
        ).apply {
            try {
                val titleText = getString(R.styleable.ReferenceItem_titleText)
                val descriptionText = getString(R.styleable.ReferenceItem_descriptionText)
                val imageResource = getResourceId(R.styleable.ReferenceItem_imageResource, R.drawable.ic_placeholder)

                setTitleText(titleText)
                setDescriptionText(descriptionText)
                setImageResource(imageResource)
            } finally {
                recycle()
            }
        }
    }

    fun setTitleText(text: String?) {
        if (text.isNullOrEmpty()) {
            titleTextView.visibility = GONE
            return
        }
        titleTextView.text = text
    }

    fun setDescriptionText(text: String?) {
        if (text.isNullOrEmpty()) {
            descriptionTextView.visibility = GONE
            return
        }
        descriptionTextView.text = text
    }

    fun setImageResource(resId: Int) {
        imageView.setImageResource(resId)
    }

}