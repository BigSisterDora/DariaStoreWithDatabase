package org.example.config;

import org.example.tools.Input;
import org.example.tools.impl.ConsoleInput;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Input consoleInput() {
        return new ConsoleInput();
    }
}