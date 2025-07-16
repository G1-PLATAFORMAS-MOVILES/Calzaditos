package com.calzaditos.android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.widget.EditText
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

    var filteredProducts : ArrayList<Product> = ArrayList()
    var allProducts : ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_products)
        initializeMenus(R.id.navigation_categories)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val brandList = listOf(
            Brand(1,"Mossa", "https://i.postimg.cc/Hsz9Rp93/03-logo-Mossa-Transparente.png"),
            Brand(2,"Bata", "https://i.postimg.cc/pL1YVq7F/05-logo-Bata-transparente.png"),
            Brand(3,"Vizzano", "https://i.postimg.cc/jdthMWPL/04-logo-vizzano-transparente.png"),
            Brand(4,"Azaleia", "https://i.postimg.cc/FKdbY05n/06-Logo-Azaleia-transparente.png"),
            Brand(5,"Viale", "https://images.falabella.com/v3/assets/bltf4ed0b9a176c126e/blt2e2c0b957206d8ec/683da8a13bb8996293a3f720/Filtro-brand-viale-logo.png")
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

            imageView.setOnClickListener {
                filterProducts(brand.id)
                loadProducts(filteredProducts)
            }

            container.addView(imageView)
        }

        ProductService().getProducts(this) { products ->
            allProducts = products
            loadProducts(products)
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

        val categorySandalias = findViewById<LinearLayout>(R.id.category_sandalias)
        categorySandalias.setOnClickListener {
            filterAndUpdateProducts("Sandalia")
        }

        val categoryZapatillas = findViewById<LinearLayout>(R.id.category_zapatillas)
        categoryZapatillas.setOnClickListener {
            filterAndUpdateProducts("Zapatilla")
        }

        val categoryBotas = findViewById<LinearLayout>(R.id.category_botas)
        categoryBotas.setOnClickListener {
            filterAndUpdateProducts("Bota")
        }

        val categoryZapatos = findViewById<LinearLayout>(R.id.category_zapatos)
        categoryZapatos.setOnClickListener {
            filterAndUpdateProducts("Zapato")
        }

    }

    fun loadProducts(products : ArrayList<Product>) {
        val recyclerView = findViewById<RecyclerView>(R.id.product_grid)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ProductAdapter(products)
    }

    fun filterProducts(brandId : Int){
        filteredProducts.clear()
        for (product in allProducts) {
            if (product.brand.id == brandId) {
                filteredProducts.add(product)
            }
        }
    }

    fun filterAndUpdateProducts(query: String) {
        val filteredProducts = allProducts.filter { it.name.contains(query, ignoreCase = true) }
        val recyclerView = findViewById<RecyclerView>(R.id.product_grid)
        recyclerView.adapter = ProductAdapter(filteredProducts)
    }
}