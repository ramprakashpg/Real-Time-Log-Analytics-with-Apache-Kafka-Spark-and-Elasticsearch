
package com.bd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Environments {

    esApiKey("cjkyT1dKa0JCZnRhNHdvNWs2TDc6bkxUXzlfRzljUUR3WmVHQUNEXzJuZw=="),
    weatherApi("V45ZEERQDA8GQECMQKEWXEXT2"),
    esUrl("https://localhost:9200"),
    weatherUrl("http://localhost:9200");

    @Getter
    private final String value;

}
