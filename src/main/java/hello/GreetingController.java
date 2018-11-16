package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @RestController handles HTTP requests, returns domain object instead of view (HTML)
// stands for @Controller + @ResponseBody
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // all HTTP operations (GET/PUT/POST) to /greeting are mapped to greeting() method
    // to narrow operations handled, use @RequestMapping(method=GET)
    @RequestMapping("/greeting")
    // @RequestParam binds "name" parameter in query string (request) into name parameter in greeting()
    // if there is no "name" parameter in the request, default value of "Word" is used
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
        // RESTful web service controller returns Greeting object instead of HTML
        // object data will be written to HTTP response as JSON
    }
}