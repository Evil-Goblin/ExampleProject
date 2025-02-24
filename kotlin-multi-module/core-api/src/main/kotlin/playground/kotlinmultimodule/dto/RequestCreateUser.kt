package playground.kotlinmultimodule.dto

data class RequestCreateUser(
    val email: String,
    val name: String,
    val password: String
)
