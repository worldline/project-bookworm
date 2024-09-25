package worldline.bookworm.rest.exceptions;

import worldline.bookworm.rest.jsonapi.ErrorArray;
import worldline.bookworm.rest.jsonapi.ErrorObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


/**
 * An exception handler that handles the general issues and exceptions not handled by more specific handlers
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@RequestMapping(produces = "application/vnd.api+json")
public class BaseErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseErrorHandler.class);

    /**
     * Returns the appropriate response in case of a generic response status exception.
     *
     * @param e the exception thrown
     * @return the appropriate response in case of a programming error
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorArray> handleGenericException(ResponseStatusException e) {

        var errorArray = new ErrorArray(
                new ErrorObject(
                        String.valueOf(e.getStatusCode().value()),
                        null,
                        e.getMessage(),
                        null
                )
        );
        LOGGER.error("the following exception cannot be handled", e);
        return new ResponseEntity<>(errorArray, INTERNAL_SERVER_ERROR);
    }

    /**
     * Returns the appropriate response in case of a programming error.
     *
     * @param e the exception thrown
     * @return the appropriate response in case of a programming error
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorArray> handleGenericException(RuntimeException e) {
        var errorArray = new ErrorArray(
                new ErrorObject(
                        Integer.toString(INTERNAL_SERVER_ERROR.value()),
                        null,
                        "Internal Server Error",
                        "The server has encountered a situation it does not know how to handle."
                )
        );
        LOGGER.error("the following exception cannot be handled", e);
        return new ResponseEntity<>(errorArray, INTERNAL_SERVER_ERROR);
    }

}
