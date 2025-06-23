package com.calzaditos.android.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calzaditos.android.DetailsActivity
import com.calzaditos.android.R
import com.calzaditos.android.models.Product

class ProductAdapter(private val productos: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        val txtName: TextView = itemView.findViewById(R.id.txt_product)
        val txtPrice: TextView = itemView.findViewById(R.id.txt_price_product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
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
    }

    override fun getItemCount(): Int = productos.size
}