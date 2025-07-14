package com.calzaditos.android

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calzaditos.android.adapters.ProductAdapter
import com.calzaditos.android.models.Brand
import com.calzaditos.android.models.Product
import com.calzaditos.android.services.ProductService
import com.calzaditos.android.utils.dp
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProductsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_products)
        initializeMenus()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val brandList = listOf(
            Brand("Mossa", "https://i.postimg.cc/Hsz9Rp93/03-logo-Mossa-Transparente.png"),
            Brand("Bata", "https://i.postimg.cc/pL1YVq7F/05-logo-Bata-transparente.png"),
            Brand("Vizzano", "https://i.postimg.cc/jdthMWPL/04-logo-vizzano-transparente.png"),
            Brand("Azaleia", "https://i.postimg.cc/FKdbY05n/06-Logo-Azaleia-transparente.png"),
            Brand("Viale", "https://i.postimg.cc/Hsz9Rp93/03-logo-Mossa-Transparente.png")
        )

        val container = findViewById<LinearLayout>(R.id.brand_container)

        for (brand in brandList) {
            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(80.dp, 40.dp).apply {
                    setMargins(0, 0, 16.dp, 0)
                }
                scaleType = ImageView.ScaleType.FIT_CENTER
                contentDescription = brand.name
            }

            Glide.with(this)
                .load(brand.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .error(R.drawable.ic_no_image)
                .into(imageView)

            container.addView(imageView)
        }

        ProductService().getProducts(this) { products ->
            val recyclerView = findViewById<RecyclerView>(R.id.product_grid)
            recyclerView.layoutManager = GridLayoutManager(this, 2)
            recyclerView.adapter = ProductAdapter(products)
        }
    }
}