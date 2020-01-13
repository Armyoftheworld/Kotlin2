package com.daijun.kotlin

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-01-12
 * @description
 */

fun main(args: Array<String>) {
    exchangeVariable()

    // 默认 | ⽤作边界前缀，但你可以选择其他字符并作为参数传⼊，⽐如 trimMargin(">")
    val text = """
        |Tell me and I forget. 
        |Teach me and I remember. 
        |Involve me and I learn. 
        |(Benjamin Franklin) """.trimMargin()
    println(text)
    iteration()
}

fun iteration() {
    val arr = arrayOf("a", "b", "c")
    for ((index, value) in arr.withIndex()) {
        println("index = $index, value = $value")
    }

    for (index in arr.indices) {
        println("index = $index, value = ${arr[index]}")
    }

    // ⽤标签限制 break 或者continue,标签的格式为标识符后跟 @ 符号
    loop@for (x in 1..100) {
        for (y in 5..99) {
            if (x * x + y * y > 100) {
                break@loop
            }
        }
    }

    arr.forEach lit@{
        if (it === "b") {
            // 这样写只表示结束本次执行，循环还是会继续执行，类似于continue
            return@lit
        }
        println("it = $it")
    }

    arr.forEach {
        if (it === "b") {
            // 这样写只表示结束本次执行，循环还是会继续执行，类似于continue
            // 通常情况下使⽤隐式标签更⽅便。该标签与接受该 lambda 的 函数同名
            return@forEach
        }
        println("it = $it")
    }

    run run@{
        arr.forEach {
            if (it === "b") {
                // 这样写相当于结束整个循环，类似于break
                return@run
            }
            println("it = $it")
        }
    }
    println("run foreach end")
}

/**
 * 交换变量
 */
fun exchangeVariable() {
    var a = 1
    var b = 2
    a = b.also { b = a }
    println("a = $a, b = $b")

    a = b.apply {
        b = a
    }
    println("a = $a, b = $b")

}
