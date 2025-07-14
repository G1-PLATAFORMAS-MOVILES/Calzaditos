package com.calzaditos.android.models

class Product(
    var id: Int,
    var name: String,
    var price: Double,
    var discount: Double,
    var description: String,
    var imageUrl: String,
    var sizes: IntArray,
    var brand: Brand,
    var category: String,
    var colorsHex: ArrayList<String>)
{
    //TODO remover cuando se haga llamada desde el API
    constructor(id: Int, name: String, price: Double, imageUrl: String) : this(
        id,
        name,
        price,
        discount = 0.0,
        description = "",
        imageUrl = imageUrl,
        sizes = intArrayOf(),
        brand = Brand("Genérico", ""),
        category = "",
        colorsHex = arrayListOf()
    )
}
