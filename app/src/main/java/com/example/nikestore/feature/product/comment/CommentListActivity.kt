package com.example.nikestore.feature.product.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_ID
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.data.Comment
import com.example.nikestore.feature.product.CommentAdapter
import kotlinx.android.synthetic.main.activity_comment_list.*
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {
    val viewModel:CommentListViewModel by viewModel{ parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)
        viewModel.progressBarLiveData.observe(this){mostShow->
            setProgressIndicator(mostShow)
        }
        viewModel.commentsLiveData.observe(this){
            val adapter=CommentAdapter(true)
            commentsRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            adapter.comments=it as ArrayList<Comment>
            commentsRv.adapter=adapter


        }
        commentListToolbar.onBackButtonClickListener= View.OnClickListener {
            finish()
        }
    }
}