package com.daijun.kotlin

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-03
 * @description 嵌套类和内部类
 */

class OuterClass {
    private val bar = 1

    /**
     * 嵌套类不能访问外部类的属性
     */
    class NestedClass {
        fun foo() = "nested"
    }

    /**
     * 标记为 inner 的嵌套类能够访问其外部类的成员。内部类会带有一个对外部类的对象的引用
     */
    inner class InnerClass {
        fun foo() = "inner $bar"
    }
}

fun main() {
    val nest = OuterClass.NestedClass()
    println(nest.foo())

    val innerclz = OuterClass().InnerClass()
    println(innerclz.foo())
}