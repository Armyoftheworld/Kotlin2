package com.daijun.kotlin

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-01-21
 * @description 对象表达式与对象声明
 */

open class A(val x: Int) {
    open val y = x
}

interface B {
    fun sayHello()
}

// 如果超类型有⼀个构造函数，则必须传递适当的构造函数参数给它
val ab: A = object : A(1), B {

    override val y = 15

    override fun sayHello() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// 如果我们只需要“⼀个对象⽽已”，并不需要特殊超类型
fun function1() {
    val obj = object {
        var x = 0
        var y = 0
    }
    println(obj.x + obj.y)
}

/**
 * 匿名对象可以⽤作只在本地和私有作⽤域中声明的类型。
 * 如果你使⽤匿名对象作为公有函数 的返回类型或者⽤作公有属性的类型，
 * 那么该函数或属性的实际类型会是匿名对象声明的超类型，
 * 如果 你没有声明任何超类型，就会是 Any
 */
class C {

    // 私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object {
        var x = 0
    }

    // 公有函数，所以其返回类型是 Any
    fun publicFoo() = object {
        var x = 0
    }

    fun bar() {
        println(foo().x)
//        println(publicFoo().x)  错误，因为Any类型没有x属性
    }
}

/**
 * 对象声明
 */
object ObjectProvider : B {

    val x = 0

    override fun sayHello() {
    }

}

class D {
    // 伴⽣对象可以继承类和实现接口
    companion object : A(1), B {

        override val y: Int = 6

        override fun sayHello() {
        }

    }
}

fun main() {
    println(ObjectProvider.x)
    ObjectProvider.sayHello()
}