package worldline.bookworm.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import worldline.bookworm.business.AuthorBO;

/**
 * An immutable author resource. This implementation adheres to the specification of resource objects as given in
 * {@code JSON API 1.1}.
 *
 * @see <a href="https://jsonapi.org/format/#document-resource-objects">JSON API 1.1</a>
 */
@JsonPropertyOrder({"type", "id", "attributes"})
public record AuthorDTO(String id, AuthorAttributes attributes) {

    public AuthorDTO(AuthorBO author) {
        this(String.valueOf(author.id()), new AuthorAttributes(author.firstName(), author.lastName()));
    }

    @JsonCreator
    private AuthorDTO(String type, String id, AuthorAttributes attributes) {
        this(id, attributes);

        if (!getType().equals(type))
            throw new IllegalArgumentException("type " + type + " is invalid");
    }


    private record AuthorAttributes(
            String firstName,
            String lastName) {
    }

    /**
     * @return the resource's type
     */
    @JsonProperty(required = true)
    public String getType() {
        return "authors";
    }
}