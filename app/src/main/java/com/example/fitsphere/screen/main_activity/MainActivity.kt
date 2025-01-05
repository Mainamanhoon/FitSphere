package com.example.fitsphere.screen.main_activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitsphere.adapter.WorkoutAdapter
import com.example.fitsphere.Loader
import com.example.fitsphere.data.service.Resource
import com.example.fitsphere.databinding.ActivityMainBinding
import com.example.fitsphere.model.Workout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var loader: Loader
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var workoutAdapter: WorkoutAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loader = Loader(this)
        var workoutList: List<Workout> = emptyList()


        mainActivityViewModel.getWorkouts()
        val recyclerView: RecyclerView = binding.recyclerView1
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        workoutAdapter= WorkoutAdapter(workoutList)
        recyclerView.adapter = workoutAdapter


        lifecycleScope.launch {
           mainActivityViewModel.dataState.collect{state->
               when(state){
                   is Resource.Success->{
                       workoutList = state.result
                       workoutAdapter.updateWorkoutList(workoutList)
                       loader.cancel()
                   }
                   is Resource.Loading->{
                       loader.show()
                   }
                   is Resource.Failure->{
                       Toast.makeText(this@MainActivity,"Error Has Occurred${state.exception.message}", Toast.LENGTH_LONG).show()
                       loader.cancel()
                   }
                   else ->Unit
               }

           }
        }


        setupUI()


    }

    private fun setupUI() {
        mainActivityViewModel.currentUser?.displayName?.let { binding.userNameText.text = it }

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = result.data
                val imageUri = intent?.data
                if (imageUri != null) {
                    binding.profilePicIv.setImageURI(imageUri)
                } else {
                    Toast.makeText(this, "No image Selected", Toast.LENGTH_LONG).show()
                }
            }

        }

        binding.profilePicIv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResultLauncher.launch(intent)
        }

    }
}