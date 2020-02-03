package com.daijun.kotlin

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-03
 * @description 委托
 */
interface Base {
    fun printMsg()
    fun printMessage()
}

class BaseImpl(val value: Int): Base {
    override fun printMessage() {
        println("new print, value = $value")
    }

    override fun printMsg() {
        println("value = $value")
    }
}

class Derived(b: Base): Base by b {
    override fun printMessage() {
        println("override print")
    }
}

fun main() {
    val b = BaseImpl(10)
    val d = Derived(b)
    d.printMsg()
    d.printMessage()
}
