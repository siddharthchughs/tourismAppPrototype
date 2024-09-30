package com.example.bizcardapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

sealed interface MyProfileUIState {
    data object Loading : MyProfileUIState
    data class Loaded(
        val projectsList: List<ProjectUIState>
    ) : MyProfileUIState

    data class ProjectUIState(
        var id: Int,
        val name: String
    )
}

@HiltViewModel
class MyProfileViewModel @Inject constructor() : ViewModel() {

    val listOfProjects = mutableListOf<MyProfileUIState.ProjectUIState>()
    var isbuttonClicked = mutableStateOf(false)

    val myProfileUIState: Flow<MyProfileUIState> = flowOf(
        transform()
    )

    private fun transform(): MyProfileUIState {
        (1..10).forEach {
            listOfProjects.add(MyProfileUIState.ProjectUIState(it, "Project $it"))
        }
        Log.i("List", "$listOfProjects")
        return MyProfileUIState.Loaded(listOfProjects)
    }

    fun eventClicked(isClicked: Boolean) {
        Log.i("List", "$isClicked")
        isbuttonClicked.value = isClicked
    }

}
