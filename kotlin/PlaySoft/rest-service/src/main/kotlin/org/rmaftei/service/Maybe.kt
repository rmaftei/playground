package org.rmaftei.service

sealed class Maybe<out T> {
    object None : Maybe<Nothing>() {
        override fun get(): Nothing {
            throw UnsupportedOperationException()
        }
    }

    class Just<T>(val t: T) : Maybe<T>() {
        override fun get(): T {
            return t
        }
    }

    abstract fun get(): T
}
