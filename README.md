# Trekkie
Trekkie is a Kotlin testing specification framework inspired by [Spock framework](https://spockframework.org/).

Its purpose is getting from Spock framework as much as possible its beautiful and highly expressive
specification language, using a Kotlin DSL for it.

In opposition to Spock, it doesn't provide its
own testing framework, but it is compatible with all the other test frameworks.
This means that it does not provide a test runner and is not focused either on
a test class organization. It is just a DSL for concrete tests.

## The test specification DSL
A Trekkie test could look like this, using kotlin-test:

```kotlin
@Test
fun `user login should work with the right credentials`() = runTest {
    Given("a user registered in the database") {
        userTable.contains(user)
    }.and ("the user has some valid credentials") {
        userCredentialsTable.contains(credentials)
    }.When("a correct login is attempted") { user, credentials ->
        loginService.login(credentials.userName, credentials.password)
    }.then("the login is correct") { result ->
        assertTrue(result.logged)
        assertEquals(userId, result.userId)
    }
}
```

Or it could also look like this, using Kotest, whose declarative syntax matches
particularly well with Trekkie:

```kotlin
class LoginServiceTest : FeatureSpec({
    val loginService = LoginService(...)
    
    feature("login with user name / password") {

        scenario("should work with the right credentials") {
            Given("a user registered in the database") {
                userTable.contains(user)
            }.and("the user has some valid credentials") {
                userCredentialsTable.contains(credentials)
            }.When("a correct login is attempted") { user, credentials ->
                loginService.login(credentials.userName, credentials.password)
            }.then("the login is correct") { result ->
                result.logged shouldBe true
            }.and("the logged user is the expected") {
                result.userId shouldBe userId
            }
        }
    }
})
```

The framework can be used in suspendable and non-suspendable contexts,
so it's compatible with coroutines.
