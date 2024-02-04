package com.rowen.alocation.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rowen.alocation.R
import com.rowen.alocation.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private var mbinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(mbinding?.root)
        var actionBar = supportActionBar
        actionBar?.hide()
    }
}