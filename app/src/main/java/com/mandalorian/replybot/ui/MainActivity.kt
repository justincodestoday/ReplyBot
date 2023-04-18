package com.mandalorian.replybot.ui

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.mandalorian.replybot.R
import com.mandalorian.replybot.service.AuthService
import com.mandalorian.replybot.service.NotificationService
import com.mandalorian.replybot.utils.Constants
import com.mandalorian.replybot.utils.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private val NOTIFICATION_REQ_CODE = 0
    private val FOREGROUND_REQ_CODE = 1

    @Inject
    lateinit var auth: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavController()
//        checkLoginStatus()

        NotificationUtils.createNotificationChannel(this)
        checkPermission("android.permission.POST_NOTIFICATIONS", NOTIFICATION_REQ_CODE)
        checkPermission("android.permission.FOREGROUND_SERVICE", FOREGROUND_REQ_CODE)

        val btnActivate = findViewById<MaterialButton>(R.id.btnActivate)
        btnActivate.setOnClickListener {
            startService()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        val btnDeactivate = findViewById<MaterialButton>(R.id.btnDeactivate)
        btnDeactivate.setOnClickListener {
            stopService()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        val btnLogout = findViewById<MaterialButton>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            logout()
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onNavigateUp()
    }

    private fun setupNavController() {
        navController = findNavController(R.id.nav_host_fragment)
        navigationView = findViewById(R.id.navigation_view)
        NavigationUI.setupWithNavController(navigationView, navController)
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
//            .setOpenableLayout(drawerLayout)
            .build()
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            Log.d(Constants.DEBUG, "Permission is granted already")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            NOTIFICATION_REQ_CODE -> {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
            }
            FOREGROUND_REQ_CODE -> {
                Toast.makeText(this, "Foreground service permission is granted", Toast.LENGTH_LONG)
                    .show()
            }
            else -> {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkLoginStatus() {
        if (auth.isAuthenticate()) {
            navController.navigate(R.id.toHomeFragment)
        } else {
            navController.navigate(R.id.toLoginFragment)
        }
    }

    private fun startService() {
        NotificationService.start()
    }

    private fun stopService() {
        NotificationService.stop()
    }

    private fun logout() {
        auth.deAuthenticate()
        navController.navigate(R.id.toLoginFragment)
    }
}