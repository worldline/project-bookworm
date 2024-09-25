# Project Bookworm
Demo project that showcases [JSON:API](https://jsonapi.org/) with Spring Boot and plain Java.

## Content
* REST with JSON API - justification
* [REST with JSON API - by example](./src/main/java/worldline/bookworm/rest/README.md)

## REST with JSON API - justification

JSON API is a specification for building APIs in JSON. Plain JSON lacks a standard way for structuring of request and responses, error handling, filtering
and pagination. JSON API defines these standards and provides additional features to reference and link resources, combine resources, save bandwidth and
reduce the number of requests...

It often makes sense to distinguish between internal and external APIs. An external API is used by third parties, like a browser, a mobile app, a partner
while an internal API is used by your own services, maybe in the same project. The requirements for an external API are usually higher than for an internal one.
Specifically, bandwidth and latency concerns as well as a self-explanatory and discoverable format are more important for an external API.

This project and the accompanying [blogpost](https://blog.worldline.tech/) focus on internal APIs omitting some of the richer features of JSON API. 

the project
* follows the baseline w.r.t. HTTP verbs, usage of _data_ and _errors_ objects
* showcases relationships in a few examples but does not enforce them everywhere
* makes use of complex / nested objects inside the attributes block ([read more](./src/main/java/worldline/bookworm/rest/README.md))
* uses a Resource Oriented Approach to provide endpoints but takes some liberty compared to gold standard RESTfulness ([read more](./src/main/java/worldline/bookworm/rest/README.md))
* implements a layered architecture but the data and business layer are just mocks/stubs
