package com.example.fitsphere.screen.intro_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.WeightRecord
import com.example.fitsphere.databinding.ActivityIntroBinding
import com.example.fitsphere.screen.main_activity.MainActivity
import com.example.fitsphere.screen.signup_activity.SignupActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class IntroActivity : AppCompatActivity() {
    private var _binding : ActivityIntroBinding?=null
    private val binding get() = _binding!!
    lateinit var auth :FirebaseAuth

    private lateinit var healthConnectClient: HealthConnectClient
    private lateinit var requestPermissions: ActivityResultLauncher<Set<String>>

//    private val permissions = setOf(
//        HealthPermission.getReadPermission(HeartRateRecord::class),
//        HealthPermission.getWritePermission(HeartRateRecord::class),
//        HealthPermission.getReadPermission(StepsRecord::class),
//        HealthPermission.getWritePermission(StepsRecord::class),
//        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
//        HealthPermission.getWritePermission(TotalCaloriesBurnedRecord::class),
//        HealthPermission.getReadPermission(WeightRecord::class),
//        HealthPermission.getWritePermission(WeightRecord::class),
//    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIntroBinding.inflate(layoutInflater)
         setContentView(binding.root)


        auth = Firebase.auth




        binding.startButton.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            val intent2 = Intent(this, MainActivity::class.java)
            if(auth.currentUser!=null) {
                startActivity(intent2)
             }
            else{
                startActivity(intent)
            }
            finish()
        }



    }



}