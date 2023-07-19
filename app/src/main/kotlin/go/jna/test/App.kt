/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package go.jna.test

import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import com.sun.jna.Pointer
import com.sun.jna.Structure
import java.awt.Point


@Structure.FieldOrder("p", "n")
open class GoString : Structure() {
    @JvmField
    var p: String = ""
    @JvmField
    var n: Long = 0

    class ByValue : GoString, Structure.ByValue {
        constructor()
        constructor(s: String) {
            p = s
            n = s.toByteArray().size.toLong()
        }
    }
}

@Structure.FieldOrder("a", "b")
open class Simple : Structure() {
    @JvmField
    var a: Int = 0
    @JvmField
    var b: Int = 0

    class ByValue() : Simple(), Structure.ByValue {
    }
}

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

external fun Hello(msg: GoString.ByValue): Pointer
external fun GetFuncDecl(): Pointer
external fun GetFuncDeclName(ptr: Pointer): Pointer
external fun GetIdentName(ptr: Pointer): GoString.ByValue

fun main() {
    Native.register(NativeLibrary.getInstance("awesome"))

    val goStr = GoString.ByValue("jna äää demo")

    var ptr: Pointer? = null
    try {
        ptr = Hello(goStr)
        println(ptr.getString(0, "utf-8"))
    } finally {
        if (ptr != null) {
            Native.free(Pointer.nativeValue(ptr))
        }
    }

    val decl = GetFuncDecl()
    val id = GetFuncDeclName(decl)
    println(GetIdentName(id).p)
}