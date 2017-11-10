package itomy.ch.pearson.storage

import android.content.Context
import android.content.SharedPreferences
import itomy.ch.pearson.model.AccessToken

private const val STORAGE_NAME = "STORAGE_NAME"

class Storage private constructor(private val sharedPreferences: SharedPreferences) {

    val accessToken: AccessToken?
        get() = AccessToken.createTokenFromPreferences(sharedPreferences)

    fun saveAccessToken(token: AccessToken) {
        val editor = sharedPreferences.edit()
        token.putToStorage(editor)
        editor.apply()
    }

    fun logout() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {

        fun getInstance(preferences: SharedPreferences): Storage {
            return Storage(preferences)
        }

        fun getInstance(context: Context): Storage {
            return Storage(context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE))
        }
    }
}