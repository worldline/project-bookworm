package worldline.bookworm.rest.jsonapi;

import java.util.List;
import java.util.Set;

/**
 * An immutable data element as specified by {@code JSON API 1.1}. This is used to wrap data in a JSON API
 * compatible format.
 *
 *   "data": [
 *     {
 *       "type": "book",
 *       "id": "1",
 *       "attributes": {
 *         "title": "The Great Gatsby",
 *         "status": "AVAILABLE"
 *       }
 *     },
 *
 * @param data a JSON API data element
 * @see <a href="https://jsonapi.org/format/#error-objects">JSON API 1.1</a>
 */
public record Data<T>(T data) {

    public static <T> Data<T> wrap(T single) {
        return new Data<>(single);
    }

    public static <T> Data<List<T>> wrap(List<T> list) {
        return new Data<>(list);
    }

    public static <T> Data<Set<T>> wrap(Set<T> set) {
        return new Data<>(set);
    }
}