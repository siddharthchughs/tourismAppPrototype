package com.example.readersapp.createuser

data class UserDataModel(
    val id:String?,
    val userId:String,
    val displayName:String,
    val avatarUrl:String,
    val quote:String,
    val proffession:String
){
    fun mapInfo():MutableMap<String,String> =
        mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "quote" to this.quote,
            "avatar_url" to this.avatarUrl,
            "profession" to this.proffession
        )
}

