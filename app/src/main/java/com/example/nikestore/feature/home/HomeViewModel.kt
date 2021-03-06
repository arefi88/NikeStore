package com.example.nikestore.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.common.asyncNetworkRequest
import com.example.nikestore.data.Banner
import com.example.nikestore.data.Product
import com.example.nikestore.data.SORT_LATEST
import com.example.nikestore.data.SORT_POPULAR
import com.example.nikestore.data.repo.BannerRepository
import com.example.nikestore.data.repo.ProductRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class HomeViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository):NikeViewModel() {
   private val _productsLiveData=MutableLiveData<List<Product>>()
    val productsLiveData:LiveData<List<Product>>
    get() = _productsLiveData
    val bannersLiveData=MutableLiveData<List<Banner>>()

    init {
        progressBarLiveData.value=true
        productRepository.getProducts(SORT_LATEST)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    _productsLiveData.value=t
                }

            })
        bannerRepository.getBanners()
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannersLiveData.value=t
                }

            })






    }
}