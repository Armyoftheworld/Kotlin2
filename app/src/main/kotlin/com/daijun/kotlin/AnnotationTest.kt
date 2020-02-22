package com.daijun.kotlin

import com.daijun.java.JavaAnno

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-16
 * @description
 */

@Target(AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
annotation class PrintLog

@Target(AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogAnno

// 这个PrintLog注解回家到lambda表达式的invoke方法上
val lambdaObj = @PrintLog { x: Int, y: Int -> x + y }

class AnnotationTest(@field:PrintLog var name: String,
                     @get:PrintLog var age: Int,
                     @param:PrintLog var sex: String) {
    // 多个注解可以这样写
    @[LogAnno PrintLog]
    var friends: String = ""

    @set:[LogAnno PrintLog]
    var familys: String = ""

    @JavaAnno(names = ["nancy", "amy"])
    var loves: String = ""
}



