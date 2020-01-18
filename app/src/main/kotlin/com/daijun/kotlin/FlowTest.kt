package com.daijun.kotlin

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-01-18
 * @description
 */

fun main() {
//    foo()
//    runFlow()
//    coldFlow()
//    cancelFlow()
//    createFlow()
//    mapFlow()
//    transformFlow()
//    takeFlow()
//    terminalOperatorFlow()
//    continuousFlow()
//    changeFlowContext()
//    bufferFlow()
//    conflateFlow()
//    zipCombineFlow()
//    flatMapFlow()
//    flatMapMergeFlow()
//    flatMapLatestFlow()
//    catchFlowException()
//    completeFlow()
    launchInFlow()
}

fun launchInFlow() = runBlocking {
    (1..3).asFlow()
        .onEach {
            println("onEach $it")
        }
        .launchIn(this)
    println("Done") // 这里立即执行
}

/**
 * 监听流的完成
 * 一种：用try-finally包裹
 * 另一种：用onCompletion操作符，onCompletion操作符也可以知道是否是异常导致的结束,仅限上游异常
 */
fun completeFlow() = runBlocking {
    try {
        (1..3).asFlow()
            .onEach {
                delay(200L)
                println(Thread.currentThread().name)
            }
            .flowOn(Dispatchers.IO)
            .collect{
                println("collect $it")
            }
    } finally {
        println("flow completely")
    }
    println("out flow")
    (0..3).asFlow()
        .onEach { delay(200L) }
        .map{
            100 / it
        }
        .onCompletion {
            println("flow complete in onCompletion, with exception: $it")
        }
        .catch {
            println("catch exception: $it")
        }
        .collect{
            println("collect $it")
        }
}

/**
 * 可以用try-catch捕获异常，所有操作符里的异常都能捕获
 * 或者用catch操作符，catch只能捕获catch上面的异常，catch下面的异常无法捕获
 */
fun catchFlowException() = runBlocking {
    try {
        (1..3).asFlow()
            .onEach { println("Emitting $it") }
            .collect{
                println("value : $it")
                check(it <= 1) { "Collected $it" }
            }
    } catch (e: Exception) {
        println("caught $e")
    }
    try {
        (1..3).asFlow()
            .onEach { println("Emitting $it") }
            .map{
                check(it <= 1) {
                    "Collected $it"
                }
                it * it
            }
            .collect{
                println("value : $it")
            }
    } catch (e: Exception) {
        println("caught $e")
    }
    (0..3).asFlow()
        .onEach { println("Emitting $it") }
        .map{
            check(it <= 1) {
                "Collected $it"
            }
            100 / it
        }
        .catch {
            println("catch exception: $it")
        }
        .collect{
            println("value : $it")
        }
}

fun flatMapLatestFlow() = runBlocking {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow()
        .onEach { delay(100L) }
        .flatMapLatest { requestFlow(it) }
        .collect{ println("$it at ${System.currentTimeMillis() - startTime}ms from start")}
}

fun flatMapMergeFlow() = runBlocking {
    val time = measureTimeMillis {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(100L) }
            .flatMapMerge { requestFlow(it) }
            .collect{ println("$it at ${System.currentTimeMillis() - startTime}ms from start")}
    }
    println("flatMapMerge consumes time $time")
}

fun flatMapFlow() = runBlocking {
    val time = measureTimeMillis {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(100L) }
            .flatMapConcat { requestFlow(it) }
            .collect{ println("$it at ${System.currentTimeMillis() - startTime}ms from start")}
    }
    println("flatMapConcat consumes time $time")
}

fun requestFlow(index: Int) = flow{
    emit("$index first")
    delay(500L)
    emit("$index second")
}

fun zipCombineFlow() = runBlocking {
    val nums = (1..4).asFlow().onEach { delay(300L) }
    val strs = flowOf("one", "two", "three").onEach { delay(400L) }
    nums.zip(strs) { a, b ->
        "zip $a --> $b"
    }.collect { println(it) }

    // nums 或 strs 流中的每次发射都会打印⼀⾏
    nums.combine(strs) { a, b ->
        "combine $a --> $b"
    }.collect{ println(it)}
}

/**
 * conflate 操作符可以⽤于跳过中间值
 * 当流代表部分操作结果或操作状态更新时，可能没有必要处理每个值，⽽是只处理最新的那个
 */
