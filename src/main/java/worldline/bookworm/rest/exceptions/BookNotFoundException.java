package worldline.bookworm.rest.exceptions;

/**
 * A specialized exception that is thrown when a book is not found in a library.
 */
public class BookNotFoundException extends IllegalArgumentException {

    private final String bookId;

    public BookNotFoundException(String bookId) {
        super(String.format("Book with id %s not found", bookId));
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }
}
