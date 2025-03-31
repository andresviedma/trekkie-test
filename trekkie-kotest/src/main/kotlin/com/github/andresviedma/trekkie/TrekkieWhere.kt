package com.github.andresviedma.trekkie

@Suppress("ktlint:standard:function-naming")
inline fun <T> Where(vararg values: T, block: (T) -> Unit) {
    listOf(
        *values
    ).forEach {
        block(it)
    }
}

inline fun <T> where(vararg values: T, block: (T) -> Unit) =
    Where(*values) { block(it) }

fun x() {
    When {

    }
}
