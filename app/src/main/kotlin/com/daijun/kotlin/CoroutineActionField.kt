package com.daijun.kotlin

import kotlinx.coroutines.*

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-01-18
 * @description
 */

class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    fun onDestory() {
        cancel()
    }

    fun doSomething() {
        repeat(10) {
            launch {
                delay((it + 1) * 200L)
                println("Coroutine $it is done")
            }
        }
    }
}

fun main() {
    runBlocking(Dispatchers.IO) {
        println("current working in ${Thread.currentThread().name}")
        val activity = Activity()
        activity.doSomething()
        println("launch coroutines")
        delay(500L)
        println("destory activity")
        activity.onDestory()
        delay(1000L)
    }
    println("main ...${Thread.currentThread().name}")
}