package com.calzaditos.android

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calzaditos.android.adapters.ProductAdapter
import com.calzaditos.android.models.Product
import com.calzaditos.android.services.ProductService

class FavoritesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorites)
        initializeMenus()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ProductService().getProducts(this) { products ->
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_favoritos)
            recyclerView.layoutManager = GridLayoutManager(this, 2)
            recyclerView.adapter = ProductAdapter(products)
        }
    }
}