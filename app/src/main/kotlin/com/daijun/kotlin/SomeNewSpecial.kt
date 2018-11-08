package com.daijun.kotlin


/**
 * author：daijun
 * time：2018/11/8
 * description：kotlin升级的一些新特性
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestAnno(val array: Array<String>)

//注解的数组参数可以使用新的数组常量语法而不是 arrayOf 函数来传递
@TestAnno(["1", "2"])
data class Person(val name: String = "", val age: Int = 0)

class TestClass {
    lateinit var map: MutableMap<String, String>

    fun func() {
        //必须要用this::map这样调用
        println("isInitialized = " + this::map.isInitialized)
        map = mutableMapOf()
        println("isInitialized = " + this::map.isInitialized)
    }
}

//内联函数现在允许其内联函式数参数具有默认值
inline fun <E> Iterable<E>.strings(transform: (E) -> String = { it.toString() }) = map { transform(it) }

//当一个变量有安全调用表达式与空检测赋值时，其智能转换现在也可以应用于安全调用接收者
fun countFirst(s: Any): Int {
    val firstChar = (s as? CharSequence)?.firstOrNull()
    if (firstChar != null) {
        //这里的s智能转换成CharSequence
        return s.count { it == firstChar }
    }

    val firstItem = (s as? Iterable<*>)?.firstOrNull()
    if (firstItem != null) {
        //这里的s智能转换成Iterable
        return s.count { it == firstItem }
    }
    return -1
}

fun listExtend() {
    val items = (1..5).toMutableList()
    //把元素打乱，洗牌的意思
    items.shuffle()
    println("item shuffle to $items")
    //所以元素都替换
    items.replaceAll { it * 2 }
    println("item replaceAll to $items")
    //把集合用5填充
    items.fill(5)
    println("item fill to $items")

    val readOnlyList = listOf(5, 6, 7, 8, 9)
    println("readOnlyList shuffled to ${readOnlyList.shuffled()}")
}

fun main(args: Array<String>) {
    //lateinit 修饰符现在可以用在顶级属性和局部变量上
    lateinit var list: List<Int>
    var testClass = TestClass()
    testClass.func()

    list = listOf(1, 2, 3)
    println(list.strings {
        "value = $it"
    })

    val strings = "abcabca"
    println(countFirst(strings))

    val items = listOf(1, 2, 3, 1, 2, 3)
    println(countFirst(items))

    listExtend()

}







