package worldline.bookworm.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import worldline.bookworm.business.LibraryBO;

@JsonPropertyOrder({"type", "id", "attributes"})
public record LibraryDTO(String id, LibraryAttributes attributes) {

    public LibraryDTO(LibraryBO library) {
        this(String.valueOf(library.getId()), new LibraryAttributes(library.getName(), library.getBooks().size()));
    }

    public record LibraryAttributes(String name, int numberOfBooks) {
    }

    /**
     * @return the resource's type
     */
    @JsonProperty(required = true)
    public String getType() {
        return "libraries";
    }
}
