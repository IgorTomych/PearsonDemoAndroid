package itomy.ch.pearson.api

import android.util.Log
import retrofit2.Response
import java.io.IOException

/**
 * Created by Yegor on 11/10/17.
 */
public class ApiResponse<T> {

    private val code: Int
    val body: T?
    val errorMessage: String?

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                    Log.e("ignored", "error while parsing response")
                }
            }
            if (message == null) {
                message = response.message()
            }
            errorMessage = message
            body = null
        }
    }

    val isSuccessful: Boolean
        get() = code in 200..299
}