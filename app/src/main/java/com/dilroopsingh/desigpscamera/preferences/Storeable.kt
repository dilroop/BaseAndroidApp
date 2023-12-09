package com.dilroopsingh.desigpscamera.preferences

interface Storeable<T> {
    fun get(): T
    fun store(value: T)
}