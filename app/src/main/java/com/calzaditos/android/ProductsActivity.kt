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
import com.calzaditos.android.utils.dp
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_products)
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



        val productoList = listOf(
            Product("Botas Bárbara", 319.90, "https://i.postimg.cc/vH05X0GN/14-bota-marron-larga-transparente.png"),
            Product("Botines Xiara", 219.90, "https://i.postimg.cc/KvGnn6hT/12-botin-negro-transparente.png"),
            Product("Botines New York", 349.90, "https://i.postimg.cc/qqLyPLyg/12-zapatos-botin-blanco-transparente.png"),
            Product("Zapatos Viale", 249.90, "https://i.postimg.cc/0y3D1rMG/15-sandalia-negra-taco-transparente.png"),
            Product("Botines Bata", 199.90, "https://i.postimg.cc/9QzZkyMF/16-Botin-negro-taco-transparente.png"),
            Product("Zapato Mera", 199.90, "https://i.postimg.cc/wvDhVHBX/11-zapato-Flat-transparente.png")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.product_grid)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ProductAdapter(productoList)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, ProductsActivity::class.java))
                    true
                }
                R.id.navigation_categories -> {
                    Toast.makeText(this, "Categorías por implementar", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.navigation_favorites -> {
                    Toast.makeText(this, "Favoritos por implementar", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.navigation_account -> {
                    Toast.makeText(this, "Cuenta por implementar", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

}