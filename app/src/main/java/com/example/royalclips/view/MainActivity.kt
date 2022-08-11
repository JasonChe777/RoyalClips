package com.example.royalclips.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.royalclips.BuildConfig
import com.example.royalclips.R
import com.example.royalclips.databinding.ActivityMainBinding
import com.example.royalclips.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var apiToken: String
    private lateinit var mobileNo: String
    private var userId: Int = 0
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(LoginActivity.FILE_NAME, MODE_PRIVATE)
        apiToken = sharedPreferences.getString(LoginActivity.API_TOKEN, "") ?: ""
        mobileNo = sharedPreferences.getString(LoginActivity.MOBILE_NO, "") ?: ""
        userId = sharedPreferences.getInt(LoginActivity.USER_ID, 0)

        initView()
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun initView() {



        binding.cvBookAppointment.setOnClickListener {
            startActivity(Intent(this@MainActivity, SelectBarberActivity::class.java))
        }

        binding.cvService.setOnClickListener {
            startActivity(Intent(this@MainActivity, ServiceCategoryActivity::class.java))
        }

        binding.cvExploreMore.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        setSupportActionBar(binding.homeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val navView = binding.navView.inflateHeaderView(R.layout.nav_view_header)
        val tvNavHeaderMobileNo: TextView = navView.findViewById(R.id.tv_nav_header_mobileNo)
        tvNavHeaderMobileNo.text = mobileNo
        val map = HashMap<String, Any>()
        map["userId"] = userId
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navBookAppointment -> startActivity(
                    Intent(
                        this@MainActivity,
                        SelectBarberActivity::class.java
                    )
                )

                R.id.navServices -> startActivity(
                    Intent(
                        this@MainActivity,
                        ServiceCategoryActivity::class.java
                    )
                )


                R.id.navAbout -> startActivity(
                    (Intent(
                        this@MainActivity,
                        AboutActivity::class.java
                    ))
                )

                R.id.navLogout -> {
                    viewModel.logout(apiToken, map)
                }

                R.id.navMyAppointment->{
                    startActivity(Intent(this@MainActivity, MyAppointmentActivity::class.java))
                }

                R.id.navWorkingHours->{
                    startActivity(Intent(this@MainActivity, WorkingHourActivity::class.java))
                }

                R.id.navShare->{
                    val appId = BuildConfig.APPLICATION_ID
                    val text = "https://play.google.com/store/apps/details?id=$appId"
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share the app!")
                    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
                    startActivity(shareIntent)
                }

                R.id.navContact->{
                    startActivity(Intent(this@MainActivity, ReachActivity::class.java))
                }

            }
            true

        }

        viewModel.logoutResponse.observe(this) {
            if (it.status == 0) {
                sharedPreferences.edit().clear().apply()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            } else {
                Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.getDashboard()

        viewModel.dashboardLiveData.observe(this){
            binding.tvShopStatus.text = "Shop ${it.isShopOpened}"
        }

        viewModel.loadingLiveData.observe(this){
            binding.progressBar.isVisible = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {

            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

}