package com.example.projecthk2

object CartManager {

    val cartList = mutableListOf<Product>()

    fun addToCart(product: Product) {

        val existingProduct = cartList.find { it.id == product.id }

        if (existingProduct != null) {
            existingProduct.quantity++
        } else {

            // chưa có -> thêm mới
            val newProduct = product.copy(quantity = 1)
            cartList.add(newProduct)
        }
    }

    fun removeProduct(product: Product) {
        cartList.remove(product)
    }

    fun getCart(): MutableList<Product> {
        return cartList
    }
}