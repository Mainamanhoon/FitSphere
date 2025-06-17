package com.example.fitsphere.screen.main_activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.healthConnect.HealthPermissionHelper
import com.example.common.Resource
import com.example.domain.model.Workout
import com.example.fitsphere.Loader
import com.example.fitsphere.adapter.WorkoutAdapter
import com.example.fitsphere.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

 class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var loader: Loader
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()
    private lateinit var workoutAdapter: WorkoutAdapter
    private lateinit var requestPermissions: ActivityResultLauncher<Set<String>>
    private lateinit var permissionHelper: HealthPermissionHelper




    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loader = Loader(this)
        var workoutList: List<Workout> = emptyList()

        permissionHelper = HealthPermissionHelper(this, mainActivityViewModel.healthConnectClient!!)
        requestPermissions = registerForActivityResult(
            permissionHelper.createPermissionRequestLauncher()
        ) { grantedPermissions ->
            val allGranted = permissionHelper.permissions.containsAll(grantedPermissions)
            if (allGranted) {
                Toast.makeText(this, "All health permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Not all health permissions were granted", Toast.LENGTH_LONG).show()
            }
        }


        mainActivityViewModel.getWorkouts()
        val recyclerView: RecyclerView = binding.recyclerView1
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        workoutAdapter= WorkoutAdapter(workoutList)
        recyclerView.adapter = workoutAdapter




        lifecycleScope.launch {
            if(!permissionHelper.isAllPermissionsGranted()){
                requestPermissions.launch(permissionHelper.permissions)
            }
        }

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
    }

}
