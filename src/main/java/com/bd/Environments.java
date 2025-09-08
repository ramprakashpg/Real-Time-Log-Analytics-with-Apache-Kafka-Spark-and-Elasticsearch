
package com.bd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Environments {

    esApiKey("RWNEd29KZ0JkeUVxOVdIM2xNYnk6dDY1TGM3dHQ3NGMzRnAyQlNpbS1TZw=="),
    weatherApi("V45ZEERQDA8GQECMQKEWXEXT2"),
    esUrl("http://localhost:9200"),
    weatherUrl("http://localhost:9200");

    @Getter
    private final String value;

}
