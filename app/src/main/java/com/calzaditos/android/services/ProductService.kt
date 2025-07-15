package com.calzaditos.android.services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.calzaditos.android.models.Product
import org.json.JSONObject

class ProductService : BaseService() {

    val resource = "Product/"

    fun getProducts(context: Context, callback: (ArrayList<Product>) -> Unit) {
        val url = "${ApiBaseURL}${resource}List"

        var jsonRequest = object : JsonObjectRequest (Method.GET, url, null,
            Response.Listener { response ->
                val productsJson = response.getJSONArray("data")
                val products = ArrayList<Product>()
                for (i in 0 until productsJson.length()) {
                    val productJson: JSONObject = productsJson.getJSONObject(i)
                    val product = Product(
                        productJson.getInt("id"),
                        productJson.getString("name"),
                        productJson.getString("description"),
                        productJson.getDouble("price"),
                        productJson.getInt("brandId"),
                        productJson.getString("imageUrl")
                    )
                    products.add(product)
                }
                callback(products)
            },
            Response.ErrorListener {
                Log.e("VolleyError", it.toString())
            }
        ) {}

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonRequest)
    }

    fun getProduct(id: Int, context: Context, callback: (Product) -> Unit) {
        val url = "${ApiBaseURL}${resource}${id}"

        var jsonRequest = object : JsonObjectRequest (Method.GET, url, null,
            Response.Listener { response ->
                val productJson = response.getJSONObject("data")
                val product = Product(
                    productJson.getInt("id"),
                    productJson.getString("name"),
                    productJson.getString("description"),
                    productJson.getDouble("price"),
                    productJson.getInt("brandId"),
                    productJson.getString("imageUrl")
                )
                callback(product)
            },
            Response.ErrorListener {
                Log.e("VolleyError", it.toString())
            }
        ) {}

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonRequest)
    }
}