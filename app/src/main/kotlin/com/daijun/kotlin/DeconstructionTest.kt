package com.daijun.kotlin

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-14
 * @description 解构
 */

class Student {
    var name = ""
    var age = 18
    var sex = "male"

    operator fun component1() = name

    operator fun component2() = age

    operator fun component3() = sex
}

/**
 * 数据类会⾃动声明 componentN() 函数
 */
data class Teacher(var name: String, var age: Int, var sex: String)

fun main() {
    val student = Student()
    // 要想使用解构，必须要有componentN函数
    val (name, age) = student
    println("name = $name, age = $age")

    val teacher = Teacher("army", 18, "female")
    // 按照声明的顺序调用componentN() 函数
    val (teacherAge, teacherName) = teacher
    println("teacherAge = $teacherAge, teacherName = $teacherName")

    val maps = mapOf("name" to "Army", "age" to "18")
    for ((key, value) in maps) {
        // ...
    }

    // 下划线⽤于未使⽤的变量
    maps.mapValues { (_, value) -> value }

    var a = ""
    a += ""

}