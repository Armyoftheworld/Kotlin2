package com.daijun.kotlin

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-09
 * @description 集合
 */

fun main() {
    val list = listOf(1, 2, 3, 4, 5)
    var listIterator = list.listIterator()
    while (listIterator.hasNext()) {
        println("listIterator.nextIndex() = ${listIterator.nextIndex()}")
        println("listIterator.next() = ${listIterator.next()}")
    }
    while (listIterator.hasPrevious()) {
        println("listIterator.previousIndex() = ${listIterator.previousIndex()}")
        println("listIterator.previous() = ${listIterator.previous()}")
    }
}