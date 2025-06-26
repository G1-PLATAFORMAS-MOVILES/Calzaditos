package com.calzaditos.android.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calzaditos.android.DetailsActivity
import com.calzaditos.android.R
import com.calzaditos.android.models.Product

class CartAdapter(
    private val productos: List<Product>,
    private val onCheckedChange: (Product, Boolean) -> Unit) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkSelect: CheckBox = itemView.findViewById(R.id.check_select)
        val imgProduct: ImageView = itemView.findViewById(R.id.img_productcar)
        val txtName: TextView = itemView.findViewById(R.id.tv_name)
        val txtSize: TextView = itemView.findViewById(R.id.tv_size)
        val txtPrice: TextView = itemView.findViewById(R.id.tv_price)
        val btnMinus: ImageButton = itemView.findViewById(R.id.btn_minus)
        val tvQuantity: TextView = itemView.findViewById(R.id.tv_quantity)
        val btnPlus: ImageButton = itemView.findViewById(R.id.btn_plus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = productos[position]
        holder.txtName.text = product.name
        holder.txtPrice.text = "S/ %.2f".format(product.price)

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_no_image)
            .error(R.drawable.ic_no_image)
            .into(holder.imgProduct)

        holder.imgProduct.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }

        holder.checkSelect.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(product, isChecked)
        }
    }

    override fun getItemCount(): Int = productos.size
}