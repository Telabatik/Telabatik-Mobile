package dev.bangkit.telabatik.data.pref

data class UserModel(
    val email: String,
    val username: String,
    val token: String,
    val isLogin: Boolean = false
)