package com.example.farebaseauthentication.presentation.signup

data class SignUpState (
    val isLoading: Boolean=false,
    val isSuccess: String? = "",
    val isError: String? = ""
        )