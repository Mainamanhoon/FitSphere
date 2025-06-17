package com.example.domain.model

import java.io.Serializable

data class Tutorial(
    var id :String? =null,
    var title:String?= null,
    var duration:String?=null,
    var link:String?=null,
    var picPath:String?=null,
    var description : String? = null
):Serializable {




}
