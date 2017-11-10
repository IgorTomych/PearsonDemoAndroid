package itomy.ch.pearson.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import itomy.ch.pearson.api.PearsonApiService
import itomy.ch.pearson.api.PulseApiService
import itomy.ch.pearson.model.AccessToken
import itomy.ch.pearson.model.AuthBody
import itomy.ch.pearson.model.Course
import itomy.ch.pearson.model.User
import itomy.ch.pearson.model.util.Resource
import okhttp3.ResponseBody

/**
 * Created by Yegor on 11/10/17.
 */
object Repository {

    //    todo save it to storage
    private var token: AccessToken? = null
    private val pearsonApiService by lazy {
        PearsonApiService.getInstance()
    }


    public fun authenticate(username: String, password: String): LiveData<Resource<AccessToken>> {
        val liveData = MediatorLiveData<Resource<AccessToken>>()
        liveData.value = Resource.loading()
        val response = pearsonApiService.authenticate(AuthBody(username, password))
        liveData.addSource(response, {
            liveData.removeSource(response)
            liveData.value = if (it!!.isSuccessful) {
//              todo save data to storage
                token = it.body
                Resource.success(it.body)
            } else {
                Resource.error(it.errorMessage)
            }
        })
        return liveData
    }

    public fun getCourses(): LiveData<Resource<List<Course>>> {
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

    private fun loadUser(liveData: MediatorLiveData<Resource<List<Course>>>, success: (User?) -> Unit) {
        if (token == null) {
            liveData.value = Resource.error("User not authorized")
        } else {
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
    }


    public fun logout(): LiveData<Resource<ResponseBody>> {
        val liveData = MediatorLiveData<Resource<ResponseBody>>()
        liveData.value = Resource.loading()
        if (token == null) {
            liveData.value = Resource.error("User not authorized")
        } else {
            val logout = pearsonApiService.logout(token!!.getHeader())
            liveData.addSource(logout, {
                liveData.removeSource(logout)
                liveData.value = if (it!!.isSuccessful) {
//                  todo remove access token
                    Resource.success(it.body)
                } else {
                    Resource.error(it.errorMessage)
                }
            })
        }
        return liveData
    }
}