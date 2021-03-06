package com.example.nikestore

import android.app.Application
import android.os.Bundle
import com.example.nikestore.data.repo.*
import com.example.nikestore.data.repo.source.BannerRemoteDataSource
import com.example.nikestore.data.repo.source.CommentRemoteDataSource
import com.example.nikestore.data.repo.source.ProductLocalDataSource
import com.example.nikestore.data.repo.source.ProductRemoteDataSource
import com.example.nikestore.feature.common.ProductListAdapter
import com.example.nikestore.feature.product.ProductDetailViewModel
import com.example.nikestore.feature.home.HomeViewModel
import com.example.nikestore.feature.list.ProductListViewModel
import com.example.nikestore.feature.product.comment.CommentListViewModel
import com.example.nikestore.services.http.FrescoImageLoadingService
import com.example.nikestore.services.http.ImageLoadingService
import com.example.nikestore.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)
        val myModule = module {
            single { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()), ProductLocalDataSource()
                )
            }
            factory {(viewType:Int)-> ProductListAdapter(viewType,get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
        }
        startKoin {
            androidContext(this@App)
            modules(myModule)
        }

    }
}