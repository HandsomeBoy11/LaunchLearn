package com.yyzh.myapplication.base


open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> WanResponse<T>): WanResponse<T> {
        return call.invoke()
    }


}