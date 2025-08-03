package com.example.mvidecomposetest.domain

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Contact(
    val id: Int = -1,
    val username: String,
    val phone: String
)
