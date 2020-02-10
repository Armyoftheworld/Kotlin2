package com.daijun.kotlin

import kotlinx.coroutines.*

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-01-17
 * @description
 */

fun main() {
//    doUnCancellableJob()
//    timeout()
//    parentCoroutine()
    launchTest()
//    globalLaunchTest()
    println("end")
}

fun launchTest() = runBlocking(Dispatchers.IO) {
    launch {
        kotlinx.coroutines.delay(1000)
        println("launch")
    }
    println("runBlocking")
}

fun globalLaunchTest() {
    GlobalScope.launch(Dispatchers.IO) {
        kotlinx.coroutines.delay(1000)
        println("GlobalScope launch")
    }
    println("GlobalScope end")
}

fun parentCoroutine() = runBlocking {
    // ⼀个⽗协程总是等待所有的⼦协程执⾏结束。⽗协程并不显式的跟踪所有⼦协程的启动，
    // 并且不必使⽤ Job.join 在最后的时候等待它们
    val request = launch {
        repeat(3) {
            launch {
                delay((it + 1) * 200L)
                println("Coroutine $it is done")
            }
        }
        // 这里不用使⽤ Job.join
        println("request: I'm done and I don't explicitly join my children that are still active")
    }
    request.join()
    println("Now processing of the request is complete")
}

fun timeout() {
    runBlocking {
        // 用withTimeout，如果超时了，会抛出异常
//        val result = withTimeout(1300L) {
//            repeat(1000) {
//                println("job: I'm sleeping $it ...")
//                delay(500L)
//            }
//            "Success"
//        }
//        println("result = $result")

        // withTimeoutOrNull，如果超时了，不会抛出异常，返回null
        val res = withTimeoutOrNull(1300L) {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                delay(500L)
            }
            "Success"
        }
        println("result = $res")
    }
}

/**
 * 运⾏不能取消的代码块, 挂起⼀个被取消的协程
 */
fun doUnCancellableJob() {
    runBlocking {
        val job = launch {
            try {
                repeat(1000) {
                    println("job: I'm sleeping $it ...")
                    delay(500L)
                }
            } finally {
                // 用withContext(NonCancellable)包裹可以让里面的代码执行完
                withContext(NonCancellable) {
                    println("job: I'm running in finally")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300L)
        println("main: I'm tired of waiting")
        job.cancelAndJoin()
        println("main: Now I can quit")
    }
}