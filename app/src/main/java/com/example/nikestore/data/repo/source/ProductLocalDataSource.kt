package com.example.nikestore.data.repo.source

import com.example.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductLocalDataSource:ProductDataSource {
    override fun getProducts(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorite(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(): Completable {
        TODO("Not yet implemented")
    }
}