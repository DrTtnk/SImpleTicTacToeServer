package com.company;

import java.util.Arrays;

public class QueryParameter {
    String key;
    String value;

    QueryParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String toString() {
        return key + "=" + value;
    }

    public static QueryParameter[] parse(String URI) {
        var components = URI.split("\\?");
        if (components.length != 2) {
            return new QueryParameter[0];
        }
        return Arrays.stream(components[1].split("&"))
                .map(s -> s.split("="))
                .map(s -> new QueryParameter(s[0], s[1]))
                .toArray(QueryParameter[]::new);
    }

}
