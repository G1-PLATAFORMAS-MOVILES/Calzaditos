package com.calzaditos.android

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.calzaditos.android.services.CartService
import com.calzaditos.android.services.ProductService
import com.calzaditos.android.utils.dp
import com.google.android.material.button.MaterialButton

class DetailsActivity : BaseActivity() {
    var selectedSize : Int = 35
    var productId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        initializeMenus()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        productId = intent.getIntExtra("productId", 0)

        ProductService().getProduct(productId, this) { product ->
            findViewById<TextView>(R.id.txtName).text = product.name
            findViewById<TextView>(R.id.txtPrice).text = "Precio : S/ %.2f".format(product.price)
            findViewById<TextView>(R.id.txtDescription).text = product.description
            val imageView = findViewById<ImageView>(R.id.product_image)

            Glide.with(this)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .error(R.drawable.ic_no_image)
                .into(imageView)
        }

        val brandImageView = findViewById<ImageView>(R.id.brand_image)
        Glide.with(this)
            .load("https://i.postimg.cc/jdthMWPL/04-logo-vizzano-transparente.png")
            .placeholder(R.drawable.ic_no_image)
            .error(R.drawable.ic_no_image)
            .into(brandImageView)

        val sizeLayout = findViewById<LinearLayout>(R.id.size_container)

        val buttons = mutableListOf<MaterialButton>()
        val sizes = 35..40

        for (i in sizes) {
            val button = MaterialButton(this).apply {
                text = i.toString()
                layoutParams = LinearLayout.LayoutParams(55.dp, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    setMargins(8.dp, 0, 8.dp, 0)
                }
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black))
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }

            button.setOnClickListener {
                buttons.forEach {
                    it.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
                    it.setTextColor(ContextCompat.getColor(this, R.color.white))
                }
                button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray))
                button.setTextColor(ContextCompat.getColor(this, R.color.black))
                selectedSize = i
            }

            buttons.add(button)
            sizeLayout.addView(button)
        }
    }

    fun addToCart(view: View) {
        val productId = intent.getIntExtra("productId", 0)
        val userId = getSharedPreferences("calzaditos", MODE_PRIVATE).getInt("userId", 0)
        CartService().addToCart(userId, productId, 1, selectedSize, this) { success ->
            if (success) {
                Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al agregar producto al carrito", Toast.LENGTH_SHORT).show()
            }
        }
    }
}