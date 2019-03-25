package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    private String message;

    public WelcomeController(@Value("${welcome.message:NOT SET}") String welcome_message) {
        this.message = welcome_message;
    }

    @GetMapping("/")
    public String sayHello() {
        return this.message;
    }
}
