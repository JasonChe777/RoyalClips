package com.example.royalclips.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.royalclips.databinding.ActivityResetPasswordBinding
import com.example.royalclips.viewmodel.ResetPasswordViewModel

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var viewModel: ResetPasswordViewModel
    lateinit var phone: String
    lateinit var password: String
    lateinit var confirmPassword: String
    var message = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]

        binding.btnReset.setOnClickListener {

            phone = binding.edtMobileNum.text.toString()
            password = binding.edtPassword.text.toString()
            confirmPassword = binding.edtConfirmPassword.text.toString()

            var check = true
            if (phone.length < 8) {
                Log.d("hi", "you entered phone.length<8")
                check = false
                message = "Mobile Number should have at least 8 digits"
            }
            if (phone.length > 13) {
                Log.d("hi", "you entered phone.length>13")
                check = false
                message = "Mobile Number should not have more than 13 digits"
            }
            if (password.length < 8) {
                Log.d("hi", "you entered password.length<8")
                check = false
                message = "Password should have at least 8 digits"
            }
            if (password != confirmPassword) {
                check = false
                message = "Password doesn't match."
            }
            if (check) {
                viewModel.resetPassword(
                    phone,
                    password,
                    binding.edtVerificationCode.text.toString()
                )
            } else {
                val builder = AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(message)
                    .setPositiveButton("Ok") { _, _ ->
                    }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
            }

        }


        binding.btnSendCode.setOnClickListener {
            var check = true
            phone = binding.edtMobileNum.text.toString()
            if (phone.length < 8) {
                check = false
                message = "Mobile Number should have at least 8 digits"
            }
            if (phone.length > 13) {
                check = false
                message = "Mobile Number should not have more than 13 digits"
            }
            if (check) {
                viewModel.getPhoneVerificationCode(phone)
            } else {
                val builder = AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(message)
                    .setPositiveButton("Ok") { _, _ ->
                    }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
            }
        }
        viewModel.error.observe(this) {
            val builder = AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(it)
                .setPositiveButton("Ok") { _, _ ->
                }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()
        }

        viewModel.code.observe(this) {
            val builder = AlertDialog.Builder(this)
                .setTitle("Verification Code")
                .setMessage("Your Verification code is: $it")
                .setPositiveButton("Ok") { _, _ ->
                }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()
        }

        viewModel.success.observe(this) {
            val builder = AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage(it)
                .setPositiveButton("Ok") { _, _ ->
                }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()
        }

        viewModel.goToLogin.observe(this) {
            if (it == true) {

                Handler(Looper.getMainLooper()).postDelayed(
                    {

                        startActivity(Intent(this@ResetPasswordActivity, LoginActivity::class.java))

                        finish()
                    }, 1000
                )

            }
        }
    }

}