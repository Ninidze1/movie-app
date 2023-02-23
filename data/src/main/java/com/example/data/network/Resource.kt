package com.example.data.network

data class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val loading: Boolean = false
) {

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(message: String): Resource<T> {
            return Resource(Status.ERROR, null, message)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }


}