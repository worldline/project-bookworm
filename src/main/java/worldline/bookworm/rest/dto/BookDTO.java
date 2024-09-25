package worldline.bookworm.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import worldline.bookworm.business.BookBO;
import worldline.bookworm.business.BookStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * An immutable book resource. This implementation adheres to the specification of resource objects as given in
 * {@code JSON API 1.1}.
 *
 * @see <a href="https://jsonapi.org/format/#document-resource-objects">JSON API 1.1</a>
 */
@JsonPropertyOrder({"type", "id", "attributes"})
public class BookDTO {

    protected final String id;
    protected final BookAttributes attributes;

    protected BookDTO(String id, BookAttributes attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public BookDTO(BookBO book) {
        this(String.valueOf(book.id()), new BookAttributes(book.title(), book.status()));
    }

    public record BookAttributes(
            @JsonProperty(required = true) @Schema(description = "the title of the book") String title,
            BookStatus status)
    {}

    /**
     * @return the resource's type
    */
    @JsonProperty(required = true)
    public String getType() {
        return "books";
    }

    public String getId() {
        return id;
    }

    public BookAttributes getAttributes() {
        return attributes;
    }
}
