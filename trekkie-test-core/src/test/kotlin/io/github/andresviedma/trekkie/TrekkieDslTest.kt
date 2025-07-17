package io.github.andresviedma.trekkie

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Suppress("ConstantConditionIf")
class TrekkieDslTest {
    @Test
    fun `Successful test with multiple givens and withs - with-when with comments`() = runTest {
        val givenChecks = (0..5).map { false }.toMutableList()
        var result: String? = null
        var and1Check = false
        var and2Check = false

        // Test
        Given("a given condition") {
            suspend { givenChecks[0] = true }()
        }.and("other condition") {
            givenChecks[1] = true
        } and {
            givenChecks[2] = true
        } and {
            suspend { givenChecks[3] = true }()
        } and {
            givenChecks[4] = true
        } and {
            givenChecks[5] = true
        }

        With("a value") {
            suspend { 1 }()
        }.and("another") {
            "other"
        }.and("more") {
            suspend { 3 }()
        }.When("something is run") { number, string, number2 ->
            delay(10)
            suspend { "OK-${number + 1}-${string.length}-$number2" }()
        }.then {
            suspend { result = it }()
        }.and("more checks") {
            suspend { and1Check = true }()
        } and {
            and2Check = true
        }

        // Test validations
        assertEquals(result, "OK-2-5-3")
        assertTrue(givenChecks.all { it })
        assertTrue(and1Check)
        assertTrue(and2Check)
    }

    @Test
    fun `Successful test with multiple givens and withs - with-when with no comments`() = runTest {
        var result: String? = null
        var and1Check = false

        With {
            suspend { 1 }()
        } and {
            suspend { 3 }()
        } When { number, number2 ->
            suspend { "OK-${number + 1}-$number2" }()
        } then {
            suspend { result = it }()
        } and {
            suspend { and1Check = true }()
        }

        // Test validations
        assertEquals(result, "OK-2-3")
        assertTrue(and1Check)
    }

    @Test
    fun `Successful test with multiple givens and withs - when with no comments`() = runTest {
        var result: Int? = null

        When {
            suspend { 1 }()
        } then {
            suspend { result = it }()
        }

        // Test validations
        assertEquals(result, 1)
    }

    @Test
    fun `Successful test with multiple givens and withs - when with comments`() = runTest {
        var result: Int? = null
        var and1Check = false

        When("with description") {
            suspend { 1 }()
        }.then("with description") {
            suspend { result = it }()
        }.and("more") {
            suspend { and1Check = true }()
        }

        // Test validations
        assertEquals(result, 1)
        assertTrue(and1Check)
    }

    @Test
    fun `Test expecting successful fails validation in then`() = runTest {
        val result = runCatching {
            When {
                "OK"
            } then {
                assertEquals("OK2", it)
            }
        }

        assertTrue(result.isFailure)
        assertEquals("AssertionFailedError", result.exceptionOrNull()!!::class.simpleName)
    }

    @Test
    fun `Test expecting successful fails validation in then-and`() = runTest {
        val result = runCatching {
            When {
                "OK"
            } then {
                assertEquals("OK", it)
            } and {
                assertEquals("OK2", it)
            }
        }

        assertTrue(result.isFailure)
        assertEquals("AssertionFailedError", result.exceptionOrNull()!!::class.simpleName)
    }

    @Test
    fun `Test expecting successful receives an exception`() = runTest {
        val result = runCatching {
            When {
                if (true) error("boom")
                "OK"
            } then {
                assertEquals("OK", it)
            }
        }

        assertTrue(result.isFailure)
        assertEquals("IllegalStateException", result.exceptionOrNull()!!::class.simpleName)
    }

    @Test
    fun `Test expecting exception receives it`() = runTest {
        val result = runCatching {
            When {
                if (true) error("boom")
                "OK"
            } thenExceptionThrown(IllegalStateException::class)
        }

        assertTrue(result.isSuccess)
    }

