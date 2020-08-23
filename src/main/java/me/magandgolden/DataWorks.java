package me.magandgolden;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class DataWorks {

    public DataWorks () {
    }
    @Getter
    @Setter
    private List <String> jokes = new ArrayList<>();
    @Getter
    @Setter
    private List <String> quotes = new ArrayList<>();
}
