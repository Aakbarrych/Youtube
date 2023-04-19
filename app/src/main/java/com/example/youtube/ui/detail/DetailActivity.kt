package com.example.youtube.ui.detail

import Item
import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.core.network.connection.ConnectionLiveData
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityDetailBinding
import com.example.youtube.ui.detail.adapter.DetailAdapter
import com.example.youtube.ui.video.VideoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {
    private lateinit var adapter: DetailAdapter
    private lateinit var cld: ConnectionLiveData

    override val viewModel: DetailViewModel by viewModel()


    override fun inflateViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun isConnection() {
        super.isConnection()

        val noInternetView = binding.noInternet
        val yesInternetView = binding.detailLayout
        cld = ConnectionLiveData(application)

        cld.observe(this) { isConnected ->
            if (isConnected) {
                noInternetView.root.visibility = View.GONE
                yesInternetView.visibility = View.VISIBLE
            } else {
                noInternetView.root.visibility = View.VISIBLE
                yesInternetView.visibility = View.GONE
            }
        }
    }

    override fun initViews() {
        super.initViews()
        adapter = DetailAdapter(this::onItemClick)
        binding.rvDetailList.layoutManager = LinearLayoutManager(this)
        binding.rvDetailList.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    override fun initViewModel() {
        super.initViewModel()
        intent.getStringExtra(ID)
        val getId = intent.getStringExtra(ID)
        val getTitle = intent.getStringExtra(TITLE)
        val getDesc = intent.getStringExtra(DESC)
        val getCount = intent.getIntExtra(COUNT ,0)
        viewModel.getPlaylistItems(getId.toString()).observe(this) {
            it.data?.let { it1 -> adapter.addData(it1.items) }
            binding.tvTitle.text = getTitle
            binding.tvDesc.text = getDesc
            binding.tvVideoCount.text = "$getCount video series"
        }
    }

    override fun initListener() {
        super.initListener()
        binding.toolbarBack.setOnClickListener {
            finish()
        }
    }

    private fun onItemClick(item: Item){
        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra("id", item.id)
        intent.putExtra("title", item.snippet.title)
        intent.putExtra("desc", item.snippet.description)
        startActivity(intent)
    }

    companion object {
        const val ID = "id"
        const val TITLE = "title"
        const val DESC = "desc"
        const val COUNT = "count"
    }

}