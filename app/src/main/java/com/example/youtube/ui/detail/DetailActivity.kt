package com.example.youtube.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.youtube.R
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityContentBinding
import com.example.youtube.ui.playlist.PlaylistViewModel

class ContentActivity : BaseActivity<ActivityContentBinding, PlaylistViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
    }

    override val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this)[PlaylistViewModel::class.java]
    }

    override fun inflateViewBinding(): ActivityContentBinding {
        return ActivityContentBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        val value = intent.getStringExtra(ID)
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ID = "id"
    }

}