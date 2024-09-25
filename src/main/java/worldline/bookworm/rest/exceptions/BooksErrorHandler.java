package worldline.bookworm.rest.exceptions;


import worldline.bookworm.rest.jsonapi.ErrorArray;
import worldline.bookworm.rest.jsonapi.ErrorObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * An exception handler that handles specific exceptions that may occur during books processing
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequestMapping(produces = "application/vnd.api+json")
public class BooksErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BooksErrorHandler.class);

    /**
     * Returns a specialized book not found error
     *
     * @param e the exception thrown
     * @return the book not found error
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorArray> handleGenericException(BookNotFoundException e) {

        var errorArray = new ErrorArray(
                new ErrorObject(
                        String.valueOf(HttpStatus.NOT_FOUND.value()),
                        Map.of("pointer", "/data/id"),
                        "Book NOT FOUND",
                        "The book with the ID " + e.getBookId() + " is not known to the library system"
                )
        );
        LOGGER.error("the following exception cannot be handled", e);
        return new ResponseEntity<>(errorArray, INTERNAL_SERVER_ERROR);
    }
}
