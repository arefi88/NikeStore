package com.example.nikestore.data.repo

import com.example.nikestore.data.Product
import com.example.nikestore.data.repo.source.ProductDataSource
import com.example.nikestore.data.repo.source.ProductLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(val remoteDataSource:ProductDataSource,
val localDataSource: ProductLocalDataSource):ProductRepository {
    override fun getProducts(sort:Int): Single<List<Product>> {
        return remoteDataSource.getProducts(sort)
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