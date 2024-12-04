package com.example.readersapp.createuser

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber
import javax.inject.Inject

interface CreateUserRepository {
    suspend fun createUser(userName: String, password: String)
    fun addUser(user: String?)
}

class CreateUserRepositoryImpl @Inject constructor() : CreateUserRepository {
    private val auth: FirebaseAuth = Firebase.auth
    override suspend fun createUser(userName: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayname = task.result?.user?.email?.split('@')?.get(0)
                        addUser(name = displayname)
                        Timber.i("Take me home :: ${task.result}}")
                    } else {
                        Timber.i("Take me home :: ${task.result}}")
                    }
                }
        } catch (e: Exception) {
            Timber.i("${e.printStackTrace()}")
        }
    }

    override fun addUser(name: String?) {
        val user = UserDataModel(
            id = null,
            userId = auth.currentUser?.uid.toString(),
            displayName = name.toString(),
            quote = "Life is great",
            proffession = "Android Developer",
            avatarUrl = ""
        )
        FirebaseFirestore.getInstance().collection("users").add(user)
    }
}
