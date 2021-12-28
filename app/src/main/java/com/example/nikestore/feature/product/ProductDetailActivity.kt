package com.example.nikestore.feature.product

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_ID
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.common.formatPrice
import com.example.nikestore.data.Comment
import com.example.nikestore.feature.product.comment.CommentListActivity
import com.example.nikestore.services.http.ImageLoadingService
import com.example.nikestore.view.scroll.ObservableScrollViewCallbacks
import com.example.nikestore.view.scroll.ScrollState
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_product_detail.productIv
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailActivity : NikeActivity() {
    val productDetailViewModel: ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService: ImageLoadingService by inject()
    val commentAdapter = CommentAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(productIv, it.image)
            titleTv.text = it.title
            previousPriceTvDetail.text = formatPrice(it.previous_price)
            previousPriceTvDetail.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTvDetail.text = formatPrice(it.price)
            toolbarTitleTv.text = it.title

        }
        productDetailViewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }
        productDetailViewModel.commentLiveData.observe(this) {
            Timber.i(it.toString())
            commentAdapter.comments = it as ArrayList<Comment>
            if (it.size > 3) {
                viewAllCommentsBtn.visibility = View.VISIBLE
                viewAllCommentsBtn.setOnClickListener {
                    startActivity(Intent(this, CommentListActivity::class.java).apply {
                      putExtra(EXTRA_KEY_ID,productDetailViewModel.productLiveData.value!!.id)
                    })
                }
            }


        }
        initViews()
    }


    fun initViews() {
        commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        commentsRv.adapter = commentAdapter
        commentsRv.isNestedScrollingEnabled = false



        productIv.post {


            val productIvHeight = productIv.height
            val toolbar = toolbarView
            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    toolbar.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    productIv.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }

            })

        }
    }

}