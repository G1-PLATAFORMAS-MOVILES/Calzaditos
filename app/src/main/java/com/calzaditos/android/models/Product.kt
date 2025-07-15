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
    var colorsHex: ArrayList<String>,
    var selectedSize: Int,
    var units: Int)
{
    constructor(
        id: Int,
        name: String,
        description: String,
        price: Double,
        brandId: Int,
        imageUrl: String,
        selectedSize: Int = 0,
        units : Int = 1,
        ) : this(
            id,
            name,
            price,
            discount = 0.0,
            description,
            imageUrl = imageUrl,
            sizes = intArrayOf(),
            brand = Brand(brandId,"Genérico", ""),
            category = "",
            colorsHex = arrayListOf(),
            selectedSize,
            units
    )
}
