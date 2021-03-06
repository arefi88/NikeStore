package com.example.nikestore.data.repo.source

import com.example.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {
    fun getProducts(Sort:Int): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorite(): Completable

    fun deleteFromFavorite(): Completable
}