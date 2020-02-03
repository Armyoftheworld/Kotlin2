package com.daijun.kotlin
/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-03
 * @description 内联类，相当于类的包装器
 */
/**
 * 内联类必须含有唯一的一个属性在主构造函数中初始化
 * 只能实现接口，不能继承类，也不能被继承
 */
inline class InlineClass(val s: String): Printable {
    override fun prettyPrint() = "This is prettyPrint"

    val length: Int
    get() = s.length

    fun greet() {
        println("hello, $s")
    }
}

interface Printable {
    fun prettyPrint(): String
}

fun asInline(i: InlineClass) {}
fun <T> asGeneric(t: T){}
fun asInterface(p: Printable) {}
fun asNullable(i: InlineClass?) {}

fun <T> id(t: T): T = t

fun main() {
    val a = InlineClass("12")
    println(a)

    // ⼀般来说，只要将内联类⽤作另⼀种类型，它们就会被装箱
    asInline(a)     // 拆箱操作，用做InlineClass本身
    asGeneric(a)    // 装箱操作，⽤作泛型类型 T
    asInterface(a)  // 装箱操作，⽤作类型 Printable
    asNullable(a)   // 装箱操作，⽤作不同于 InlineClass 的可空类型 InlineClass?

    id(a) // 先装箱操作，⽤作泛型类型 T，后拆箱操作，⽤作泛型类型 T本身
}