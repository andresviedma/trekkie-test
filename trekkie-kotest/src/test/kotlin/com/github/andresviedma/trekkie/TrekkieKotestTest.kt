package com.github.andresviedma.trekkie

import io.kotest.assertions.AssertionFailedError
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class TrekkieKotestTest : FeatureSpec({

    feature("normal Trekkie tests working in kotest") {
        scenario("Successful test, testing that it visits all the code") {
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
                suspend { "OK-${number + 1}-${string.length}-$number2" }()
            }.then {
                suspend { result = it }()
            }.and("more checks") {
                suspend { and1Check = true }()
            } and {
                and2Check = true
            }

            // Test validations
            result shouldBe "OK-2-5-3"
            givenChecks.all { it } shouldBe true
            and1Check shouldBe true
            and2Check shouldBe true
        }

        scenario("A failing test") {
            val result = runCatching {
                When {
                    "OK"
                } then {
                    it shouldBe "OK2"
                }
            }

            result.isFailure shouldBe true
            println(result.exceptionOrNull()!!::class)
            result.exceptionOrNull()!!::class shouldBe AssertionFailedError::class
        }
    }

    feature("Tests using Where") {
        Where(
            row("zero", 0, 0),
            row("1", 1, 1),
            row("a positive number", 7, 49),
            row("a negative number", -5, 25),

        ) { (caseName, input, expectedResult) ->
           scenario("$caseName squared") {
               When {
                   input * input
               } then {
                   it shouldBe expectedResult
               }
           }
        }
    }
})
