package com.calzaditos.android.models

class Product(
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
    constructor(name: String, price: Double, imageUrl: String) : this(
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
