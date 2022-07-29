package com.example.royalclips.model.data.login

data class LoginResponse(
    val apiToken: String,
    val createdOn: String,
    val dateOfBirth: String,
    val deletedOn: String,
    val emailId: String,
    val emailVerificationCode: String,
    val evcExpiresOn: String,
    val fcmToken: String,
    val fullName: String,
    val gender: String,
    val ipAddress: String,
    val isActive: Int,
    val isDeleted: Int,
    val isEmailVerified: Int,
    val isMobileVerified: Int,
    val message: String,
    val mobileNo: String,
    val password: String,
    val profilePic: String,
    val status: Int,
    val tokenValidUpTo: String,
    val updatedOn: String,
    val userId: Int
)