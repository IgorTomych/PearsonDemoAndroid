package itomy.ch.pearson.info

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import itomy.ch.pearson.model.AccessToken
import itomy.ch.pearson.model.Course
import itomy.ch.pearson.model.User
import itomy.ch.pearson.model.util.Resource
import itomy.ch.pearson.repository.Repository
import okhttp3.ResponseBody

/**
 * Created by Yegor on 11/10/17.
 */
class CoursesViewModel(private val repository: Repository) : ViewModel() {

    fun authenticate(username: String, password: String): LiveData<Resource<AccessToken>> {
        return repository.authenticate(username, password)
    }

    fun loadCourses(): LiveData<Resource<List<Course>>> {
        return repository.getCourses()
    }

    fun logout(): LiveData<Resource<ResponseBody>> {
        return repository.logout()
    }

    fun isAuthorised(): Boolean {
        return repository.isAuthorised()
    }

    fun loadUser(): LiveData<Resource<User>> {
        return repository.loadUser()
    }
}

class PearsonViewModelFactory(val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val instance = Repository.getInstance(context)
        return CoursesViewModel(instance) as T
    }

}