package toker.panel.controller.rest;

import org.springframework.web.bind.annotation.RestController;
import toker.panel.service.SimpleService;

@RestController
public class SimpleController {

    private SimpleService simpleService;

    public SimpleController(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

}
