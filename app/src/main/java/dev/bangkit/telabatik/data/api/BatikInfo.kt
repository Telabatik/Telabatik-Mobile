package dev.bangkit.telabatik.data.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BatikInfo (
    val name: String,
    val origin: String? = "Indonesia",
    val image: String
): Parcelable
