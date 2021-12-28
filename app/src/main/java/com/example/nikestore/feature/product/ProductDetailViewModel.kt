package com.example.nikestore.feature.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.common.asyncNetworkRequest
import com.example.nikestore.data.Comment
import com.example.nikestore.data.Product
import com.example.nikestore.data.repo.CommentRepository
import com.example.nikestore.view.NikeImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(bundle: Bundle,commentRepository: CommentRepository):NikeViewModel() {
    val productLiveData=MutableLiveData<Product>()
    val commentLiveData=MutableLiveData<List<Comment>>()

    init {
        productLiveData.value=bundle.getParcelable(EXTRA_KEY_DATA)
        progressBarLiveData.value=true
        commentRepository.getAll(productLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally {
                progressBarLiveData.value=false
            }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentLiveData.value=t
                }

            })
    }

}