package com.example.royalclips.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.royalclips.databinding.ActivitySelectServiceBinding

class SelectServiceActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySelectServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}