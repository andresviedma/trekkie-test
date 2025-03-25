@file:Suppress("unused", "unused_parameter", "nothing_to_inline")

package com.github.andresviedma.trekkie

import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass
import kotlin.reflect.cast

/* GIVEN / WITH */
inline fun <reified T> CoroutineScope.Given(description: String = "", givenCode: () -> T) = Given1(givenCode())
inline fun <reified T> CoroutineScope.given(description: String = "", givenCode: () -> T) = Given1(givenCode())
inline fun <reified T> CoroutineScope.With(description: String = "", givenCode: () -> T) = Given1(givenCode())
inline fun <reified T> CoroutineScope.with(description: String = "", givenCode: () -> T) = Given1(givenCode())

/* GIVEN / WITH - and/when */
data class Given1 <T1> (val given1: T1)
inline infix fun <reified T> Given1<T>.and(givenCode: () -> T) = Given2(given1, givenCode())
inline fun <T1, reified T> Given1<T1>.and(description: String, givenCode: () -> T) = Given2(given1, givenCode())

suspend fun <T1, R> Given1<T1>.When(description: String = "", whenCode: (T1) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1) }

data class Given2 <T1, T2> (val given1: T1, val given2: T2)
inline infix fun <T1, T2, reified T> Given2<T1, T2>.and(givenCode: () -> T) =
    Given3(given1, given2, givenCode())
inline fun <T1, T2, reified T> Given2<T1, T2>.and(description: String, givenCode: () -> T) =
    Given3(given1, given2, givenCode())

suspend inline infix fun <T1, T2, R> Given2<T1, T2>.When(crossinline whenCode: (T1, T2) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1, given2) }
suspend inline fun <T1, T2, R> Given2<T1, T2>.When(description: String, crossinline whenCode: (T1, T2) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1, given2) }

data class Given3 <T1, T2, T3> (val given1: T1, val given2: T2, val given3: T3)
inline infix fun <T1, T2, T3, reified T> Given3<T1, T2, T3>.and(givenCode: () -> T) =
    Given4(given1, given2, given3, givenCode())
inline fun <T1, T2, T3, reified T> Given3<T1, T2, T3>.and(description: String, givenCode: () -> T) =
    Given4(given1, given2, given3, givenCode())

suspend inline infix fun <T1, T2, T3, R> Given3<T1, T2, T3>.When(crossinline whenCode: suspend (T1, T2, T3) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1, given2, given3) }

suspend inline fun <T1, T2, T3, R> Given3<T1, T2, T3>.When(description: String, crossinline whenCode: suspend (T1, T2, T3) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1, given2, given3) }

data class Given4 <T1, T2, T3, T4> (val given1: T1, val given2: T2, val given3: T3, val given4: T4)

inline infix fun <T1, T2, T3, T4, reified T> Given4<T1, T2, T3, T4>.and(givenCode: () -> T) =
    Given5(given1, given2, given3, given4, givenCode())
inline fun <T1, T2, T3, T4, reified T> Given4<T1, T2, T3, T4>.and(description: String, givenCode: () -> T) =
    Given5(given1, given2, given3, given4, givenCode())

suspend inline infix fun <T1, T2, T3, T4, R> Given4<T1, T2, T3, T4>.When(crossinline whenCode: (T1, T2, T3, T4) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1, given2, given3, given4) }
suspend inline fun <T1, T2, T3, T4, R> Given4<T1, T2, T3, T4>.When(description: String, crossinline whenCode: (T1, T2, T3, T4) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1, given2, given3, given4) }

data class Given5 <T1, T2, T3, T4, T5> (val given1: T1, val given2: T2, val given3: T3, val given4: T4, val given5: T5)

inline infix fun <T1, T2, T3, T4, T5, reified T> Given5<T1, T2, T3, T4, T5>.and(givenCode: () -> T) =
    Given6(given1, given2, given3, given4, given5, givenCode())
inline fun <T1, T2, T3, T4, T5, reified T> Given5<T1, T2, T3, T4, T5>.and(description: String, givenCode: () -> T) =
    Given6(given1, given2, given3, given4, given5, givenCode())

