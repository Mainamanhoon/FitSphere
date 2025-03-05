package com.example.fitsphere.screen.intro_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fitsphere.databinding.ActivityIntroBinding
import com.example.fitsphere.model.Tutorial
import com.example.fitsphere.model.Workout
import com.example.fitsphere.screen.main_activity.MainActivity
import com.example.fitsphere.screen.signup_activity.SignupActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

class IntroActivity : AppCompatActivity() {
    private var _binding : ActivityIntroBinding?=null
    private val binding get() = _binding!!
    lateinit var auth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIntroBinding.inflate(layoutInflater)
         setContentView(binding.root)
        auth = Firebase.auth
//        pushWorkoutDataOnce()
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



//    fun pushWorkoutDataOnce() {
//        val firestore = FirebaseFirestore.getInstance()
//        val workoutCollection = firestore.collection("workouts")
//
//        // Create your workout data
//        val workoutData = Workout(
//            description = "You just woke up. It is a brand new day. The canvas is blank. How do you begin? Take 21 minutes to cultivate a peaceful mind and strong body.",
//            durationAll = "65 min",
//            kcal = 180,
//            picPath = "pic_3",
//            title = "Yoga",
//            tutorials = listOf(
//                Tutorial(
//                    duration = "23:00",
//                    link = "v7AYKMP6rOE",
//                    picPath = "pic_3_1",
//                    title = "Lesson 1"
//                )
//            )
//        )
//
//        // Check if the collection is empty before adding data
//        workoutCollection.get().addOnSuccessListener { snapshot ->
//            if (snapshot.isEmpty) {
//                // Add the workout data to Firestore
//                workoutCollection.add(workoutData)
//                    .addOnSuccessListener {
//                        Log.d("h1","Workout data added successfully!")
//                    }
//                    .addOnFailureListener { exception ->
//                        println("Error adding workout data: ${exception.message}")
//                    }
//            } else {
//                println("Workout data already exists, not adding again.")
//            }
//        }.addOnFailureListener { exception ->
//            println("Error checking Firestore collection: ${exception.message}")
//        }
//    }

}