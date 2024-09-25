package worldline.bookworm.rest;

import worldline.bookworm.data.MockData;
import worldline.bookworm.rest.dto.AuthorDTO;
import worldline.bookworm.rest.jsonapi.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The Authors endpoint is a simplistic read only endpoint that demonstrates how data can be presented in JSON API using
 * plain java.
 *
 * the plain Java approach
 * - converts from the business layer representation (BusinessObject) to the presentation layer representation (DTO)
 * - plain Java and Jackson are used to create the JSON API structures and nesting
 * - the REST DTOs have a pure presentational purpose including the object nesting, structure and documentation related annotations.
 * - only in very simple cases BO and DTO can be one object.
 * - the approach provides strong decoupling between the layers and works well with processors to create documentation.
 *
 * alternatives are
 *  - reflection based libraries that create the Java structures for marshalling on the fly
 *  - code generation tools that crate the Java structures from specification / documentation
 *  - libraries that build the Java structures with generic containers for "data", "attributes", ... and then fill them with the business data.
 */
@RestController
@RequestMapping(
        value = "/v1",
        produces = "application/vnd.api+json"
)
public class AuthorsRestController {

    @GetMapping("/authors")
    public Data<Set<AuthorDTO>> getAuthors()
    {
        return Data.wrap(MockData.authors.stream()
                .map(AuthorDTO::new)
                .collect(Collectors.toSet()));
    }

}
