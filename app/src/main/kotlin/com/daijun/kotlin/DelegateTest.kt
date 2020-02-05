package com.daijun.kotlin

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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

class Delegate {

    /**
     * @param thisRef 持有该变量的对象, 如果是一个变量的属性被委托，那thisRef就是该变量
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

/**
 * 只读的map只能委托给只读属性，
 * 可写的map能委托给只读和可写的属性
 */
class User(map: Map<String, Any?>, mutableMap: MutableMap<String, Any?>) {
    val name: String by map
    val age: Int by map
    var sex: String by mutableMap
}

class ReadOnlyDelegate: ReadOnlyProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }
}

class ReadWriteDelegate: ReadWriteProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

fun main() {
    val b = BaseImpl(10)
    val d = Derived(b)
    d.printMsg()
    d.printMessage()

    var str: String by Delegate()
    println(str)
    str = "newValue"

    /**
     * lazy默认是线程安全的
     */
    val lazyStr: String by lazy {
        println("in SYNCHRONIZED lazy")
        "lazy value"
    }
    println(lazyStr)
    println(lazyStr)

    // 可观察属性 Observable
    var observableAttr: String by Delegates.observable("initValue") { prop, oldValue, newValue ->
        println("${prop.name}的值从$oldValue 变成 $newValue")
    }
    observableAttr = "first value"
    observableAttr = "second value"

    // 把属性储存在映射中
    // 如果某个属性在map里面没有，如果不访问此属性，也不会报错
    val user = User(
        mapOf(
            "name" to "Army",
            "age" to 18
        ),
        mutableMapOf("sex" to "female")
    )
    println(user.name)
    println(user.age)
    println(user.sex)
}
