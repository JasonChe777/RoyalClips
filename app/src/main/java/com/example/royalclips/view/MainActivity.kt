package com.example.royalclips.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.royalclips.R
import androidx.navigation.ui.AppBarConfiguration
import com.example.royalclips.databinding.ActivityMainBinding
import com.example.royalclips.model.data.login.LoginResponse
import com.example.royalclips.viewmodel.MainViewModel
import com.example.royalclips.viewmodel.SelectBarberViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }
    private fun initView() {

        binding.cvBookAppointment.setOnClickListener {
            startActivity(Intent(this@MainActivity, SelectBarberActivity::class.java))
        }
        setSupportActionBar(binding.homeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        Log.e("intent.extras", intent.extras.toString())
        var info = intent.extras?.get(LOGIN_INFO)
        if(info != null) {
            var loginInfo = info as LoginResponse
            viewModel.setUserLiveData(loginInfo)
        }
        val navView = binding.navView.inflateHeaderView(R.layout.nav_view_header)
        val tvNavHeaderMobileNo: TextView = navView.findViewById(R.id.tv_nav_header_mobileNo)
        tvNavHeaderMobileNo.text = viewModel.userLiveData.value?.mobileNo
        binding.navView.setNavigationItemSelectedListener{ menuItem->
            when(menuItem.itemId){
                R.id.navBookAppointment->{
                    //startActivity(Intent(this@MainActivity,UserLogin::class.java))
//                    Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_LONG).show()
                }

                R.id.navAbout->startActivity((Intent(this@MainActivity, AboutActivity::class.java)))
//                R.id.cart -> startActivity(Intent(this@MainActivity,CartActivity::class.java))
//                R.id.order -> Toast.makeText(this, "Orders", Toast.LENGTH_SHORT).show()
//                R.id.profile -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
//                R.id.home -> binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            true

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

    companion object {
        const val LOGIN_INFO = "login_info"
    }

}