package com.mandalorian.replybot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import com.mandalorian.replybot.R
import com.mandalorian.replybot.service.AuthService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var auth: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if(auth.isAuthenticate()){
//            findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
//        }else{
//            findNavController(R.id.nav_host_fragment).navigate(R.id.loginFragment)
//        }
    }

}