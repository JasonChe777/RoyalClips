package com.example.royalclips.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}