suspend inline infix fun <T1, T2, T3, T4, T5, R> Given5<T1, T2, T3, T4, T5>.When(crossinline whenCode: (T1, T2, T3, T4, T5) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1, given2, given3, given4, given5) }
suspend inline fun <T1, T2, T3, T4, T5, R> Given5<T1, T2, T3, T4, T5>.When(description: String, crossinline whenCode: (T1, T2, T3, T4, T5) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1, given2, given3, given4, given5) }


data class Given6 <T1, T2, T3, T4, T5, T6> (val given1: T1, val given2: T2, val given3: T3, val given4: T4, val given5: T5, val given6: T6)

suspend inline infix fun <T1, T2, T3, T4, T5, T6, R> Given6<T1, T2, T3, T4, T5, T6>.When(crossinline whenCode: (T1, T2, T3, T4, T5) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1, given2, given3, given4, given5) }
suspend inline fun <T1, T2, T3, T4, T5, T6, R> Given6<T1, T2, T3, T4, T5, T6>.When(description: String, crossinline whenCode: (T1, T2, T3, T4, T5) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(given1, given2, given3, given4, given5) }

/* Root WHEN / EXPECT */
suspend inline fun <R> CoroutineScope.When(description: String = "", crossinline whenCode: () -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode() }
suspend inline fun <R> CoroutineScope.`when`(description: String = "", crossinline whenCode: () -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode() }

suspend fun <R> CoroutineScope.Expect(description: String = "", whenCode: suspend () -> R): ThenBlock<R> =
    WhenBlock.runWhen { whenCode() }.then {}
suspend fun <R> CoroutineScope.expect(description: String = "", whenCode: suspend () -> R): ThenBlock<R> =
    WhenBlock.runWhen { whenCode() }.then {}

/* WHEN / THEN containers */
class WhenBlock<T>(val result: Result<T>) {

    companion object {
        suspend fun <T> runWhen(whenCode: suspend () -> T): WhenBlock<T> =
            WhenBlock(runCatching { whenCode() })
                .also { TrekkieListeners.processBeforeVerificationListeners() }
    }
}

inline infix fun <T> WhenBlock<T>.then(thenCode: (T) -> Unit): ThenBlock<T> {
    val value = result.getOrThrow()
    thenCode(value)
    return ThenBlock(value)
}

inline infix fun <T, reified E : Throwable>WhenBlock<T>.thenExceptionThrown(thenCode: (E) -> Unit): ThenBlock<E> =
    thenExceptionThrown(E::class, thenCode)

inline infix fun <T, E : Throwable> WhenBlock<T>.thenExceptionThrown(clazz: KClass<E>): ThenBlock<E> =
    thenExceptionThrown(clazz) {}

inline fun <T, E : Throwable> WhenBlock<T>.thenExceptionThrown(clazz: KClass<E>, thenCode: (E) -> Unit): ThenBlock<E> {
    val exception = result.exceptionOrNull()
    when {
        exception == null ->
            throw RuntimeException("An exception type ${clazz.qualifiedName} was expected, none was thrown")
        !clazz.isInstance(exception) ->
            throw exception
        else -> {
            val castedException = clazz.cast(exception)
            thenCode(castedException)
            return ThenBlock(castedException)
        }
    }
}


data class ThenBlock <T> (val result: T)

inline infix fun <T> ThenBlock<T>.and(thenCode: (T) -> Unit): ThenBlock<T> {
    thenCode(result)
    return this
}

inline fun <T> ThenBlock<T>.and(description: String, thenCode: (T) -> Unit): ThenBlock<T> {
    thenCode(result)
    return this
}

suspend inline infix fun <T, R> ThenBlock<T>.When(crossinline whenCode: (T) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(result) }
suspend inline infix fun <T, R> ThenBlock<T>.`when`(crossinline whenCode: (T) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(result) }
suspend inline fun <T, R> ThenBlock<T>.When(description: String, crossinline whenCode: (T) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(result) }
suspend inline fun <T, R> ThenBlock<T>.`when`(description: String, crossinline whenCode: (T) -> R): WhenBlock<R> =
    WhenBlock.runWhen { whenCode(result) }
