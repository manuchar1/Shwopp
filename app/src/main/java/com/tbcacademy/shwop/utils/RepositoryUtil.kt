package com.tbcacademy.shwop.utils

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch(e: Exception) {
        Resource.Error(e.message ?: "An unknown error occured")
    }
}