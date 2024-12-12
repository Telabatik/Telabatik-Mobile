package com.dicoding.telabatik.data.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PredictResponse(

	@field:SerializedName("data")
	val data: PredictData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class PredictHistoryResponse(

	@field:SerializedName("data")
	val data: List<PredictData>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

@Parcelize
data class PredictData(

	@field:SerializedName("confidenceScore")
	val confidenceScore: Float,

	@field:SerializedName("predictedAt")
	val predictedAt: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("label")
	val label: String
): Parcelable
