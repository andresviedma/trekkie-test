package io.github.andresviedma.trekkie

data class Tuple2 <T1, T2>(val v1: T1, val v2: T2)
data class Tuple3 <T1, T2, T3>(val v1: T1, val v2: T2, val v3: T3)
data class Tuple4 <T1, T2, T3, T4>(val v1: T1, val v2: T2, val v3: T3, val v4: T4)
data class Tuple5 <T1, T2, T3, T4, T5>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5)
data class Tuple6 <T1, T2, T3, T4, T5, T6>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6)
data class Tuple7 <T1, T2, T3, T4, T5, T6, T7>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5, val v6: T6, val v7: T7)

fun <T1, T2> row(v1: T1, v2: T2) = Tuple2(v1, v2)
fun <T1, T2, T3> row(v1: T1, v2: T2, v3: T3) = Tuple3(v1, v2, v3)
fun <T1, T2, T3, T4> row(v1: T1, v2: T2, v3: T3, v4: T4): Tuple4<T1, T2, T3, T4> = Tuple4(v1, v2, v3, v4)
fun <T1, T2, T3, T4, T5> row(v1: T1, v2: T2, v3: T3, v4: T4, v5: T5) = Tuple5(v1, v2, v3, v4, v5)
fun <T1, T2, T3, T4, T5, T6> row(v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6) = Tuple6(v1, v2, v3, v4, v5, v6)
fun <T1, T2, T3, T4, T5, T6, T7> row(v1: T1, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6, v7: T7) = Tuple7(v1, v2, v3, v4, v5, v6, v7)
