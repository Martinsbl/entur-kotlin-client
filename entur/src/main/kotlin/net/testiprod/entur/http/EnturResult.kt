package net.testiprod.entur.http

sealed class EnturResult<out T : Any> {
    data class Success<T : Any>(val data: T) : EnturResult<T>()

    data class Error(val exception: Throwable?) : EnturResult<Nothing>()
}

inline fun <T : Any, R : Any> EnturResult<T>.transformResult(transform: (T) -> R): EnturResult<R> = when (this) {
    is EnturResult.Success -> EnturResult.Success(transform(this.data))
    is EnturResult.Error -> this
}
