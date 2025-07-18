package com.calzaditos.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        initializeMenus(R.id.navigation_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loggedUserName = getSharedPreferences("calzaditos", MODE_PRIVATE).getString("userName", "")
        findViewById<TextView>(R.id.tv_greeting).text = "Hola, $loggedUserName!"

    }

    fun Iradress(view: View) {
        val intent = Intent(this, AddAddressActivity::class.java)
        startActivity(intent)
    }



}