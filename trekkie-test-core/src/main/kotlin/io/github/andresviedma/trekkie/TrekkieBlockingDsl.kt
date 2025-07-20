@file:Suppress("unused", "unused_parameter", "nothing_to_inline", "FunctionName")

package io.github.andresviedma.trekkie

import kotlin.reflect.KClass
import kotlin.reflect.cast

/* GIVEN / WITH */
inline fun <reified T> Given(givenCode: () -> T) = BlockingGiven1(givenCode())
inline fun <reified C, T> Given(context: C, givenCode: C.() -> T) = BlockingGiven1(context.givenCode())
inline fun <reified T> given(givenCode: () -> T) = BlockingGiven1(givenCode())
inline fun <reified C, T> given(context: C, givenCode: C.() -> T) = BlockingGiven1(context.givenCode())

inline fun <reified T> With(description: String = "", givenCode: () -> T) = BlockingGiven1(givenCode())
inline fun <reified T> with(description: String = "", givenCode: () -> T) = BlockingGiven1(givenCode())

/* GIVEN / WITH - and/when */
data class BlockingGiven1 <T1> (val given1: T1) {
    inline infix fun <reified T> and(givenCode: () -> T) = BlockingGiven2(given1, givenCode())
    inline fun <reified T> and(description: String, givenCode: () -> T) = BlockingGiven2(given1, givenCode())

    fun <R> When(description: String = "", whenCode: (T1) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1) }
}

data class BlockingGiven2 <T1, T2> (val given1: T1, val given2: T2) {
    inline infix fun <reified T> and(givenCode: () -> T) =
        BlockingGiven3(given1, given2, givenCode())
    inline fun <reified T> and(description: String, givenCode: () -> T) =
        BlockingGiven3(given1, given2, givenCode())

    inline infix fun <R> When(crossinline whenCode: (T1, T2) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1, given2) }
    fun <R> When(description: String, whenCode: (T1, T2) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1, given2) }
}

data class BlockingGiven3 <T1, T2, T3> (val given1: T1, val given2: T2, val given3: T3) {
    inline infix fun <reified T> and(givenCode: () -> T) =
        BlockingGiven4(given1, given2, given3, givenCode())
    inline fun <T1, T2, T3, reified T> and(description: String, givenCode: () -> T) =
        BlockingGiven4(given1, given2, given3, givenCode())

    inline infix fun <R> When(crossinline whenCode: (T1, T2, T3) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1, given2, given3) }

    fun <R> When(description: String, whenCode: (T1, T2, T3) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1, given2, given3) }
}

data class BlockingGiven4 <T1, T2, T3, T4> (val given1: T1, val given2: T2, val given3: T3, val given4: T4) {
    inline infix fun <reified T> and(givenCode: () -> T) =
        BlockingGiven5(given1, given2, given3, given4, givenCode())
    inline fun <T1, T2, T3, T4, reified T> and(description: String, givenCode: () -> T) =
        BlockingGiven5(given1, given2, given3, given4, givenCode())

    inline infix fun <R> When(crossinline whenCode: (T1, T2, T3, T4) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1, given2, given3, given4) }
    fun <R> When(description: String, whenCode: (T1, T2, T3, T4) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1, given2, given3, given4) }
}

data class BlockingGiven5 <T1, T2, T3, T4, T5> (val given1: T1, val given2: T2, val given3: T3, val given4: T4, val given5: T5) {
    inline infix fun <reified T> and(givenCode: () -> T) =
        BlockingGiven6(given1, given2, given3, given4, given5, givenCode())
    inline fun <T1, T2, T3, T4, T5, reified T> and(description: String, givenCode: () -> T) =
        BlockingGiven6(given1, given2, given3, given4, given5, givenCode())

    inline infix fun <R> When(crossinline whenCode: (T1, T2, T3, T4, T5) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1, given2, given3, given4, given5) }
    fun <R> When(description: String, whenCode: (T1, T2, T3, T4, T5) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1, given2, given3, given4, given5) }
}

data class BlockingGiven6 <T1, T2, T3, T4, T5, T6> (val given1: T1, val given2: T2, val given3: T3, val given4: T4, val given5: T5, val given6: T6) {
    inline infix fun <R> When(crossinline whenCode: (T1, T2, T3, T4, T5) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1, given2, given3, given4, given5) }
    fun <R> When(description: String, whenCode: (T1, T2, T3, T4, T5) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(given1, given2, given3, given4, given5) }
}

/* Root WHEN / EXPECT */
fun <R> When(description: String = "", whenCode: () -> R): BlockingWhenBlock<R> =
    BlockingWhenBlock.runWhen { whenCode() }
fun <R> `when`(description: String = "", whenCode: () -> R): BlockingWhenBlock<R> =
    BlockingWhenBlock.runWhen { whenCode() }

fun <R> Expect(description: String = "", whenCode: () -> R): BlockingThenBlock<R> =
    BlockingWhenBlock.runWhen { whenCode() }.then {}
fun <R> expect(description: String = "", whenCode: () -> R): BlockingThenBlock<R> =
    BlockingWhenBlock.runWhen { whenCode() }.then {}

/* WHEN / THEN containers */
class BlockingWhenBlock<T>(val result: Result<T>) {

    inline infix fun then(thenCode: (T) -> Unit): BlockingThenBlock<T> {
        val value = result.getOrThrow()
        thenCode(value)
        return BlockingThenBlock(value)
    }

    inline infix fun <reified E : Throwable> thenExceptionThrown(noinline thenCode: (E) -> Unit): BlockingThenBlock<E> =
        thenExceptionThrown(E::class, thenCode)

    inline infix fun <E : Throwable> thenExceptionThrown(clazz: KClass<E>): BlockingThenBlock<E> =
        thenExceptionThrown(clazz) {}

    fun <E : Throwable> thenExceptionThrown(clazz: KClass<E>, thenCode: (E) -> Unit): BlockingThenBlock<E> {
        val exception = result.exceptionOrNull()
        when {
            exception == null ->
                throw RuntimeException("An exception type ${clazz.qualifiedName} was expected, none was thrown")
            !clazz.isInstance(exception) ->
                throw exception
            else -> {
                val castedException = clazz.cast(exception)
                thenCode(castedException)
                return BlockingThenBlock(castedException)
            }
        }
    }

    companion object {
        fun <T> runWhen(whenCode: () -> T): BlockingWhenBlock<T> =
            BlockingWhenBlock(runCatching { whenCode() })
                .also { TrekkieListeners.processBeforeVerificationListenersBlocking() }
    }
}

data class BlockingThenBlock <T> (val result: T) {
    inline infix fun and(thenCode: (T) -> Unit): BlockingThenBlock<T> {
        thenCode(result)
        return this
    }

    fun and(description: String, thenCode: (T) -> Unit): BlockingThenBlock<T> {
        thenCode(result)
        return this
    }

    inline infix fun <R> andWhen(crossinline whenCode: (T) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(result) }
    inline infix fun <R> When(crossinline whenCode: (T) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(result) }
    inline infix fun <R> `when`(crossinline whenCode: (T) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(result) }
    fun <R> When(description: String, whenCode: (T) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(result) }
    fun <R> `when`(description: String, whenCode: (T) -> R): BlockingWhenBlock<R> =
        BlockingWhenBlock.runWhen { whenCode(result) }
}
