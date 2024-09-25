package worldline.bookworm.business;

import java.util.LinkedList;
import java.util.List;

public class LibraryBO {

    /*
     * added some tiny business logic to support the borrow and return book use cases
     */

    private final long id;
    private final String name;
    private final String address;
    private final LinkedList<BookBO> books;

    public LibraryBO(long id, String name, String address, List<BookBO> books) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.books = new LinkedList<>(books);
    }

    public BookBO borrowBook(long bookId) {

        // horrible, none error handling business code!

        var theBook = books.stream().filter(book -> book.id() == bookId).findFirst().get();
        books.remove(theBook);

        var newBook = new BookBO(theBook.id(), theBook.title(), BookStatus.BORROWED, theBook.authors());
        books.add(newBook);

        return newBook;
    }

    public BookBO returnBook(long bookId) {

        // horrible, none error handling business code!

        var theBook = books.stream().filter(book -> book.id() == bookId).findFirst().get();
        books.remove(theBook);

        var newBook = new BookBO(theBook.id(), theBook.title(), BookStatus.AVAILABLE, theBook.authors());
        books.add(newBook);

        return newBook;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<BookBO> getBooks() {
        return books;
    }

}
