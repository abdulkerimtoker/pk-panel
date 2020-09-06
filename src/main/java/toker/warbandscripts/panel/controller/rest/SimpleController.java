package toker.warbandscripts.panel.controller.rest;

import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.service.SimpleService;

@RestController
public class SimpleController {

    private SimpleService simpleService;

    public SimpleController(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

}
