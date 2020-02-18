package com.daijun.kotlin

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-18
 * @description
 */

// 作⽤域控制,只允许调⽤ 最近层的接收者的成员
@DslMarker
annotation class HtmlTagMarker

interface Element {
    fun render(builder: StringBuilder, indent: String)
}

class TextElement(val text: String) : Element {
    override fun render(builder: StringBuilder, indent: String) {
        builder.append(indent).append(text).append("\n")
    }
}

@HtmlTagMarker
abstract class Tag(val name: String) : Element {
    val children = arrayListOf<Element>()
    val attributes = hashMapOf<String, String>()

    protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent<$name${renderAttributes()}>\n")
        for (child in children) {
            child.render(builder, "$indent  ")
        }
        builder.append("$indent<$name>\n")
    }

    private fun renderAttributes(): String {
        val builder = StringBuilder()
        for ((name, value) in attributes) {
            builder.append(""" $name="$value"""")
        }
        return builder.toString()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder, "")
        return builder.toString()
    }
}

abstract class TagWithText(name: String) : Tag(name) {
    operator fun String.unaryPlus() {
        children.add(TextElement(this))
    }
}

class Html : TagWithText("html") {
    fun head(init: Head.() -> Unit) = initTag(Head(), init)

    fun body(init: Body.() -> Unit) = initTag(Body(), init)

}

class Title : TagWithText("title")

class Head : TagWithText("head") {
    fun title(init: Title.() -> Unit) = initTag(Title(), init)
}

abstract class BodyTag(name: String) : TagWithText(name) {
    fun b(init: B.() -> Unit) = initTag(B(), init)
    fun p(init: P.() -> Unit) = initTag(P(), init)
    fun h1(init: H1.() -> Unit) = initTag(H1(), init)
    fun a(href: String, init: A.() -> Unit) {
        val a = initTag(A(), init)
        a.href = href
    }
}

class Body : BodyTag("body")
class B : BodyTag("b")
class P : BodyTag("p")
class H1 : BodyTag("h1")

class A : BodyTag("a") {
    var href: String
        get() = attributes["href"]!!
        set(value) {
            attributes["href"] = value
        }
}

fun html(init: Html.() -> Unit) = Html().apply {
    init()
}

fun main() {
    html {
        head {
            title {
                +"XML encoding with Kotlin"
            }
        }
        body {
            h1 {
                +"XML encoding with Kotlin"
            }
            p {
                +"this format can be used as an alternative markup to XML"
            }
            a("http://kotlinlang.org") {
                +"Kotlin"
            }
            p {
                +"This is some"
                b { +"mixed" }
                +"text. For more see the"
                a(href = "http://kotlinlang.org") { +"Kotlin" }
                +"project"
            }
            p { +"some text" }
        }
    }.let(::println)
}