package worldline.bookworm.rest;

import org.springframework.web.bind.annotation.*;
import worldline.bookworm.data.MockData;
import worldline.bookworm.rest.dto.BookDTO;
import worldline.bookworm.rest.dto.BookRelDTO;
import worldline.bookworm.rest.exceptions.BookNotFoundException;
import worldline.bookworm.rest.jsonapi.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Books endpoint makes use of more features of JSON API and provides a more complex example.
 *
 * - showcases how different representations of the same business object can be provided
 * - uses the "relationships" element from JSON API for referencing of objects
 * - demonstrates a POST endpoint for the creation of new books to show how a DTO can be parsed from a request body
 * - adds error handling utilizing @ControllerAdvice in the exceptions package
 *
 * Exception handling integrates very well with Spring and allows to create rich user-friendly errors combined with
 * catch all exceptions for missed cases. Relationships also work well but DTOs for full JSON API objects with includes,
 * links, meta information ... would become quite complex and creation becomes more and more effort.
 *
 * A possible shortcut is to merge the BookDTO and BookRelDTO with nullable "relationships" but this messes up the
 * swagger documentation.
 */
@RestController
@RequestMapping(
        value = "/v1",
        produces = "application/vnd.api+json"
)
public class BooksRestController {

    @GetMapping("/books")
    public Data<List<BookDTO>> getBooks()
    {
        return Data.wrap(MockData.books.stream()
                .map(BookDTO::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/books/{id}")
    public Data<BookRelDTO> getBookById(@PathVariable String id)
    {
        return Data.wrap(MockData.books.stream()
                .map(BookRelDTO::new)
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id)));
    }

    @PostMapping("/books")
    public void createBook(@RequestBody Data<BookRelDTO> bookRequest)
    {
        System.out.println("Fake creating book: " + bookRequest.data());
    }
}
