package worldline.bookworm.data;

import worldline.bookworm.business.AuthorBO;
import worldline.bookworm.business.BookBO;
import worldline.bookworm.business.BookStatus;
import worldline.bookworm.business.LibraryBO;

import java.util.List;
import java.util.Set;

/**
 * Mock data to emulate a data layer, exposed to business and presentation layers.
 */
public class MockData {

    private static final AuthorBO fitzgerald = new AuthorBO(1, "F. Scott", "Fitzgerald");
    private static final AuthorBO salinger = new AuthorBO(2, "J.D.", "Salinger");
    private static final AuthorBO pratchett = new AuthorBO(3, "Terry", "Pratchett");
    private static final AuthorBO gaiman = new AuthorBO(4, "Neil", "Gaiman");

    private static final BookBO gatsby = new BookBO(1, "The Great Gatsby", BookStatus.AVAILABLE, Set.of(fitzgerald));
    private static final BookBO catcher = new BookBO(2, "The Catcher in the Rye", BookStatus.AVAILABLE, Set.of(salinger));
    private static final BookBO omens = new BookBO(3, "Good Omens", BookStatus.AVAILABLE, Set.of(pratchett, gaiman));
    private static final BookBO mort = new BookBO(4, "Mort", BookStatus.BORROWED, Set.of(pratchett));
    private static final BookBO mort2 = new BookBO(5, "Mort", BookStatus.AVAILABLE, Set.of(pratchett));

    public static final Set<AuthorBO> authors = Set.of(fitzgerald, salinger, pratchett, gaiman);
    public static final Set<BookBO> books = Set.of(gatsby, catcher, omens, mort, mort2);
    public static final LibraryBO library = new LibraryBO(
            1,
            "Great Library of Alexandria",
            "Alexandria",
            List.of(gatsby, catcher, omens, mort, mort2)
    );

}
