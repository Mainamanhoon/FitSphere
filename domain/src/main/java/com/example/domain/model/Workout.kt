package com.example.domain.model

import java.io.Serializable

data class Workout(
    var id :String? = null,
    var title:String?=null,
    var description:String?=null,
    var picPath:String? = null,
    var kcal:Int? = null,
    var durationAll:String? = null,
    var tutorials: List<String> = emptyList()
 ):Serializable {


}