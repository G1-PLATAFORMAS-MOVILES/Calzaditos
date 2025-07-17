package com.calzaditos.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calzaditos.android.models.Product
import com.calzaditos.android.services.CartService

class PayActivity : BaseActivity() {
    var sum = 0.0
    var total = 0.0

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

        sum = intent.getDoubleExtra("sum", 0.0)
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
                val intent = Intent(this, OrderConfirmedActivity::class.java)
                intent.putExtra("total", total)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error al vaciar el carrito", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun applyCoupon(view: View) {
        val coupon = findViewById<TextView>(R.id.cupon).text.toString()
        CartService().getCoupon(coupon, this) { discount ->
            val textViewDiscount = findViewById<TextView>(R.id.txt_discount)
            val layoutDiscount = findViewById<LinearLayout>(R.id.discount_layout)
            if(discount != null) {
                total = sum*(1-(discount/100))
                textViewDiscount.text = "- S/ %.2f".format(sum*(discount/100))
                layoutDiscount.visibility = View.VISIBLE
                val textViewTotal = findViewById<TextView>(R.id.text_total)
                textViewTotal.text = "S/ %.2f".format(sum*(1-(discount/100)))
            }
            else {
                total = sum
                layoutDiscount.visibility = View.GONE
                val textViewTotal = findViewById<TextView>(R.id.text_total)
                textViewTotal.text = "S/ %.2f".format(sum)
                Toast.makeText(this, "Cupón no válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}