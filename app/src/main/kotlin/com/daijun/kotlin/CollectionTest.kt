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

    println("--------------------------------序列--------------------------------")

    // 这是个无限的序列, 不能调用count方法
    var sequence = generateSequence(1) { it + 2 }
    sequence.take(5).toList().forEach {
        print("$it \t")
    }
    println()

    // 想要创建个有限的序列，请提供⼀个函数，该函数在需要的最后⼀个元素之后返回 null
    sequence = generateSequence(1) { if (it < 10) it + 2 else null }
    sequence.forEach {
        print("$it \t")
    }
    println()

    // 通过 yield() 与 yieldAll() 方法生成序列
    sequence = kotlin.sequences.sequence {
        yield(2)
        yieldAll(listOf(4, 6, 8))
        yieldAll(generateSequence(10) { it + 2 })
    }
    sequence.take(10).forEach {
        print("$it \t")
    }
    println()
    println("--------------------------------序列与集合的比较--------------------------------")
    compareSequenceIterate()

    println("--------------------------------集合的操作--------------------------------")
    // 对于某些集合操作，有⼀个选项可以指定⽬标对象。 ⽬标是⼀个可变集合，该函数将其结果项附加到该
    //可变对象中，⽽不是在新对象中返回它们
    val numbers = listOf("one", "two", "three", "four")
    val filterResult = mutableListOf<String>()
    numbers.filterTo(filterResult) { it.length > 3 }
    numbers.filterIndexedTo(filterResult) { index, _ -> index == 0 }
    // 两次filter的结果都在filterResult里
    println(filterResult)

    val nums = setOf(1, 2, 3)
    println(nums.mapNotNull { if (it == 2) null else it * 3 })
    println(nums.mapIndexedNotNull { index, i -> if (index == 0) null else index * i })

    // 如果集合的⼤⼩不同，则zip() 的结果为较⼩集合的⼤⼩；结果中不包含较⼤集合的后续元素
    val colors = listOf("red", "brown", "grey")
    val animals = listOf("fox", "bear", "wolf")
    val zipResult = colors zip animals
    println(zipResult)
    println(colors.zip(animals) { color, animal -> "The ${animal.capitalize()} is $color" })

    println(zipResult.unzip())

    // 基本的关联函数 associateWith() 创建⼀个 Map，
    // 其中原始集合的元素是键，并通过给定的转换函数从中产⽣值
    println(colors.associateWith { it.length })
    // associateBy() 根据元素的值返回键
    println(colors.associateBy { it.first().toUpperCase() })
    println(colors.associateBy({ item -> item.toUpperCase() }, { item -> item.repeat(2) }))
    println(colors.associate { it.capitalize() to it.repeat(3) })

    // 打平，只能打平一级
    val numberSets = listOf(setOf(1, 2, 3), setOf(4, 5), setOf(6))
    println("打平结果：${numberSets.flatten()}")
    val deepSets = listOf(setOf(setOf(1)), setOf(setOf(2, 3)), setOf(setOf(4, 5, 6))) // [1, 2, 3, 4, 5, 6]
    println("打平结果2：${deepSets.flatten()}") // [[1], [2, 3], [4, 5, 6]]

    println(numbers.joinToString(separator = "|", prefix = "start: ", postfix = " :end"))

    // filterIsInstance()通过过滤给定类型的元素来缩⼩元素的类型
    val objs = listOf(null, 1, "str", true, 1.1)
    println(objs.filterIsInstance<String>())

    // partition() ‒ 通过⼀个谓词过滤集合并且将不匹配的元素存放在⼀个单独的列表中
    val (match, other) = numbers.partition { it.length > 3 }
    println("partition match = $match, other = $other")
}

fun compareSequenceIterate() {
    // 假定有⼀个单词列表。下⾯的代码过滤⻓于三个字符的单词，并打印前四个单词的⻓度
    val words = "The quick brown fox jumps over the lazy dog".split(" ")
    val result = words.filter {
        println("iterate filter $it")
        it.length > 3
    }.map {
        println("iterate map ${it.length}")
        it.length
    }.take(4)
    print("Lengths of first 4 words longer than 3 chars:")
    println(result)

    val sequenceResult = words.asSequence().filter {
        println("sequence filter $it")
        it.length > 3
    }.map {
        println("sequence map ${it.length}")
        it.length
    }.take(4) // 当结果⼤⼩达到 4 时，处理将停⽌，因为它是 take(4) 可以返回的最⼤⼤⼩
    println("Lengths of first 4 words longer than 3 chars:")
    // 只能通过末端操作(toList)才能检索序列元素
    println("sequenceResult: ${sequenceResult.toList()}")
//    sequenceResult.forEach {
//        println("result: $it \t")
//    }
}

class MyList(override val size: Int) : AbstractList<String>() {
    override fun get(index: Int): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}