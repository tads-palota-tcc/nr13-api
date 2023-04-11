package br.com.smartnr.nr13api.api.exceptionhandler;

import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.exception.EntityNotFoundException;
import br.com.smartnr.nr13api.domain.exception.TokenValidationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String USER_MESSAGE = "Ocorreu um erro interno inesperado no sistema. " +
            "Tente novamente e se o problema persistir, contate o administrador do sistema.";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }
        var errorType = ErrorType.INCOMPREHENSIVE_MESSAGE;
        var detail = "The request body is invalid. Check syntax error.";
        var problem = createErrorBuilder(status, errorType, detail)
                .userMessage(USER_MESSAGE)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        var error = createErrorBuilder(HttpStatus.NOT_FOUND, ErrorType.RESOURCE_NOT_FOUND, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var errorType = ErrorType.BUSINESS_ERROR;
        var detail = ex.getMessage();
        var problem = createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var errorType = ErrorType.AUTHENTICATION_ERROR;
        var detail = ex.getMessage();
        var problem = createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        var status = HttpStatus.FORBIDDEN;
        var errorType = ErrorType.AUTHENTICATION_ERROR;
        var detail = "Acesso negado";
        var problem = createErrorBuilder(status, errorType, detail)
                .userMessage("Usuário não possui acesso ao recurso solicitado")
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<?> handleTokenValidationException(TokenValidationException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var errorType = ErrorType.AUTHENTICATION_ERROR;
        var detail = ex.getMessage();
        var problem = createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorType errorType = ErrorType.SYSTEM_ERROR;

        ex.printStackTrace();

        Error error = createErrorBuilder(status, errorType, USER_MESSAGE)
                .userMessage(USER_MESSAGE)
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
                                                            HttpStatusCode status, WebRequest request) {
        var errorType = ErrorType.INVALID_DATA;
        var detail = "Um ou mais campos estão inválidos. Preencha corretamente e tente novamente";
        List<Error.Object> errorObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    var message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    var name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }
                    return Error.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                }).collect(Collectors.toList());
        var error = createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .objects(errorObjects)
                .build();
        return handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var path = joinPath(ex.getPath());
        var errorType = ErrorType.INCOMPREHENSIVE_MESSAGE;
        var detail = String.format("Propriedade '%s' não existe. Corrija ou remova esta propriedade e tente novamente.", path);
        var error = createErrorBuilder(status, errorType, detail)
                .userMessage(USER_MESSAGE)
                .build();
        return handleExceptionInternal(ex, error, headers, status, request);
    }
    private Error.ErrorBuilder createErrorBuilder(HttpStatusCode status, ErrorType errorType, String detail) {
        return Error.builder()
                .status(status.value())
                .type(errorType.getUri())
                .title(errorType.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var path = joinPath(ex.getPath());
        var errorType = ErrorType.INCOMPREHENSIVE_MESSAGE;
        var detail = String.format("Propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. " +
                "Corrija e envie um valor do tipo '%s'", path, ex.getValue(), ex.getTargetType().getSimpleName());
        var problem = createErrorBuilder(status, errorType, detail)
                .userMessage(USER_MESSAGE)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

}