    @Test
    fun `Test expecting exception receives it, with additional successful validation`() = runTest {
        val result = runCatching {
            When {
                if (true) error("boom")
                "OK"
            } thenExceptionThrown { exception: IllegalStateException ->
                suspend { assertEquals("boom", exception.message) }()
            }
        }

        assertTrue(result.isSuccess)
    }

    @Test
    fun `Test expecting exception receives it, with additional failing validation`() = runTest {
        val result = runCatching {
            When {
                if (true) error("boom")
                "OK"
            } thenExceptionThrown { exception: IllegalStateException ->
                suspend { assertEquals("boomXXX", exception.message) }()
            }
        }

        assertTrue(result.isFailure)
        assertEquals("AssertionFailedError", result.exceptionOrNull()!!::class.simpleName)
    }

    @Test
    fun `Test expecting exception receives a different exception`() = runTest {
        val result = runCatching {
            When {
                if (true) error("boom")
                "OK"
            } thenExceptionThrown(IndexOutOfBoundsException::class)
        }

        assertTrue(result.isFailure)
        assertEquals("IllegalStateException", result.exceptionOrNull()!!::class.simpleName)
    }

    @Test
    fun `Test expecting exception with additional code receives a different exception`() = runTest {
        val result = runCatching {
            When {
                if (true) error("boom")
                "OK"
            } thenExceptionThrown { _: IndexOutOfBoundsException ->
            }
        }

        assertTrue(result.isFailure)
        assertEquals(IllegalStateException::class, result.exceptionOrNull()!!::class)
    }

    @Test
    fun `Test Expect with success`() = runTest {
        val result = runCatching {
            Expect {
                "OK"
            }
        }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Test Expect with error`() = runTest {
        val result = runCatching {
            Expect {
                error("boom")
            }
        }
        assertTrue(result.isFailure)
        assertEquals(IllegalStateException::class, result.exceptionOrNull()!!::class)
    }

    @Test
    fun `Test Expect with error in and`() = runTest {
        val result = runCatching {
            Expect {
                suspend { "OK" }()
            } and {
                suspend { error("boom") }()
            }
        }
        assertTrue(result.isFailure)
        assertEquals(IllegalStateException::class, result.exceptionOrNull()!!::class)
    }


    @Test
    fun `Test listeners are called on successful execution`() = runTest {
        var checks1 = 0
        var checks2 = 0
        TrekkieListeners.clear()
        TrekkieListeners.beforeVerification { checks1++ }
        TrekkieListeners.beforeVerification { checks2++ }

        When {
            "OK"
        } then {
        }

        assertEquals(1, checks1)
        assertEquals(1, checks2)
    }

    @Test
    fun `Test listeners are called on successful execution with multiple whens`() = runTest {
        var checks1 = 0
        var checks2 = 0
        var result1: String? = null
        var result2: String? = null

        TrekkieListeners.clear()
        TrekkieListeners.beforeVerification { checks1++ }
        TrekkieListeners.beforeVerification { checks2++ }

        When {
            "OK"
        } then {
            result1 = it
        } When {
            "OK2"
        } then {
            result2 = it
        }

        assertEquals(2, checks1)
        assertEquals(2, checks2)
        assertEquals("OK", result1)
        assertEquals("OK2", result2)
    }

    @Test
    fun `Test listeners are called on unexpected exception`() = runTest {
        var checks1 = 0
        var checks2 = 0
        TrekkieListeners.clear()
        TrekkieListeners.beforeVerification { checks1++ }
        TrekkieListeners.beforeVerification { checks2++ }

        runCatching {
            When {
                error("boom")
            } then {
            }
        }

        assertEquals(1, checks1)
        assertEquals(1, checks2)
    }

    @Test
    fun `Test listeners are called on expected exception`() = runTest {
        var checks1 = 0
        var checks2 = 0
        TrekkieListeners.clear()
        TrekkieListeners.beforeVerification { checks1++ }
        TrekkieListeners.beforeVerification { checks2++ }

        When {
            error("boom")
        } thenExceptionThrown(IllegalStateException::class)

        assertEquals(1, checks1)
        assertEquals(1, checks2)
    }
}
