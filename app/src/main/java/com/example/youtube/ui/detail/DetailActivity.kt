package com.example.youtube.ui.detail

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.core.network.connection.ConnectionLiveData
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityDetailBinding
import com.example.youtube.ui.detail.adapter.DetailAdapter

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    private lateinit var adapter: DetailAdapter

    private lateinit var cld: ConnectionLiveData

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
        adapter = DetailAdapter()
        binding.rvDetailList.layoutManager = LinearLayoutManager(this)
        binding.rvDetailList.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    override fun initViewModel() {
        super.initViewModel()
        intent.getStringExtra(ID)
        val getId = intent.getStringExtra("id")
        val getTitle = intent.getStringExtra("title")
        val getDesc = intent.getStringExtra("desc")
        val getCount = intent.getIntExtra("count" ,0)
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

    override val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this)[DetailViewModel::class.java]
    }

    companion object {
        const val ID = "id"
    }

}