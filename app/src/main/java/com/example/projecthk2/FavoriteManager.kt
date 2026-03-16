package com.example.projecthk2

object FavoriteManager {

    private val favoriteList = mutableListOf<Product>()

    fun toggleFavorite(product: Product) {
        if (favoriteList.contains(product)) {
            favoriteList.remove(product)
        } else {
            favoriteList.add(product)
        }
    }

    fun isFavorite(product: Product): Boolean {
        return favoriteList.contains(product)
    }

    fun getFavorites(): List<Product> {
        return favoriteList
    }
}