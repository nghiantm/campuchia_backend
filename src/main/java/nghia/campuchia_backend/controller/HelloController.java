package nghia.campuchia_backend.controller;

import java.util.concurrent.atomic.AtomicLong;

import nghia.campuchia_backend.dto.Hello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/api/public/hello")
    public Hello greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Hello(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/api/hello")
    public Hello private_greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Hello(counter.incrementAndGet(), String.format(template, name));
    }
}
