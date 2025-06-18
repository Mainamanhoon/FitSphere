package com.example.fitsphere.screen.videoplayer_activity

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.common.AppViewModel
import com.example.domain.model.Tutorial
import com.example.domain.model.VideoProgress
import com.example.domain.use_cases.ClearVideoProgressUseCase
import com.example.domain.use_cases.GetVideoProgressUseCase
import com.example.domain.use_cases.SaveVideoProgressUseCase
import com.example.domain.use_cases.StreamVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.domain.model.PlaybackState
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoplayerViewModel @Inject constructor(
    private val streamVideoUseCase: StreamVideoUseCase,
    private val saveProgressUseCase: SaveVideoProgressUseCase,
    private val getProgressUseCase: GetVideoProgressUseCase,
    private val clearProgressUseCase: ClearVideoProgressUseCase
):AppViewModel() {

    private val _uiState = MutableStateFlow(VideoPlayerUiState())
    val uiState : StateFlow<VideoPlayerUiState> = _uiState.asStateFlow()

    private var currentTutorial : Tutorial? = null

    fun loadVideo(tutorial:Tutorial){
        currentTutorial = tutorial

         val fullUrl = when {
            tutorial.link.isNullOrEmpty() -> " "
            tutorial.link!!.startsWith("http") -> tutorial.link // Already has prefix
            tutorial.link!!.length == 11 -> "https://youtu.be/${tutorial.link}" // Just video ID
            else -> tutorial.link // Handle other cases
        }

        val videoId = streamVideoUseCase.extractVideoId(fullUrl!!)

        Log.e("Hello MF", "link : $videoId")
        Log.e("Hello MF", "link : $fullUrl")


        _uiState.update { it.copy(
            videoId = videoId,
            tutorial  = tutorial,
            isLoading = true
        )}
        loadSavedProgress(tutorial.id?:"")

    }

    private fun loadSavedProgress(tutorialId:String){
        viewModelScope.launch {
            getProgressUseCase(tutorialId).fold(
                onSuccess = {progress ->
                    _uiState.update { it.copy(
                        savedProgress = progress,
                        showResumeDialog = progress!=null && progress.watchedTill >10
                    ) }
                },
                onFailure = {
                    _uiState.update { it.copy(
                        savedProgress = null
                    ) }
                }
            )
        }
    }

    fun onResumeFromLastPosition(){
        _uiState.update { it.copy(
            shouldSeekTo = it.savedProgress?.watchedTill ?:0,
            showResumeDialog = false // look over it, looks sus
        ) }
    }
    fun onStartFromBeginning(){
        _uiState.update { it.copy(
            shouldSeekTo = 0,
            showResumeDialog = false
        ) }
        currentTutorial?.id?.let { tutorialId->
            viewModelScope.launch {
                clearProgressUseCase(tutorialId)
            }
        }
    }
    fun onPlayerStateChanged(state:PlaybackState){
        _uiState.update { it.copy(
            playerState = state
        ) }
        when(state){
            PlaybackState.Paused->{
                saveCurrentProgress()
            }
            PlaybackState.Ended->{
                saveCurrentProgress()
                markAsCompleted()
            }
            else ->{

            }
        }

    }
    fun onProgressUpdate(currentSeconds:Long, totalSeconds:Long){
        _uiState.update { it.copy(
            totalDuration = totalSeconds,
            currentPosition = currentSeconds
        ) }
    }

    private fun saveCurrentProgress(){
        val state = _uiState.value
        currentTutorial?.id?.let { tutorialId->
            viewModelScope.launch{
                saveProgressUseCase(
                    tutorialId = tutorialId,
                    currentPosition = state.currentPosition,
                    totalDuration = state.totalDuration
                )
            }
        }
    }
    private fun markAsCompleted(){
        val state = _uiState.value
        currentTutorial?.id?.let { tutorialId->
            viewModelScope.launch{
                saveProgressUseCase(
                    tutorialId = tutorialId,
                    currentPosition = state.totalDuration,
                    totalDuration = state.totalDuration
                )
            }
        }
    }
    fun onBackPressed(){
        saveCurrentProgress()
    }

    override fun onCleared() {
        super.onCleared()
        saveCurrentProgress()
    }






}
data class VideoPlayerUiState(
    val videoId: String? = null,
    val tutorial: Tutorial? = null,
    val isLoading: Boolean = false,
    val playerState: PlaybackState = PlaybackState.Idle,
    val currentPosition: Long = 0,
    val totalDuration: Long = 0,
    val savedProgress: VideoProgress? = null,
    val showResumeDialog: Boolean = false,
    val shouldSeekTo: Long = 0,
    val error: String? = null
)

