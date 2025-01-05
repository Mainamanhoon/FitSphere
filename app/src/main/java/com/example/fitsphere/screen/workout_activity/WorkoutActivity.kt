package com.example.fitsphere.screen.workout_activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitsphere.adapter.TutorialAdapter
import com.example.fitsphere.databinding.ActivityWorkoutBinding
import com.example.fitsphere.model.Workout

class WorkoutActivity : AppCompatActivity() {
    var _binding:ActivityWorkoutBinding?=null
    val binding get() = _binding!!
    lateinit var workout : Workout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWorkoutBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        getObject()
        setVariables()
    }

    private fun getObject() {
         workout = intent.getSerializableExtra("object") as Workout;
    }

    @SuppressLint("SetTextI18n")
    fun setVariables(){
        val resId = resources.getIdentifier(workout.picPath,"drawable",packageName)
        Glide.with(this)
            .load(resId)
            .into(binding.imageView5)
        binding.titleTxt.text = workout.title
        binding.exerciseTxt.text = "${workout.tutorials.size} Exercises"
        binding.durationTxt.text = workout.durationAll
        binding.kcalTxt.text = "${workout.kcal} Kcal"
        binding.descriptionTxt.text = workout.description

        val recyclerView :RecyclerView = binding.view3

        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        recyclerView.adapter = TutorialAdapter( workout.tutorials)

    }
}