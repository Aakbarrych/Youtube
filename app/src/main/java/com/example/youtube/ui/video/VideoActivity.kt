package com.example.youtube.ui.video

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.youtube.R
import com.example.youtube.core.network.connection.ConnectionLiveData
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityVideoBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util

class VideoActivity(override val viewModel: VideoViewModel) : BaseActivity<ActivityVideoBinding, VideoViewModel>() {

    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private lateinit var cld: ConnectionLiveData

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.exoPlayer.player = exoPlayer
                val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4))
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
            }
    }

    override fun inflateViewBinding(): ActivityVideoBinding {
        return ActivityVideoBinding.inflate(layoutInflater)
    }

    override fun isConnection() {
        super.isConnection()

        val noInternetView = binding.noInternet
        val yesInternetView = binding.videoLayout
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

    override fun initViewModel() {
        super.initViewModel()
        val getId = intent.getStringExtra(ID)
        val getTitle = intent.getStringExtra(TITLE)
        val getDesc = intent.getStringExtra(DESC)
        viewModel.getVideo(getId!!).observe(this) {
            binding.tvName.text = getTitle
            binding.tvInfoDetail.text = getDesc
        }
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.exoPlayer).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun initListener() {
        super.initListener()
        binding.toolbarBack.setOnClickListener { finish() }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    companion object {
        const val ID = "id"
        const val TITLE = "title"
        const val DESC = "desc"
    }
}