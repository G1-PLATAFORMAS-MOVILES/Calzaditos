package com.calzaditos.android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calzaditos.android.adapters.ProductAdapter
import com.calzaditos.android.models.Product
import com.calzaditos.android.services.ProductService

class HomeActivity : BaseActivity() {

    var allProducts: List<Product> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        initializeMenus()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ProductService().getProducts(this) { products ->
            allProducts = products
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_destacados)
            recyclerView.layoutManager = GridLayoutManager(this, 2)
            recyclerView.adapter = ProductAdapter(products)
        }

        var searchBar = findViewById<EditText>(R.id.search_bar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                filterAndUpdateProducts(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })
    }

    fun filterAndUpdateProducts(query: String) {
        val filteredProducts = allProducts.filter { it.name.contains(query, ignoreCase = true) }
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_destacados)
        recyclerView.adapter = ProductAdapter(filteredProducts)
    }

    // ir hacia products
    fun irProducts(view: View) {
        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
    }

}