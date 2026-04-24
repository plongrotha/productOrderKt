package org.plongrotha.productorder.exception


class ResourceNotFoundException(message: String) : RuntimeException(message)

class BadRequestException(message: String) : RuntimeException(message)

class ResourceLockedException(
    message: String, val resourceId: String, val reason: String
) : RuntimeException(message)