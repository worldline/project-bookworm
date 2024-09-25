package worldline.bookworm.business;

import java.util.Set;

public record BookBO(long id, String title, BookStatus status, Set<AuthorBO> authors)
{ }