fun conflateFlow() = runBlocking {
    var time = measureTimeMillis {
        flow {
            for (index in 1..3) {
                delay(100L)
                println("current working in ${Thread.currentThread().name}")
                emit(index)
            }
        }
            .conflate()
            .collect {
                delay(300L)
                println(it)
            }
    }
    println("run with buffer consumes time $time")
    time = measureTimeMillis {
        flow {
            for (index in 1..3) {
                delay(100L)
                println("current working in ${Thread.currentThread().name}")
                emit(index)
            }
        }
            .collectLatest {
                // 处理最新值
                delay(300L)
                println(it)
            }
    }
    println("collectLatest consumes time $time")
}

/**
 * 缓冲
 * 高效创建了处理流⽔线
 */
fun bufferFlow() = runBlocking {
    var time = measureTimeMillis {
        flow {
            for (index in 1..3) {
                delay(200L)
                println("current working in ${Thread.currentThread().name}")
                emit(index)
            }
        }
            .collect {
                delay(300L)
                println(it)
            }
    }
    println("run without buffer consumes time $time")
    time = measureTimeMillis {
        flow {
            for (index in 1..3) {
                delay(200L)
                println("current working in ${Thread.currentThread().name}")
                emit(index)
            }
        }
            .buffer()
            .collect {
                delay(300L)
                println(it)
            }
    }
    println("run with buffer consumes time $time")
}

fun changeFlowContext() = runBlocking {
    flow {
        for (index in 1..5) {
            println("value is $index, flow current work in ${Thread.currentThread().name}")
            delay(500L)
            println("value is $index, flow current work in ${Thread.currentThread().name}")
            emit(index)
        }
    }
        .flowOn(Dispatchers.IO) // 改变上面执行的上下文
        .map {
            println("value is $it, map current work in ${Thread.currentThread().name}")
            delay(200L)
            println("value is $it, map current work in ${Thread.currentThread().name}")
            "string $it"
        }
        .flowOn(Dispatchers.Default)
        .collect { println("result is $it, collect current work in ${Thread.currentThread().name}") }
}

/**
 * 流是连续的
 */
fun continuousFlow() = runBlocking {
    (1..10).asFlow()
        .filter {
            println("filter $it")
            it % 2 == 0
        }
        .map {
            println("map $it")
            "string $it"
        }
        .collect {
            println(it)
        }
}

/**
 * 末端流操作符, collect, toList, toSet, first, single, reduce, fold等等
 */
fun terminalOperatorFlow() = runBlocking {
    // 求和
    val sum = (1..100).asFlow()
        .reduce { a, b -> a + b }
    println("the result is $sum")
}

/**
 * 限⻓操作符
 */
fun takeFlow() = runBlocking {
    flow {
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        } finally {
            println("Finally")
        }
    }
        .take(2) //只获取前两个
        .collect { value -> println(value) }
}

/**
 * 使⽤ transform 操作符，我们可以 发射 任意值任意次
 */
fun transformFlow() = runBlocking {
    val stringMap = StringMap()
    flowOf(1, 2, 3)
        .transform {
            emit("Making request $it")
            emit(stringMap.convertString(it))
        }
        .collect { value -> println(value) }
}

fun mapFlow() = runBlocking {
    val stringMap = StringMap()
    flowOf(1, 2, 3)
        .map { value -> stringMap.convertString(value) }
        .collect { value -> println(value) }
}

class StringMap {
    suspend fun convertString(int: Int): String {
        delay(500L)
        return "StringMap $int"
    }
}

fun createFlow() = runBlocking {
    println("createFlow...")
    flowOf(1, 2, 3, 4, 5).collect { value -> println(value) }
    (1..5).asFlow().collect { value -> println(value) }
}

fun cancelFlow() = runBlocking {
    println("cancelFlow...")
    withTimeoutOrNull(250L) {
        generateFlow().collect { value -> println(value) }
    }
    println("Done")
}

fun coldFlow() = runBlocking {
    println("calling generateFlow...")
    val flow = generateFlow()
    println("calling collect...")
    flow.collect { value -> println(value) }
    println("calling collect again...")
    flow.collect { value -> println(value) }
}


fun runFlow() = runBlocking {
    launch {
        for (index in 1..3) {
            println("I'm not blocked $index")
            delay(100L)
        }
    }
    generateFlow().collect { value -> println(value) }
}

fun generateFlow() = flow {
    println("Flow started")
    for (index in 1..5) {
        delay(100L)
        println("Emitting $index ...")
        emit(index)
    }
}

fun foo() {
    generateSequence().forEach(::println)
}

fun generateSequence() = sequence {
    for (index in 1..5) {
        Thread.sleep(100L)
        yield(index)
    }
}

