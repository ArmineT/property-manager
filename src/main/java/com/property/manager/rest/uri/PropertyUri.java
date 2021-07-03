package com.property.manager.rest.uri;

public interface PropertyUri {
    String baseUri = BaseRestUri.baseUri + "property";

    String propertyUri = "/{id}";
}
