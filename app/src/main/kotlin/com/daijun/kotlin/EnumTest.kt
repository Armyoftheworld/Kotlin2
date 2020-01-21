package com.daijun.kotlin

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-01-21
 * @description
 */

enum class Color(val rgb: Int) {
    RED(0xFF0000), GREEN(0x00FF00), BLUE(0x0000FF)
}

enum class Language {

    CHINESE {
        override fun code() = "CN"
    },

    ENGLISH {
        override fun code() = "EN"

    };

    abstract fun code(): String
}

enum class Children : People, Fruit {

    XIAOMING {
        override fun fruitName() = "apple"
    },

    XIAOHONG {
        override fun fruitName() = "orange"

    };

    override fun eat() = fruitName()
}

inline fun <reified T: Enum<T>> printAllValues() {
    println(enumValues<T>().joinToString{ it.name })
}

fun main() {
    printAllValues<Color>()
}

interface People {
    fun eat(): String
}

interface Fruit {
    fun fruitName(): String
}