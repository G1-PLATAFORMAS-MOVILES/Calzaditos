package com.calzaditos.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calzaditos.android.adapters.ProductAdapter
import com.calzaditos.android.models.Product

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val toolbar = findViewById<Toolbar>(R.id.include_toolbar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageView = findViewById<ImageView>(R.id.product_image)

        Glide.with(this)
            .load("https://i.postimg.cc/vH05X0GN/14-bota-marron-larga-transparente.png")
            .placeholder(R.drawable.ic_no_image)
            .error(R.drawable.ic_no_image)
            .into(imageView)

        val brandImageView = findViewById<ImageView>(R.id.brand_image)
        Glide.with(this)
            .load("https://i.postimg.cc/jdthMWPL/04-logo-vizzano-transparente.png")
            .placeholder(R.drawable.ic_no_image)
            .error(R.drawable.ic_no_image)
            .into(brandImageView)


    }


    fun returnProduct(view: View) {
        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
    }

    fun irCart(view: View) {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }




}