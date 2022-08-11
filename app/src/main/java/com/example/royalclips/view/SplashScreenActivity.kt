package com.example.royalclips.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.royalclips.R
import com.example.royalclips.view.LoginActivity.Companion.FILE_NAME
import com.example.royalclips.view.LoginActivity.Companion.USER_ID

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
                if (sharedPreferences.contains(USER_ID)) {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                }
                finish()
            }, 2000
        )
    }
}