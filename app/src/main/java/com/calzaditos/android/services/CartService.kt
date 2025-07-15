package com.calzaditos.android.services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.calzaditos.android.models.Product
import org.json.JSONObject

class CartService : BaseService(){

    val resource = "Cart/"

    fun addToCart(userId: Int, productId: Int, quantity: Int, size: Int, context: Context, callback: (Boolean) -> Unit) {

        val url = "${ApiBaseURL}${resource}AddProduct"

        var addToCartJSONObject = JSONObject()
        addToCartJSONObject.put("userId", userId)
        addToCartJSONObject.put("productId", productId)
        addToCartJSONObject.put("quantity", quantity)
        addToCartJSONObject.put("size", size)

        val jsonRequest = object : JsonObjectRequest(
            Method.POST, url, addToCartJSONObject,
            Response.Listener { response ->
                callback(true)
            },
            Response.ErrorListener { error ->
                Log.e("VolleyError", error.toString())
                callback(false)
            }
        ) {}

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonRequest)
    }

    fun emptyCart(userId: Int,  context: Context, callback: (Boolean) -> Unit) {

        val url = "${ApiBaseURL}${resource}Empty?userId=${userId}"

        val jsonRequest = object : JsonObjectRequest(
            Method.POST, url, null,
            Response.Listener { response ->
                callback(true)
            },
            Response.ErrorListener { error ->
                Log.e("VolleyError", error.toString())
                callback(false)
            }
        ) {}

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonRequest)
    }

    fun getCart(userId: Int, context: Context, callback: (ArrayList<Product>) -> Unit){
        val url = "${ApiBaseURL}${resource}User/${userId}"

        val jsonRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                val cart = response.getJSONObject("data")
                val productsJson = cart.getJSONArray("products")
                val products = ArrayList<Product>()
                for (i in 0 until productsJson.length()) {
                    val productJson: JSONObject = productsJson.getJSONObject(i)
                    val product = Product(
                        productJson.getInt("id"),
                        productJson.getString("name"),
                        productJson.getString("description"),
                        productJson.getDouble("price"),
                        0,
                        productJson.getString("imageUrl"),
                        productJson.getInt("size"),
                        productJson.getInt("units")
                    )
                    products.add(product)
                }
                callback(products)
            },
            Response.ErrorListener { error ->
                Log.e("VolleyError", error.toString())
            }
        ) {}

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonRequest)
    }
}