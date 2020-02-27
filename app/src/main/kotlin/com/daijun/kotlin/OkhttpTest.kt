package com.daijun.kotlin

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/2/27
 * @description 仿照：https://github.com/square/retrofit/blob/master/retrofit/src/main/java/retrofit2/KotlinExtensions.kt
 */

suspend fun Call.await(): String = suspendCancellableCoroutine { continuation ->
    enqueue(object : Callback{
        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    continuation.resumeWithException(NullPointerException(response.toString()))
                } else {
                    continuation.resume(body.string())
                }
            } else {
                continuation.resumeWithException(Exception(response.toString()))
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            if (continuation.isCancelled) {
                return
            }
            continuation.resumeWithException(e)
        }

    })
    continuation.invokeOnCancellation {
        try {
            cancel()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}

fun iterate(params: MutableMap<String, String>) = runBlocking{
    flowOf(1, 2 ,3)
        .map { "index = $it" }
        .collect { params[it] = it }
}

fun test() = runBlocking {
    val client = OkHttpClient.Builder().build()
    val request = Request.Builder()
        .url("https://www.wanandroid.com/article/list/0/json")
        .get()
        .build()
    try {
        val params = mutableMapOf<String, String>()
//        println(params.also { iterate(it) })
//        flowOf(params)
//            .onEach {
//                iterate(it)
//            }.flatMapConcat {
//                println(it)
                val call = client.newCall(request)
                val result = call.await()
                println(result)
//            }.collect {
//                println(it.substring(0, 10))
//            }
//        println(client.newCall(request).await().substring(0, 10))
//            .collect {
//                println(it.substring(0, 10))
//            }
//        println(client.newCall(request).execute().body()?.string())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun testRetrofit() = runBlocking {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com/")
        .build()
    val wanAndroidApi = retrofit.create(WanAndroidApi::class.java)
    println(wanAndroidApi.getArticles())
}

interface WanAndroidApi {
    @GET("article/list/0/json")
    suspend fun getArticles(): String
}

fun main() {
//    test()
    testRetrofit()
    println("end")
}