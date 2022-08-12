package com.example.royalclips.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.royalclips.databinding.ActivityLoginBinding
import com.example.royalclips.model.data.login.LoginRequest
import com.example.royalclips.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        setContentView(binding.root)
        setUpObserver()
        initView()


    }

    private fun initView() {


        binding.btnSignIn.setOnClickListener {
            val loginRequest = LoginRequest(
                binding.edtMobileNum.text.toString(), binding.edtPassword.text.toString()
            )
            viewModel.doLogin(loginRequest)

        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun setUpObserver() {

        viewModel.userLiveData.observe(this) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if (it.isSuccessful) {
                    viewModel.updateFcmToken(it.result)
                }
            }
        }

        viewModel.goToHomeScreen.observe(this) {
            if (it != null) {
                editor.putInt(USER_ID, it.userId)
                editor.putString(API_TOKEN, it.apiToken)
                editor.putString(MOBILE_NO,it.mobileNo)
                editor.commit()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }

        }




        viewModel.error.observe(this) {

            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val FILE_NAME = "Account Details"
        const val USER_ID = "USER ID"
        const val API_TOKEN = "API TOKEN"
        const val MOBILE_NO = "MOBILE_NO"
    }

}