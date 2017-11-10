package itomy.ch.pearson.model

import android.content.SharedPreferences
import com.google.gson.annotations.SerializedName

/**
 * Created by Yegor on 11/10/17.
 */
private const val TOKEN_PREF_KEY = "TOKEN_PREF_KEY"

data class AccessToken(
        @field:SerializedName("access_token")
        private val token: String) {

    constructor(preferences: SharedPreferences) : this(preferences.getString(TOKEN_PREF_KEY, ""))

    public fun getHeader(): String {
        return "Bearer $token"
    }

    companion object {
        fun createTokenFromPreferences(preferences: SharedPreferences): AccessToken {
            return AccessToken(preferences)
        }
    }

    fun putToStorage(editor: SharedPreferences.Editor) {
        editor.putString(TOKEN_PREF_KEY, token)
    }
}