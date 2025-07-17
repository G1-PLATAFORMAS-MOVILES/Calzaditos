package com.calzaditos.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderConfirmedActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_confirmed)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sum = intent.getDoubleExtra("total", 0.0)
        val textViewTotal = findViewById<TextView>(R.id.tv_total_paid)
        textViewTotal.text = "S/ %.2f".format(sum)

    }
    fun irHome (view: View) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }


}