package io.github.andresviedma.trekkie

import kotlinx.coroutines.runBlocking

@Suppress("MemberVisibilityCanBePrivate")
object TrekkieListeners {
    val beforeVerificationListeners = mutableListOf<suspend () -> Unit>()

    fun beforeVerification(listener: suspend () -> Unit) {
        beforeVerificationListeners.add(listener)
    }

    fun processBeforeVerificationListenersBlocking() {
        runBlocking {
            beforeVerificationListeners.forEach { it() }
        }
    }

    suspend fun processBeforeVerificationListeners() {
        beforeVerificationListeners.forEach { it() }
    }

    fun clear() {
        beforeVerificationListeners.clear()
    }
}
