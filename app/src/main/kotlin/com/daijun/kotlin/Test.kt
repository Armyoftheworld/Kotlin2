package com.daijun.kotlin

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-13
 * @description
 */

class Test{
    var map: Map<String, String>? = null
}

fun main() {
    val test = Test()
    println(test.map?.isEmpty() == false)
    println(test.map?.isEmpty() == true)

}