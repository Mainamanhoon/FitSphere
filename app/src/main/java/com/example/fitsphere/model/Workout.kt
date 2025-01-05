package com.example.fitsphere.model

import java.io.Serializable

data class Workout(
    var id :String? = null,
    var title:String?=null,
    var description:String?=null,
    var picPath:String? = null,
    var kcal:Int? = null,
    var durationAll:String? = null,
    var tutorials: List<Tutorial> = emptyList()
):Serializable {


//    fun getTittle(): String{
//        return title
//    }
//    fun getDescription(): String{
//        return description
//    }
//    fun getPicPath(): String {
//       return picPath
//    }
//
//    fun getLessons(): ArrayList<Tutorial> {
//       return tutorials
//    }
//
//    fun getKcal(): Int {
//        return kcal
//    }
//
//    fun getDurationAll(): String {
//        return durationAll
//    }

}