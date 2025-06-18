package com.example.fitsphere.screen.videoplayer_activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.common.Constant.EXTRA_TUTORIAL
import com.example.domain.model.PlaybackState
import com.example.domain.model.Tutorial
import com.example.domain.model.VideoProgress
import com.example.fitsphere.R
import com.example.fitsphere.databinding.ActivityVideoPlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint

class VideoPlayerActivity : AppCompatActivity() {
    private var _binding : ActivityVideoPlayerBinding? = null
    val binding get() = _binding!!
    private lateinit var youTubePlayer: YouTubePlayer
    private var isPlayerReady = false
    private var videoDuration: Float = 0f
    private var pendingVideoId: String? = null // Store video ID until player is ready


    private val viewModel by viewModels<VideoplayerViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tutorial = intent.getSerializableExtra(EXTRA_TUTORIAL) as? Tutorial

        tutorial?.let {
            setupUI(it)
            initializePlayer()
            observeViewModel()
            viewModel.loadVideo(it)
        }

    }
    override fun onBackPressed() {
        viewModel.onBackPressed()
        super.onBackPressed()
    }
    override fun onPause() {
        super.onPause()
        // Save progress when activity is paused
        viewModel.onPlayerStateChanged(PlaybackState.Paused)
    }
    override fun onDestroy() {
        // Save progress before destroying
        if (isFinishing) {
            viewModel.onBackPressed()
        }
        super.onDestroy()
    }
    private fun setupUI(tutorial: Tutorial) {
        binding.toolbarTitle.text = tutorial.title
        binding.buttonBack.setOnClickListener { finish() }
    }
    fun initializePlayer(){
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                Log.d("VideoPlayer", "YouTube player is ready!")
                this@VideoPlayerActivity.youTubePlayer = youTubePlayer
                isPlayerReady = true

                // Debug: Log what we're trying to load
                viewModel.uiState.value.videoId?.let { videoId ->
                    Log.d("VideoPlayer", "Attempting to load video ID: '$videoId'")
                    Log.d("VideoPlayer", "Video ID length: ${videoId.length}")
                    Log.d("VideoPlayer", "Video ID characters: ${videoId.toCharArray().joinToString(",")}")

                    // Only load if it's a valid YouTube video ID
                    if (videoId.matches(Regex("^[a-zA-Z0-9_-]{11}$"))) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    } else {
                        Log.e("VideoPlayer", "Invalid video ID format: $videoId")
                    }
                }
            }
            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                when (state) {
                    PlayerConstants.PlayerState.PLAYING -> viewModel.onPlayerStateChanged(PlaybackState.Playing)
                    PlayerConstants.PlayerState.PAUSED -> viewModel.onPlayerStateChanged(PlaybackState.Paused)
                    PlayerConstants.PlayerState.ENDED -> viewModel.onPlayerStateChanged(PlaybackState.Ended)
                    PlayerConstants.PlayerState.BUFFERING -> viewModel.onPlayerStateChanged(PlaybackState.Buffering)
                    else -> {}
                }
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                videoDuration = duration
                Log.d("VideoPlayer", "Video duration: $duration")
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                viewModel.onProgressUpdate(second.toLong(), videoDuration.toLong())
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                Log.e("VideoPlayer", "YouTube Player Error: $error")
            }
        })
    }
    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }
    private var lastLoadedVideoId: String? = null

    private fun updateUI(state: VideoPlayerUiState) {
        if (isPlayerReady && state.videoId != null && state.videoId != lastLoadedVideoId) {
            Log.d("VideoPlayer", "Loading video: ${state.videoId}")
            youTubePlayer.loadVideo(state.videoId, state.shouldSeekTo.toFloat())
            lastLoadedVideoId = state.videoId // avoid reloading
        }

        if (state.showResumeDialog) {
            showResumeDialog(state.savedProgress)
        }
    }

    private fun showResumeDialog(progress: VideoProgress?) {
        progress?.let {
            AlertDialog.Builder(this)
                .setTitle("Resume Video")
                .setMessage("Resume from ${formatTime(it.watchedTill)} or start from beginning?")
                .setPositiveButton("Resume") { _, _ ->
                    viewModel.onResumeFromLastPosition()
                }
                .setNegativeButton("Start Over") { _, _ ->
                    viewModel.onStartFromBeginning()
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    private fun formatProgress(current: Long, total: Long): String {
        return "${formatTime(current)} / ${formatTime(total)}"
    }
    private fun loadVideoInPlayer(videoId: String, startSeconds: Float) {
        if (::youTubePlayer.isInitialized) {
            Log.d("VideoPlayer", "Actually loading video: $videoId at $startSeconds seconds")
            youTubePlayer.loadVideo(videoId, startSeconds)
        }
    }
}