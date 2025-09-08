
package com.bd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Environments {

    apiKey("RWNEd29KZ0JkeUVxOVdIM2xNYnk6dDY1TGM3dHQ3NGMzRnAyQlNpbS1TZw=="),
    URL("http://localhost:9200");

    @Getter
    private final String value;

}
