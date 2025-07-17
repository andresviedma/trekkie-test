package io.github.andresviedma.trekkie

import io.mockk.MockKMatcherScope
import io.mockk.MockKStubScope
import io.mockk.MockKVerificationScope
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyAll
import io.mockk.coVerifyOrder
import io.mockk.coVerifySequence

// *** Alias for mockk coXxx coroutine-compatible functions to make them more readable */

fun <T> on(stubBlock: suspend MockKMatcherScope.() -> T): MockKStubScope<T, T> =
    coEvery(stubBlock)

// 1 * { mock.call(...) }
operator fun Int.times(verifyBlock: suspend MockKVerificationScope.() -> Unit) =
    coVerify(Ordering.UNORDERED, false, 1, Int.MAX_VALUE, this, 0, verifyBlock)

// (0..3) * { mock.call(...) }
operator fun ClosedRange<Int>.times(verifyBlock: suspend MockKVerificationScope.() -> Unit) =
    coVerify(Ordering.UNORDERED, false, this.start, this.endInclusive, -1, 0, verifyBlock)

// *** coVerifyXXX aliases

fun called(
    exactly: Int = -1,
    times: ClosedRange<Int> = 1..Int.MAX_VALUE,
    timeout: Long = 0,
    verifyBlock: suspend MockKVerificationScope.() -> Unit
) = coVerify(Ordering.UNORDERED, false, times.start, times.endInclusive, exactly, timeout, verifyBlock)

fun calledAll(
    inverse: Boolean = false,
    verifyBlock: suspend MockKVerificationScope.() -> Unit
) = coVerifyAll(inverse, verifyBlock)

fun calledOrder(
    inverse: Boolean = false,
    verifyBlock: suspend MockKVerificationScope.() -> Unit
) = coVerifyOrder(inverse, verifyBlock)

fun calledSequence(
    inverse: Boolean = false,
    verifyBlock: suspend MockKVerificationScope.() -> Unit
) = coVerifySequence(inverse, verifyBlock)
