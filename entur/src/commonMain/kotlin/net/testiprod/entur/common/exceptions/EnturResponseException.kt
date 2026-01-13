package net.testiprod.entur.common.exceptions

class EnturResponseException(
    message: String,
    cause: Throwable?,
) : Exception(message, cause)
