package toker.warbandscripts.panel.controller.rest;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SimpleController {

    @GetMapping("/api/vonPigIsARetard")
    public String retard(HttpServletRequest request) {
        return "ur ip is " + request.getRemoteHost() + " retard";
    }
}
