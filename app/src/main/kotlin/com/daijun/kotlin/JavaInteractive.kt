@file:JvmName("KotlinUtils")
@file:JvmMultifileClass
// 可以使⽤ @file:JvmName 注解修改⽣成的 Java 类的类名
// 使⽤ @file:JvmMultifileClass 注解可以把同名Java类合成一个
package com.daijun.kotlin

import java.io.File
import java.io.IOException
import java.util.*

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-20
 * @description 与Java交互的一些注意事项，对应的在java中的调用，见com.daijun.java.KotlinInteractive类
 */
/**
 * 静态字段通过以下⽅式之⼀暴露出来：
 * — @JvmField 注解:给伴生对象里的属性添加@JvmField注解
 * — lateinit 修饰符:在具名对象或者伴⽣对象中的⼀个延迟初始化的属性具有与属性 setter 相同可⻅性的静态幕后字段
 * — const 修饰符: （在类中以及在顶层）以 const 声明的属性在 Java 中会成为静态字段
 *
 */
/**
 * Kotlin 将包级函数表⽰为静态⽅法
 * 为具名对象或伴⽣对象中定义的函数标注 @JvmStatic 的话,将表⽰为静态⽅法
 * 注解也可以应⽤于对象或伴⽣对象的属性，使其 getter 和 setter ⽅法在该对象或包 含该伴⽣对象的类中是静态成员
 */
class PropertyJvmField {
    // Java中可以直接访问到id这个属性
    @JvmField val id: String = ""

    // 如需在没有显式实现 getter 与 setter 的情况下更改属性⽣成的访问器⽅法的名称，
    // 可以使⽤ @get:JvmName 与 @set:JvmName ：
    @set:JvmName("addGirlFriends")
    @get:JvmName("showGirlFriends")
    var girlFriends: String = ""

    companion object {
        @JvmField
        val name: String = ""

        lateinit var sex: String

        const val MAX_COUNT = 5

        @JvmStatic val year: String = "2020"

        @JvmStatic
        fun getWeek(): String {
            return "Sunday"
        }
    }
}
object PropertyKotlin {
    lateinit var friends: String

    @JvmStatic
    fun getMonth(): String {
        return "February"
    }
}

/**
 * 在Java中调用的话，用KotlinUtils.getTime();
 */
fun getTime(): Long {
    return Date().time
}

@JvmName("getkotlinSeconds")
fun getSeconds(): String {
    return (System.currentTimeMillis() / 1000).toString()
}

/**
 * 使⽤@JvmOverloads注解向Java调⽤者暴露多个重载
 * 对于每⼀个有默认值的参数，都会⽣成⼀个额外的重载，这个重载会把这个参数和它右边的所有参数都移除掉
 * 适用于非抽象函数和接口里的函数
 */
@JvmOverloads
fun getMinutes(date: Date = Date()) = date.time / 60_000

/**
 * 想要在Java捕捉调用函数抛出的异常需要用@Throws 注解
 */
@Throws(IOException::class)
fun throwExecptionFunction() {
    val file = File("test.log")
    if (!file.exists()) {
        throw IOException()
    }
}

class Box<out T>(val value: T)

interface BaseClass

class ChildClass: BaseClass

/**
 * 如果我们在默认不⽣成通配符的地⽅需要通配符，我们可以使⽤ @JvmWildcard 注解
 * 不加@JvmWildcard注解的话，下面的函数被转换成：public Box<ChildClass> boxChildClass(ChildClass value){}
 * 加@JvmWildcard注解的话，下面的函数被转换成：public Box<? extend ChildClass> boxChildClass(ChildClass value){}
 * 这种变化可以在Java中调用的时候的代码提示里看到
 */
fun boxChildClass(value: ChildClass): Box<@JvmWildcard ChildClass> = Box(value)

fun unboxChildClass(box: Box<BaseClass>): BaseClass = box.value

/**
 * 如果我们根本不需要默认的通配符转换，我们可以使⽤ @JvmSuppressWildcards
 * 不加@JvmWildcard注解的话，下面的函数被转换成：public BaseClass boxChildClass(Box<? extend BaseClass> box){}
 * 加@JvmWildcard注解的话，下面的函数被转换成：public BaseClass boxChildClass(Box<BaseClass> box){}
 * 这种变化可以在Java中调用的时候的代码提示里看到
 */
fun unboxChildClass2(box: Box<BaseClass>): BaseClass = box.value

fun returnEmptyList() = emptyList<String>()

fun main() {
}

