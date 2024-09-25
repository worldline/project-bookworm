package worldline.bookworm.rest;

import worldline.bookworm.business.BookBO;
import worldline.bookworm.business.BookStatus;
import worldline.bookworm.data.MockData;
import worldline.bookworm.rest.dto.BookDTO;
import worldline.bookworm.rest.dto.LibraryDTO;
import worldline.bookworm.rest.jsonapi.Data;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The Libraries endpoint supports filtering of books by status (available, borrowed) and allows to manipulate this
 * status as part of a PATCH request.
 *
 * - combining API annotations and Java streaming: filtering of objects can easily be integrated
 * - sorting and pagination should be quite similar and also fit in well
 * - enforcing the manipulation of objects and object relationships (not in the example) with the PATCH method and
 *   request bodies leads to a pure RESTfull API that works on resources and state transitions. However, a rather
 *   procedural approach like /POST /borrow/{bookId} is faster to implement and closer to the business logic.
 * - proper REST gives more stable long term maintainable APIs at a tradeoff for implementation speed.
 *
 */
@RestController
@RequestMapping(
        value = "/v1",
        produces = "application/vnd.api+json"
)
public class LibrariesRestController {

    @GetMapping("/libraries")
    public Data<Set<LibraryDTO>> getLibraries()
    {
        return new Data<>(Set.of(new LibraryDTO(MockData.library)));
    }

    @GetMapping("/libraries/{libraryId}")
    public Data<LibraryDTO> getLibraryById(@PathVariable long libraryId)
    {
        if (libraryId != MockData.library.getId()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new Data<>(new LibraryDTO(MockData.library));
    }

    @GetMapping("/libraries/{libraryId}/books")
    public Data<List<BookDTO>> getBooksOfLibrary(
            // libraryId
            @PathVariable long libraryId,
            // book status filter
            @Parameter(description = "Filter by book status", schema = @Schema(type = "string", allowableValues = {"borrowed", "available"}))
            @RequestParam(required = false, value = "filter[book.status]") String filter)
    {
        if (libraryId != MockData.library.getId()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Predicate<BookBO> bookFilter = createBookFilter(filter);

        return new Data<>(MockData.library.getBooks().stream()
                .filter(bookFilter)
                .map(BookDTO::new)
                .collect(Collectors.toList()));
    }

    @PatchMapping("/libraries/{libraryId}/books/{bookId}")
    public Data<BookDTO> updateBookInstance(@PathVariable long libraryId, @PathVariable long bookId, @RequestBody Data<BookDTO> bookChange)
    {
        if (libraryId != MockData.library.getId()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var newStatus = bookChange.data().getAttributes().status();
        if (newStatus == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // business logic
        BookBO newBook;
        if (newStatus.equals(BookStatus.BORROWED))
        {
            newBook = MockData.library.borrowBook(bookId);
        } else {
            newBook = MockData.library.returnBook(bookId);
        }

        return new Data<>(new BookDTO(newBook));
    }

    /**
     * Creates a predicate that filters books by status.
     *
     * @param filter the status to filter by
     * @return the predicate
     */
    private static Predicate<BookBO> createBookFilter(final String filter) {
        Predicate<BookBO> bookFilter = book -> true;

        if (filter != null && filter.equalsIgnoreCase("available"))
        {
            bookFilter = book -> book.status().equals(BookStatus.AVAILABLE);
        }
        else if (filter != null && filter.equalsIgnoreCase("borrowed"))
        {
                bookFilter = book -> book.status().equals(BookStatus.BORROWED);
        }

        return bookFilter;
    }

}
