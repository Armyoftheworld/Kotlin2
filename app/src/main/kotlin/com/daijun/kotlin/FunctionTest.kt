package com.daijun.kotlin

import android.app.Activity
import android.view.View
import android.widget.LinearLayout

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/2/6
 * @description
 */
/**
 * 可以实现函数类型接口
 */
class IntTransform : (Int) -> Int {
    override fun invoke(p1: Int): Int {
        return p1 * p1
    }
}

val intFunction: (Int) -> Int = IntTransform()

// 带与不带接收者的函数类型非字面值可以互换，其中接收者可以替代第一个参数，反之亦然。
// 例如，(A, B) -> C 类型的值可以传给或赋值给期待 A.(B) -> C 的地方，反之亦然
val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }
val twoParameters: (String, Int) -> String = repeatFun

fun runTransformation(f: (String, Int) -> String): String {
    return f("hello", 3)
}

val result = runTransformation(repeatFun)

val stringPlus: (String, String) -> String = String::plus
val intPlus: Int.(Int) -> Int = Int::plus

fun main() {
    repeatFun("1", 1)

    stringPlus.invoke("Hello", "world")
    stringPlus("Hello", "world")

    intPlus.invoke(1, 1)
    intPlus(1, 1)
    1.intPlus(1)

    val list = mutableListOf(1, 2, 3)
    // 下面两个lambda表达式的是一样的
    list.filter {
        it > 0
    }
    list.filter {
        return@filter it > 0
    }
    // 匿名函数
    list.filter(fun(item: Int): Boolean {
        return item > 0
    })
    // 上面的匿名函数可以简写成如下
    list.filter(fun(item) = item > 0)

    // 匿名函数语法允许你直接指定函数字面值的接收者类型
    val sum = fun Int.(other: Int): Int = this + other

    android {
        defaultConfig()
    }

    // 我们使用 reified 修饰符来限定类型参数
    val view = View(Activity())
    view.findParentOfType<LinearLayout>()

    // takeIf 及 takeUnless 与作⽤域函数⼀起特别有⽤。
    // ⼀个很好的例⼦是⽤ let 链接它们，以便 在与给定谓词匹配的对象上运⾏代码块
    list.takeIf { it.size > 0 }?.let {
        println(it)
    }
}

class Android {
    fun defaultConfig() {
        println("this is body")
    }
}

fun android(init: Android.() -> Unit): Android {
    val android = Android()
    android.init()
    return android
}

// 具体化的类型参数, 内联函数支持 具体化的类型参数
// 我们使用 reified 修饰符来限定类型参数，现在可以在函数内部访问它了， 几乎就像是一个普通的类一样
inline fun <reified T> View.findParentOfType(): T? {
    var p = parent
    while (p !is T) {
        p = p.parent
    }
    return p as T?
}

