package com.bd.logkafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Sys {
    private int type;
    private int id;
    private String country;
    private long sunrise;
    private long sunset;

}
