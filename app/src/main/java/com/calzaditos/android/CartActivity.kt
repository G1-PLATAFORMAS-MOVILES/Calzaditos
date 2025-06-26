package com.calzaditos.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calzaditos.android.adapters.CartAdapter
import com.calzaditos.android.models.Product

class CartActivity : BaseActivity() {

    private lateinit var productList: List<Product>
    private var cont: Int = 0
    private var sum: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        initializeMenus()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textViewSelect = findViewById<TextView>(R.id.text_select)
        val textViewTotal = findViewById<TextView>(R.id.text_total)


        productList = listOf(
            Product("Botas Bárbara", 319.90, "https://i.postimg.cc/vH05X0GN/14-bota-marron-larga-transparente.png"),
            Product("Botines Xiara", 219.90, "https://i.postimg.cc/KvGnn6hT/12-botin-negro-transparente.png"),
            Product("Botines New York", 349.90, "https://i.postimg.cc/qqLyPLyg/12-zapatos-botin-blanco-transparente.png"),
            Product("Zapatos Viale", 249.90, "https://i.postimg.cc/0y3D1rMG/15-sandalia-negra-taco-transparente.png"),
            Product("Botines Bata", 199.90, "https://i.postimg.cc/9QzZkyMF/16-Botin-negro-taco-transparente.png"),
            Product("Zapato Mera", 199.90, "https://i.postimg.cc/wvDhVHBX/11-zapato-Flat-transparente.png") ,
        )

        textViewSelect.text = "Objetos seleccionados($cont)"
        textViewTotal.text = "S/ %.2f".format(sum)


        val recyclerView = findViewById<RecyclerView>(R.id.product_gridCar)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = CartAdapter(productList) { product, isChecked ->
            updateTotal(product, isChecked)
        }
    }

    fun irPagos (view: View) {
        val intent = Intent(this, PayActivity::class.java)
        startActivity(intent)
    }

    fun updateTotal(product: Product, isChecked: Boolean) {
        val textViewSelect = findViewById<TextView>(R.id.text_select)
        val textViewTotal = findViewById<TextView>(R.id.text_total)
        if(isChecked) {
            cont++
            sum += product.price
        } else {
            cont--
            sum -= product.price
        }
        textViewSelect.text = "Objetos seleccionados($cont)"
        textViewTotal.text = "S/ %.2f".format(sum)
    }
}


