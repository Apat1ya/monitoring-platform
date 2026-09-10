package io.github.apat1ya.monitor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EndpointNotFoundException.class)
    public ProblemDetail handleEndpointNotFound(EndpointNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
        problem.setTitle("Endpoint not found");
        return problem;
    }

    @ExceptionHandler(InvalidPathException.class)
    public ProblemDetail handleInvalidPath(InvalidPathException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
        problem.setTitle("Invalid path");
        return problem;
    }

    @ExceptionHandler(MonitorNotFoundException.class)
    public ProblemDetail handleMonitorNotFound(MonitorNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
        problem.setTitle("Monitor not found");
        return problem;
    }

    @ExceptionHandler(NoAccessException.class)
    public ProblemDetail handleNoAccess(NoAccessException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                e.getMessage()
        );
        problem.setTitle("Mo access");
        return problem;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFound(UserNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
        problem.setTitle("User not found");
        return problem;
    }
}
