package com.example;

import java.io.IOException;

public class MockExternalService implements ExternalService {
    @Override
    public String get(String arg) throws IOException {
        String answer = "Some data";
        return answer;
    }
}
