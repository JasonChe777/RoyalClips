package com.example.royalclips.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.royalclips.databinding.ActivityRegisterBinding
import com.example.royalclips.model.data.register.RegisterRequest
import com.example.royalclips.viewmodel.RegisterViewModel
import com.google.firebase.messaging.FirebaseMessaging

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var fcmToken: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        setContentView(binding.root)
        setUpObserver()
        getFcmToken()
        initView()
    }

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                fcmToken = it.result
                Log.d("FCM_Token", "FCM_TOKEN: ${it.result}")
            }
        }
    }


    private fun initView() {
        binding.btnSignUp.setOnClickListener {
            val registerRequest = RegisterRequest(
                fcmToken,
                binding.edtMobileNum.text.toString(),
                binding.edtPassword.text.toString()
            )
            if (binding.edtPassword.text.toString() == binding.edtConfirmPassword.text.toString()) {
                viewModel.doRegister(registerRequest)
                Toast.makeText(this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(
                    this,
                    "Password Doesn't Match!\nPlease Try Again!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun setUpObserver() {
        viewModel.goToLogin.observe(this) {
            if (it == true) {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}