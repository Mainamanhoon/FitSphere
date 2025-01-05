package com.example.fitsphere.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitsphere.screen.workout_activity.WorkoutActivity
import com.example.fitsphere.R
import com.example.fitsphere.model.Workout

class WorkoutAdapter(var items : List<Workout>)
    : RecyclerView.Adapter<WorkoutAdapter.ViewHolder>(){
    lateinit var context: Context


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
          val title : TextView = itemView.findViewById(R.id.title_tv)
          val imageView : ImageView = itemView.findViewById(R.id.img_iv)
          val duration : TextView = itemView.findViewById(R.id.duration_tv)
          val exercise : TextView = itemView.findViewById(R.id.exercise_tv)
          val kcal :TextView = itemView.findViewById(R.id.Kcal_tv)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutAdapter.ViewHolder {
        context =parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_1,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WorkoutAdapter.ViewHolder, position: Int) {
        val workout = items[position]
        holder.title.text = workout.title
        val resId = holder.itemView.context.resources.getIdentifier(
            workout.picPath, "drawable", holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(resId)
            .into(holder.imageView)
        holder.exercise.text = "${workout.tutorials.size} Exercise"
        holder.kcal.text = "${workout.kcal} Kcal"
        holder.duration.text = workout.durationAll

        holder.itemView.setOnClickListener{
            val intent = Intent(context, WorkoutActivity::class.java)
            intent.putExtra("object",workout)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateWorkoutList(newList: List<Workout>) {
        items = newList
        notifyDataSetChanged()
    }
}