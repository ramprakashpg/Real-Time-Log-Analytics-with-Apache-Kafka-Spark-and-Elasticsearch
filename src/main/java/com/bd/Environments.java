
package com.bd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Environments {

    esApiKey("TFFZOVZwa0JMeWJlei05Q0RKRW06T3A0QlBiODdCR0hmOUpNa0Y4QVlUUQ=="),
    weatherApi("V45ZEERQDA8GQECMQKEWXEXT2"),
    esUrl("https://localhost:9200"),
    weatherUrl("http://localhost:9200");

    @Getter
    private final String value;

}
