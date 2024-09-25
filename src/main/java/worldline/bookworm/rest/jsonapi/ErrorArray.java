package worldline.bookworm.rest.jsonapi;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Set;

/**
 * An immutable array of error objects as specified by {@code JSON API 1.1}. This is used to communicate error details
 * with error responses on the {@code REST} interface.
 *
 *   "errors": [
 *     {
 *        "status": "404",
 *        "title": "404 NOT_FOUND"
 *     }
 *   ]
 *
 * @param errors the contained error objects
 * @see <a href="https://jsonapi.org/format/#error-objects">JSON API 1.1</a>
 */
@JsonTypeName(value = "errors")
public record ErrorArray(Set<ErrorObject> errors) {

    /**
     * Creates an instance with the given error object as the one and only entry.
     *
     * @param error the error object to put in the array
     */
    public ErrorArray(ErrorObject error) {
        this(Set.of(error));
    }

    /**
     * Creates an instance containing the given error objects. At least one error object must be provided.
     *
     * @param errors the error objects to put in the array
     */
    public ErrorArray(Set<ErrorObject> errors) {
        if (errors.isEmpty())
            throw new IllegalArgumentException("empty error array");
        this.errors = Set.copyOf(errors);
    }
}
