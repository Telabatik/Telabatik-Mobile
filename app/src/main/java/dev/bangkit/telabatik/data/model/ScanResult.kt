package dev.bangkit.telabatik.data.model

import android.os.Parcelable
import java.util.Date
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScanResult(
    val id: Int,
    val name: String,
    val dateCreated: Date
): Parcelable
