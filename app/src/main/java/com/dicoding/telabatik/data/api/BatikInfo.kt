package com.dicoding.telabatik.data.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BatikInfo (
    val name: String,
    val origin: String? = null,
    val image: String
): Parcelable
