@file:JvmName("KotlinUtils")
@file:JvmMultifileClass
// 可以使⽤ @JvmName 注解修改⽣成的 Java 类的类名
// 使⽤ @JvmMultifileClass 注解可以把同名Java类合成一个
package com.daijun.kotlin

import java.util.*

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-20
 * @description
 */

fun getDate(): String {
    return Date().toString()
}