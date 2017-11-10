package itomy.ch.pearson.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import itomy.ch.pearson.api.PearsonApiService
import itomy.ch.pearson.api.PulseApiService
import itomy.ch.pearson.model.AccessToken
import itomy.ch.pearson.model.AuthBody
import itomy.ch.pearson.model.Course
import itomy.ch.pearson.model.User
import itomy.ch.pearson.model.util.Resource
import itomy.ch.pearson.storage.Storage
import okhttp3.ResponseBody

/**
 * Created by Yegor on 11/10/17.
 */
class Repository private constructor(private val storage: Storage) {

    private var token: AccessToken? = storage.accessToken
    private val pearsonApiService by lazy {
        PearsonApiService.getInstance()
    }

    companion object {
        private var instance: Repository? = null

        fun getInstance(context: Context): Repository {
            return instance ?: Repository(Storage.Companion.getInstance(context)).also {
                instance = it
            }
        }
    }

    fun isAuthorised(): Boolean {
        return storage.accessToken != null
    }

    fun authenticate(username: String, password: String): LiveData<Resource<AccessToken>> {
        val liveData = MediatorLiveData<Resource<AccessToken>>()
        liveData.value = Resource.loading()
        val response = pearsonApiService.authenticate(AuthBody(username, password))
        liveData.addSource(response, {
            liveData.removeSource(response)
            liveData.value = if (it!!.isSuccessful) {
                token = it.body!!
                storage.saveAccessToken(token!!)
                Resource.success(it.body)
            } else {
                Resource.error(it.errorMessage)
            }
        })
        return liveData
    }

    fun getCourses(): LiveData<Resource<List<Course>>> {
        val liveData = MediatorLiveData<Resource<List<Course>>>()
        liveData.value = Resource.loading()
        loadUser(liveData, {
            val school = it!!.schools[0]
            loadCourses(school.links.services, school.id, liveData)
        })
        return liveData
    }

    private fun loadCourses(url: String, schoolId: String, liveData: MediatorLiveData<Resource<List<Course>>>) {
        val courseSection = PulseApiService.getInstance(url).getCourseSection(token!!.getHeader(), schoolId)
        liveData.addSource(courseSection, {
            liveData.removeSource(courseSection)
            liveData.value = if (it!!.isSuccessful) {
                Resource.success(it.body)
            } else {
                Resource.error(it.errorMessage)
            }
        })

    }

    public fun loadUser(): LiveData<Resource<User>> {
        val liveData = MediatorLiveData<Resource<User>>()
        liveData.value = Resource.loading()
        loadUser(liveData, {
            liveData.value = Resource.success(it)
        })
        return liveData
    }

    private fun <T> loadUser(liveData: MediatorLiveData<Resource<T>>, success: (User?) -> Unit) {
        val user = pearsonApiService.getUser(token!!.getHeader())
        liveData.addSource(user, {
            liveData.removeSource(user)
            it?.let {
                if (it.isSuccessful) {
                    success.invoke(it.body)
                } else {
                    liveData.value = Resource.error(it.errorMessage)
                }
            }
        })
    }

    fun logout(): LiveData<Resource<ResponseBody>> {
        val liveData = MediatorLiveData<Resource<ResponseBody>>()
        liveData.value = Resource.loading()
        val logout = pearsonApiService.logout(token!!.getHeader())
        liveData.addSource(logout, {
            liveData.removeSource(logout)
            liveData.value = if (it!!.isSuccessful) {
                Resource.success(it.body)
            } else {
                Resource.error(it.errorMessage)
            }
        })
        storage.logout()
        return liveData
    }
}