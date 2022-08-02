package com.example.royalclips.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.ViewModelProvider
import com.example.royalclips.databinding.ActivityLoginBinding
import com.example.royalclips.model.data.login.LoginRequest
import com.example.royalclips.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        sharedPreferences = getSharedPreferences("TOKEN_FILE", MODE_PRIVATE)
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

        viewModel.goToHomeScreen.observe(this){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }

        viewModel.loginLiveData.observe(this){
        editor.putString("TOKEN_ID", it.apiToken)
        }


        viewModel.error.observe(this){

            Snackbar.make(binding.root,it,Snackbar.LENGTH_SHORT).show()
        }
    }

}