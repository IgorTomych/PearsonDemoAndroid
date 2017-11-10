package itomy.ch.pearson.model.util

/**
 * Created by Yegor on 11/10/17.
 */
class Resource<T>(val status: Status,
                      val data: T? = null,
                      val message: String? = null) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(msg: String?): Resource<T> {
            return Resource(Status.ERROR, null, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING)
        }
    }


    //    constructor(status: Status) {
//        this.status = status
//        this.data = null
//        this.message = null
//    }
//
//    constructor(status: Status, data: T?) : this(status) {
//        this.data = data
//    }
//
//    constructor(status: Status, message: String?) : this(status) {
//        this.message = message
//    }
//
//    constructor(status: Status, data: T?, message: String?) : this(status, data) {
//        this.message = message
//    }

//    override fun equals(o: Any?): Boolean {
//        if (this === o) {
//            return true
//        }
//        if (o == null || javaClass != o.javaClass) {
//            return false
//        }
//
//        val resource = o as Resource<*>?
//
//        if (status !== resource!!.status) {
//            return false
//        }
//        if (if (message != null) message != resource!!.message else resource!!.message != null) {
//            return false
//        }
//        return if (data != null) data == resource.data else resource.data == null
//    }
//
//    override fun hashCode(): Int {
//        var result = status.hashCode()
//        result = 31 * result + (message?.hashCode() ?: 0)
//        result = 31 * result + (data?.hashCode() ?: 0)
//        return result
//    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}