package com.calzaditos.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calzaditos.android.models.Product
import com.calzaditos.android.services.CartService

class PayActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pay)
        initializeMenus()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sum = intent.getDoubleExtra("sum", 0.0)
        val textViewTotal = findViewById<TextView>(R.id.text_total)
        textViewTotal.text = "S/ %.2f".format(sum)
        val textViewSubTotal = findViewById<TextView>(R.id.text_subtotal)
        textViewSubTotal.text = "S/ %.2f".format(sum)
    }

    fun payOrder(view: View){
        val userId = getSharedPreferences("calzaditos", MODE_PRIVATE).getInt("userId", 0)
        CartService().emptyCart(userId, this) { success ->
            if (success) {
                Toast.makeText(this, "Pago realizado con éxito", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error al vaciar el carrito", Toast.LENGTH_SHORT).show()
            }
        }
    }
}