package com.example.common.youtubePlayer

sealed class PlayerEvent {
    object Play : PlayerEvent()
    object Pause : PlayerEvent()
    data class SeekTo(val timeSeconds: Float) : PlayerEvent()
    data class ChangeQuality(val quality: VideoQuality) : PlayerEvent()
    object ToggleFullscreen : PlayerEvent()
}
