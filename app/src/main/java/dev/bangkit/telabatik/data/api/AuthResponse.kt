package dev.bangkit.telabatik.data.api

import com.google.gson.annotations.SerializedName

data class LoginResponse2(

	@field:SerializedName("data")
	val data: LoginData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class LoginData(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("token")
	val token: String
)

data class RegisterResponse(

	@field:SerializedName("data")
	val data: RegisterData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class RegisterData(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
