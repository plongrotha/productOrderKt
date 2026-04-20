package org.plongrotha.productorder.exception

import jakarta.servlet.http.HttpServletRequest
import org.plongrotha.productorder.dto.res.ApiErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException
import java.time.Instant
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalException {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ProblemDetail {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message ?: "Not Found")
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ProblemDetail {
        val errors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }

        return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST).apply {
            title = "Validation Failed"
            detail = "One or more fields are invalid"
            setProperty("errors", errors)
            setProperty("timestamp", Instant.now())
        }
    }

    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handleMethodValidationException(e: HandlerMethodValidationException): ProblemDetail {
        val errors = mutableMapOf<String, String?>()

        e.parameterValidationResults.forEach { parameterError ->
            val paramName = parameterError.methodParameter.parameterName ?: "parameter"
            parameterError.resolvableErrors.forEach { messageError ->
                errors[paramName] = messageError.defaultMessage
            }
        }

        return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST).apply {
            title = "Method Parameter Validation Failed"
            setProperty("timestamp", LocalDateTime.now())
            setProperty("errors", errors)
        }
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(
        ex: IllegalArgumentException,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {

        val response = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "Invalid request",
            path = request.requestURI
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {

        val response = ApiErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Unexpected error occurred",
            path = request.requestURI
        )

        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}