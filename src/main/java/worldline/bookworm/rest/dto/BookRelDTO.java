package worldline.bookworm.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import worldline.bookworm.business.AuthorBO;
import worldline.bookworm.business.BookBO;
import worldline.bookworm.rest.jsonapi.Data;
import worldline.bookworm.rest.jsonapi.ResourceIdentifier;

import java.util.Set;
import java.util.stream.Collectors;

@JsonPropertyOrder({"type", "id", "attributes"})
public class BookRelDTO extends BookDTO {

    private final BookRelationships relationships;

    public BookRelDTO(BookBO book) {
        super(book);
        relationships = extractRelationships(book.authors());
    }

    @JsonCreator
    public BookRelDTO(String type, String id, BookAttributes attributes, BookRelationships relationships) {
        super(id, attributes);
        this.relationships = relationships;

        if (!getType().equals(type))
            throw new IllegalArgumentException("type " + type + " is invalid");
    }

    public record BookRelationships(Data<Set<ResourceIdentifier>> authors)
    {}

    private static BookRelationships extractRelationships(Set<AuthorBO> authors) {
        var identifiers = authors.stream()
                .map(a -> new ResourceIdentifier("author", String.valueOf(a.id())))
                .collect(Collectors.toSet());
        return new BookRelationships(Data.wrap(identifiers));

    }

    public BookRelationships getRelationships() {
        return relationships;
    }
}
