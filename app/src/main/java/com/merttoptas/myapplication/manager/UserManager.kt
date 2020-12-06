package com.merttoptas.myapplication.manager

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {
    // Create the data Store
    private val dataStore = context.createDataStore(name = "user_prefs")

    companion object {
        val USER_AGE_KEY = preferencesKey<Int>("USER_AGE")
        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
        val USER_REMEMBER_KEY = preferencesKey<Boolean>("USER_REMEMBER")
    }

    //Store user data
    suspend fun storeUser(age: Int, name: String, isRemember: Boolean) {
        dataStore.edit {
            it[USER_AGE_KEY] = age
            it[USER_NAME_KEY] = name
            it[USER_REMEMBER_KEY] = isRemember
        }
    }

    val userAgeFLow: Flow<Int> = dataStore.data.map {
        val age = it[USER_AGE_KEY] ?: 0

        Toast.makeText(context, "You are age : $age", Toast.LENGTH_SHORT).show()

        age
    }

    //create a name
    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

    val userRememberFlow: Flow<Boolean> = dataStore.data.map {
        it[USER_REMEMBER_KEY] ?: false
    }
}