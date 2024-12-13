package dev.bangkit.telabatik.data.api

import com.google.gson.annotations.SerializedName

data class GenericResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
