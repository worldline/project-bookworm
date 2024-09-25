package worldline.bookworm.rest.jsonapi;

/**
 * An immutable element that can be used within "relationships" as specified by {@code JSON API 1.1}.
 *
 * "relationships": {
 *    "authors": {
 *       "data": [
 *          {
 *            "type": "author",
 *            "id": "1"
 *          }
 *       ]
 *    }
 * }
 *
 * @param type the JSON API type of the related resource, e.g. "author"
 * @param id the JSON API identifier of the related resource, e.g. "1"
 * @see <a href="https://jsonapi.org/format/#error-objects">JSON API 1.1</a>
 */
public record ResourceIdentifier(String type, String id) {
}
