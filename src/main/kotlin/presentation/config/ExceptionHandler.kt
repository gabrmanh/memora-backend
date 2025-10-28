package org.showoff.presentation.config

import org.showoff.application.utils.exceptions.EntityNotFoundException
import org.showoff.application.utils.exceptions.UserUnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import kotlin.reflect.KClass


@ControllerAdvice
class ExceptionHandler {
    private val exceptionStatusMap: Map<KClass<out Throwable>, HttpStatus> = mapOf(
        EntityNotFoundException::class to HttpStatus.NOT_FOUND,
        UserUnauthorizedException::class to HttpStatus.UNAUTHORIZED,
        IllegalArgumentException::class to HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val status = exceptionStatusMap[ex::class] ?: HttpStatus.INTERNAL_SERVER_ERROR

        println(ex.message)

        val response = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = ex.message,
            path = request.getDescription(false)
        )

        return ResponseEntity(response, status)
    }
}