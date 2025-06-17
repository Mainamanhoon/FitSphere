package com.example.fitsphere.screen.workout_activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.common.Resource
import com.example.domain.model.Tutorial
import com.example.domain.model.Workout
import com.example.fitsphere.adapter.TutorialAdapter
import com.example.fitsphere.databinding.ActivityWorkoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

 class WorkoutActivity : AppCompatActivity() {

    var _binding:ActivityWorkoutBinding?=null
    val binding get() = _binding!!
    lateinit var workout : Workout
    lateinit var tutorials: List<Tutorial>
     private val viewModel by viewModels<WorkoutActivityViewModel>()

    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWorkoutBinding.inflate(layoutInflater)

        setContentView(binding.root)
        workout = intent.getSerializableExtra("object") as Workout;

//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
         setVariables()
        getObject()
    }

    private fun getObject() {
        viewModel.getTutorials(workout.tutorials)
        lifecycleScope.launch {
            viewModel.dataState.collect{state->
                when(state){
                    is Resource.Success ->{
                          Log.d("WorkoutActivity", "${state.result}")
                          recyclerView.adapter = TutorialAdapter(state.result)
                    }

                    is Resource.Failure ->{
                        state.exception.printStackTrace()
                        Log.e("WorkoutActivity" ,state.exception.toString())
                    }
                    Resource.Loading -> Unit
                    null -> Unit
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    fun setVariables(){
        Glide.with(this)
            .load(workout.picPath)
            .into(binding.imageView5)
        binding.titleTxt.text = workout.title
        binding.exerciseTxt.text = "${workout.tutorials.size} Exercises"
        binding.durationTxt.text = workout.durationAll
        binding.kcalTxt.text = "${workout.kcal} Kcal"
        binding.descriptionTxt.text = workout.description

         recyclerView  = binding.view3
//
        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)

    }
}