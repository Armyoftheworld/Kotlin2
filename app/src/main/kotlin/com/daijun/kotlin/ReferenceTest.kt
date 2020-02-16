package com.daijun.kotlin

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-16
 * @description
 */
// 使用::获取函数的引用
fun isOdd(x: Int) = x % 2 != 0
fun isOdd(s: String) = s.length % 2 != 0

// 函数组合
fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { x -> f(g(x)) }
}

fun length(s: String) = s.length

val rName: String = "Army"
var rAge: Int = 0

class RA(val p: Int)

fun rFunction(factory: (Int) -> RA) {
    val x = factory(10)
}

fun main() {
    val list = listOf(1, 2, 3, 4)
    list.filter(::isOdd)

    val oddLength = compose(::isOdd, ::length)
    val strs = listOf("a", "ab", "abc")
    strs.filter(oddLength)

    // 表达式 ::rName 求值为 KProperty<String> 类型的属性对象
    println("::rName.get() = ${::rName.get()}")
    println("::rName.name = ${::rName.name}")

    // 对于可变属性，::rAge 返回 KMutableProperty<Int> 类型的⼀个值，该类型有 ⼀个 set() ⽅法
    ::rAge.set(18)
    println("::rAge.get() = ${::rAge.get()}")
    println("::rAge.name = ${::rAge.name}")

    // 属性引⽤可以⽤在预期具有单个泛型参数的函数的地⽅
    println(strs.map(String::length))

    // 要访问属于类的成员的属性，我们这样限定它
    val prop = RA::p
    println(prop.get(RA(10)))

    // 构造函数引⽤
    rFunction(::RA)

    // 可以引⽤特定对象的实例⽅法
    val regex = "\\d+".toRegex()
    val isNumber: (CharSequence) -> Boolean = regex::matches
    println(isNumber("29"))

    strs.filter(isNumber)

    // ⽐较绑定的类型(isNumber)和相应的未绑定类型的引⽤(matches)。
    // 绑定的可调⽤引⽤有其接收者“附加”到其上，因此接收者 的类型不再是参数
    val matches: (Regex, CharSequence) -> Boolean = Regex::matches

}