package com.example.nikestore.data.repo.source

import com.example.nikestore.data.Product
import com.example.nikestore.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService):ProductDataSource {
    override fun getProducts(sort:Int): Single<List<Product>> = apiService.getProduct(sort.toString())
       // return apiService.getProduct()






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