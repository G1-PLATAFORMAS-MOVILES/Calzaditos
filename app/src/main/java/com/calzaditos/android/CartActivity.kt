package com.calzaditos.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calzaditos.android.adapters.CartAdapter
import com.calzaditos.android.models.Product
import com.calzaditos.android.services.CartService

class CartActivity : BaseActivity() {

    private lateinit var productList: ArrayList<Product>
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

        val userId = getSharedPreferences("calzaditos", MODE_PRIVATE).getInt("userId", 0)
        CartService().getCart(userId, this) { products ->
            loadProducts(products)
        }

        textViewSelect.text = "Objetos seleccionados($cont)"
        textViewTotal.text = "S/ %.2f".format(sum)
    }

    fun loadProducts(products : ArrayList<Product>) {
        productList = products
        val recyclerView = findViewById<RecyclerView>(R.id.product_gridCar)
        val userId = getSharedPreferences("calzaditos", MODE_PRIVATE).getInt("userId", 0)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = CartAdapter(
            productList,
            userId,
            { product, isChecked -> updateTotal(product, isChecked) },
            { product, quantity -> updateQuantity(product, quantity) },
            this
        )
        cont = 0
        sum = 0.0
    }

    fun irPagos (view: View) {
        val intent = Intent(this, PayActivity::class.java)
        intent.putExtra("sum", sum)
        startActivity(intent)
    }

    fun updateTotal(product: Product, isChecked: Boolean) {
        if(isChecked) {
            cont++
            sum += product.price * product.units
        } else {
            cont--
            sum -= product.price * product.units
        }
        updateViewTotals()
    }

    fun updateQuantity(product: Product, quantity: Int) {
        sum += product.price * quantity
        if(product.units == 0) {
            productList.remove(product)
            loadProducts(productList)
        }
        updateViewTotals()
    }

    fun emptyCart(view: View) {
        val userId = getSharedPreferences("calzaditos", MODE_PRIVATE).getInt("userId", 0)
        CartService().emptyCart(userId, this) { success ->
            if (success) {
                loadProducts(ArrayList<Product>())
                cont = 0
                sum = 0.0
                updateViewTotals()
            } else {
                Toast.makeText(this, "Error al vaciar el carrito", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateViewTotals(){
        val textViewSelect = findViewById<TextView>(R.id.text_select)
        val textViewTotal = findViewById<TextView>(R.id.text_total)
        textViewSelect.text = "Objetos seleccionados($cont)"
        textViewTotal.text = "S/ %.2f".format(sum)
    }
}


