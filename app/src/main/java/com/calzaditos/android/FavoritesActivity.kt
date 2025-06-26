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

        val productoList = listOf(
            Product("Botas Bárbara", 319.90, "https://i.postimg.cc/vH05X0GN/14-bota-marron-larga-transparente.png"),
            Product("Botines Xiara", 219.90, "https://i.postimg.cc/KvGnn6hT/12-botin-negro-transparente.png"),
            Product("Botines New York", 349.90, "https://i.postimg.cc/qqLyPLyg/12-zapatos-botin-blanco-transparente.png"),
            Product("Zapatos Viale", 249.90, "https://i.postimg.cc/0y3D1rMG/15-sandalia-negra-taco-transparente.png"),
            Product("Botines Bata", 199.90, "https://i.postimg.cc/9QzZkyMF/16-Botin-negro-taco-transparente.png"),
            Product("Zapato Mera", 199.90, "https://i.postimg.cc/wvDhVHBX/11-zapato-Flat-transparente.png")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_favoritos)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ProductAdapter(productoList)
    }
}