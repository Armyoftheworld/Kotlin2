package com.daijun.kotlin

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-01-17
 * @description
 */

fun main() {
//    doTogetherSync()
//    doTogetherAsync()
//    doThgetherAsyncLazy()
//    doTogetherWithAsyncStyle()
//    doStructuredConcurrent()
    doStructuredConcurrentFailure()
}

fun doStructuredConcurrentFailure() {
    runBlocking {
        try {
            doTogetherWithStructuredFailure()
        } catch (e: ArithmeticException) {
            println("Computation failed with ArithmeticException")
        }
    }
}

/**
 * 如果其中⼀个⼦协程（即 two ）失败，第⼀个 async 以及等待中的⽗协程都会被取消
 */
suspend fun doTogetherWithStructuredFailure() = coroutineScope {
    val one = async {
        try {
            // 模拟⼀个⻓时间的运算
            delay(Long.MAX_VALUE)
            42
        } finally {
            println("first child was cancelled")
        }
    }
    val two = async<Int> {
        println("second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}

fun doStructuredConcurrent() {
    runBlocking {
        val time = measureTimeMillis {
            println("the answer is ${doTogetherWithStructured()}")
        }
        println("total time is $time")
    }
}

/**
 * 结构化并发
 * 这种情况下，如果在 concurrentSum 函数内部发⽣了错误，
 * 并且它抛出了⼀个异常，所有在作⽤域中启动的协程都会被取消
 */
suspend fun doTogetherWithStructured() = coroutineScope {
    val d1 = async { doSomethingUsefulOne() }
    val d2 = async { doSomethingUsefulTwo() }
    d1.await() + d2.await()
}

fun doTogetherWithAsyncStyle() {
    val time = measureTimeMillis {
        // 我们可以在协程外⾯启动异步执⾏
        val deferred1 = doSomethingUsefulOneAsync()
        val deferred2 = doSomethingUsefulTwoAsync()
        // 但是等待结果必须调⽤其它的挂起或者阻塞
        // 当我们等待结果的时候，这⾥我们使⽤ `runBlocking { …… }` 来阻塞主线程
        runBlocking {
            println("the answer is ${deferred1.await() + deferred2.await()}")
        }
    }
    println("total time is $time")
}

/**
 * 只有结果通过 await 获取的时候协程才会启动，或者在 Job 的 start 函数调⽤的时候
 */
fun doThgetherAsyncLazy() {
    runBlocking {
        val time = measureTimeMillis {
            val d1 = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            val d2 = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
            println("the answer is ${d1.await() + d2.await()}")
        }
        println("total time is $time")
    }
}

/**
 * 异步执行代码，async
 */
fun doTogetherAsync() {
    runBlocking {
        val time = measureTimeMillis {
            val d1 = async { doSomethingUsefulOne() }
            val d2 = async { doSomethingUsefulTwo() }
            println("the answer is ${d1.await() + d2.await()}")
        }
        println("total time is $time")
    }
}

fun doTogetherSync() {
    runBlocking {
        val time = measureTimeMillis {
            val r1 = doSomethingUsefulOne()
            val r2 = doSomethingUsefulTwo()
            println("the answer is ${r1 + r2}")
        }
        println("total time is $time")
    }
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L)
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L)
    return 27
}

/**
 * 在 Kotlin 的协程中使⽤这种⻛格是强烈不推荐强烈不推荐的
 */
fun doSomethingUsefulOneAsync() = GlobalScope.async(start = CoroutineStart.LAZY) {
    doSomethingUsefulOne()
}

/**
 * 在 Kotlin 的协程中使⽤这种⻛格是强烈不推荐强烈不推荐的
 */
fun doSomethingUsefulTwoAsync() = GlobalScope.async(start = CoroutineStart.LAZY) {
    doSomethingUsefulTwo()
}