package worldline.bookworm.rest.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonPointer;

import java.util.Map;

/**
 * An immutable error object as specified by {@code JSON API 1.1}. This implementation provides the subset of error
 * object attributes needed in this application.
 *
 *   "errors": [
 *     {
 *        "status": "404",
 *        "title": "404 NOT_FOUND"
 *        "detail" "The book with the ID 8 could not be found in this library."
 *     }
 *   ]
 *
 * @param status the HTTP status code applicable to this problem
 * @param source an object containing references to the primary source of the error
 * @param title a short, human-readable summary of the problem
 * @param detail a human-readable explanation specific to this occurrence of the problem
 * @see <a href="https://jsonapi.org/format/#error-objects">JSON API 1.1</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorObject(String status, Map<String, String> source, String title, String detail) {

    /**
     * Creates an instance from the given attributes. All attributes are optional and may therefore be {@code null}, but
     * at least one attribute must be given. Given values will be validated against the {@code JSON API 1.1}
     * specification.
     *
     * @param status the HTTP status code applicable to this problem
     * @param source an object containing references to the primary source of the error
     * @param title a short, human-readable summary of the problem
     * @param detail a human-readable explanation specific to this occurrence of the problem
     * @see <a href="https://jsonapi.org/format/#error-objects">JSON API 1.1</a>
     */
    public ErrorObject {
        if ((status == null || status.isBlank()) && (source == null || source.isEmpty()) && (title == null
                || title.isBlank()) && (detail == null || detail.isBlank()))
            throw new IllegalArgumentException("error object has no content");

        if (source != null)
            if (source.isEmpty())
                throw new IllegalArgumentException("source contains no members");
            else if (source.size() > 1)
                throw new IllegalArgumentException("source contains too many members " + source.keySet());
            else if (!source.containsKey("pointer") && !source.containsKey("parameter") && !source.containsKey(
                    "header"))
                throw new IllegalArgumentException("source contains unknown member " + source.keySet());
            else if (source.containsKey("pointer"))
                JsonPointer.compile(source.get("pointer"));

        if (status != null)
            try {
                Integer.parseInt(status);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("status is not a valid status code", e);
            }
    }
}

