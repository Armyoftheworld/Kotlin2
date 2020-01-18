package com.daijun.kotlin

import kotlinx.coroutines.*

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-01-17
 * @description
 */

@ObsoleteCoroutinesApi
fun main() {
    launchBydDifferentDispatcher()
    namedCoroutine()
}

fun namedCoroutine() = runBlocking {
    val d1 = async(CoroutineName("one-coroutine")) {
        delay(500L)
        println("d1 ${Thread.currentThread().name}")
        2
    }
    val d2 = async(CoroutineName("two-coroutine")) {
        delay(400L)
        println("d2 ${Thread.currentThread().name}")
        3
    }
    println("the answer is ${d1.await() + d2.await()}")
}

@ObsoleteCoroutinesApi
fun launchBydDifferentDispatcher()  = runBlocking {
    // 运⾏在⽗协程的上下⽂中，即 runBlocking 主协程
    launch(CoroutineName("main-coroutine")) {
        println("main runBlocking: I'm working in thread :${Thread.currentThread().name}")
    }
    // 不受限的——将⼯作在主线程中
    launch(Dispatchers.Unconfined + CoroutineName("unconfined-coroutine")) {
        println("Unconfined: I'm working in thread :${Thread.currentThread().name}")
    }
    // 将会获取默认调度器
    launch(Dispatchers.Default + CoroutineName("default-coroutine")) {
        println("Default: I'm working in thread :${Thread.currentThread().name}")
    }
    // 将使它获得⼀个新的线程
    launch(newSingleThreadContext("MyOwnThread")) {
        println("newSingleThreadContext: I'm working in thread :${Thread.currentThread().name}")
    }
}