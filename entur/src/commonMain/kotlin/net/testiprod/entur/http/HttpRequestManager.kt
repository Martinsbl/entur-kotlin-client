package net.testiprod.entur.http

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.isSuccess

class EnturHttpException(message: String?) : Exception(message)

internal suspend inline fun <reified S : Any> doHttpRequest(method: () -> HttpResponse): EnturResult<S> = try {
    val httpResponse = method.invoke()
    if (httpResponse.status.isSuccess()) {
        val data: S = httpResponse.body<S>()
        EnturResult.Success(data)
    } else {
        val exception = EnturHttpException("${httpResponse.request.url}: ${httpResponse.status}")
        EnturResult.Error(exception)
    }
} catch (e: Exception) {
    EnturResult.Error(e)
}
