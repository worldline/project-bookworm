## REST with JSON API - by example
This project provides guidance for an internal REST API build with Spring, Jackson and JSON API. Including fully working Swagger documentation with
the option to "try out" the REST endpoints

### HTTP / REST Services Build
* `spring-boot-starter-web` adds the possibility to define rest-endpoints and starts the tomcat embedded server to serve them
* address and port of the server are defined in `application.properties`
* the REST service implements the bookworm example, a simple CRUD service for libraries, books and authors
* we use Jackson to marshall/unmarshall the JSON API format
* `springdoc-openapi-starter-webmvc-ui` dependency adds the possibility to generate an OpenAPI documentation for the REST endpoints
* finally `openapi` annotations help to improve the generated documentation
* go to `http://localhost:8090/swagger-ui.html` to explore the REST endpoint

### JSON API Demonstration
* explore the Authors endpoint to understand how the JSON API format is used to simply wrap trivial data structures
* take a look at the Books endpoint for relationships, object creation and exception handling
* study the Libraries endpoint for object updates and filtering 

### Internal APIs and Relationships/Includes
Books are written by one or many authors, which is expressed as a Set of AuthorBO references in the BookBO. For the books REST endpoint
_relationships_ are used to express this. With _includes_ we could even go a step further and add the authors to the response if the
client requests it. 

However, in an internal API a simpler approach would also be possible. The BookDTO could contain authors or author ids directly
as part of its attributes. Although the specification does not recommend such shortcuts it is a valid approach for an internal APIs. For example:

```json
{
  "data": {
    "type": "books",
    "id": "1",
    "attributes": {
      "title": "The Gathering Storm (Wheel of Time, 12)",
      "status": "BORROWED",
      "authors": [
        {
          "firstname": "Robert",
          "lastname": "Jordan"
        },
        {
          "firstname": "Brandon",
          "lastname": "Sanderson"
        }
      ]
    }
  }
}
```

or even simpler

```json
{
  ... 
  "authors": ["1", "3"]        
}
```

for reference the JSON API specification defines the following rules for attributes:
* Attributes may contain any valid JSON value, including complex data structures involving JSON objects and arrays.
* Keys that reference related resources (e.g. author_id) SHOULD NOT appear as attributes. Instead, relationships SHOULD be used.

### Internal APIs and a Resource Oriented Approach
In the bookworm example we support borrowing of books. The straight forward solution for this would be a RPC style (Remote Procedure Call) endpoint like _POST /borrow/{bookId}_.
However, this is not really RESTful or resource oriented and leads to tight coupling between client and server. The other end of the extreme would be to model the library as a resource
with _relationships_ (available, borrowed) to the books resources and manipulate these relationship with POST, PATCH and DELETE requests. 

For an internal API we should aim for some middle ground, not falling into the traps of RPC but also not overengineering the API. One such solution with PATCH requests that manipulate
the status of books is contained in the example code. Another similar solution would store the book status as part of the library (two sets of books) and PATCH the library accordingly. Both
approaches work directly on the data model. 

A third solution is the explicit modelling of actions as REST resources.

_POST /book-rental_
```json
  "data": [
    {
      "type": "book-rental",
      "attributes": {
        "status": "AVAILABLE"
      },
      "relationships": {
          "book": {
            "data": [
            {
              "type": "book",
              "id": "1"
            }
         ]
      }
    }
  ]  
```

The difference to RPC is at first glance subtle, we are not calling an action on book, instead we are  creating a new resource that represents the action. But unlike in RPC a book-rental resource can easily
be extended with additional attributes (like a relationship to the user). It can also be made retrievable GET /book-rental/{id}. Returning of books can be modeled with a DELETE. Modifications
are possible with a PATCH (e.g. a rental extension) ...

For internal APIs often a simple PATCH should be sufficient, especially if relationships are included as nested attributes (see above). If more complex interactions are required,
consider modeling the action as a dedicated resource or make use of JSON API _relationships_.