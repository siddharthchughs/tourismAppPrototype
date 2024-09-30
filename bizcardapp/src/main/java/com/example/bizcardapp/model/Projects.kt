package com.example.bizcardapp.model

import javax.annotation.concurrent.Immutable

@Immutable
data class Projects(
    var id:Int,
    val name:String
)
