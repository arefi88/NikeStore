package com.example.nikestore.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.R
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.common.asyncNetworkRequest
import com.example.nikestore.data.Product
import com.example.nikestore.data.repo.ProductRepository

class ProductListViewModel(var sort:Int,val productRepository: ProductRepository) : NikeViewModel() {
    val productLiveData=MutableLiveData<List<Product>>()
    val selectedSortLiveData=MutableLiveData<Int>()
    val sortTitles= arrayOf(R.string.sortLatest,R.string.sortPopular,R.string.sortPriceHighToLow,R.string.sortPriceLowToHigh)

    init {
        getProduct()
        selectedSortLiveData.value= sortTitles[sort]
    }
    fun getProduct(){
        progressBarLiveData.value=true
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value=t
                }


            })
    }
    fun onSelectedSortChangeByUser(sort:Int){
        this.sort=sort
        this.selectedSortLiveData.value=sortTitles[sort]
        getProduct()
    }
}