package com.example.bizcardapp.myprofile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectionNameViewModel @Inject constructor() : ViewModel() {

    val count = mutableStateOf(0)
    val indexCount = mutableStateOf(0)
    fun incrementIndexPosition(){
            count.value += 1
            indexCount.value +=1
    Log.i("Count","${count.value}")
    }


}

val names = listOf(
    "John",
    "Mathew",
)