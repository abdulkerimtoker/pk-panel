package toker.warbandscripts.panel.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.openid.OpenIDConsumer;
import org.springframework.security.openid.OpenIDConsumerException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OpenIDLoginController {

    @Autowired
    private OpenIDConsumer consumer;

    public OpenIDLoginController(OpenIDConsumer consumer) {
        this.consumer = consumer;
    }

    @RequestMapping("/api/login")
    public HttpEntity login(HttpServletRequest request) throws OpenIDConsumerException {
        String redirectTo = consumer.beginConsumption(request, "https://steamcommunity.com/openid",
                "http://localhost:8080/api/processLogin", "http://localhost:8080/");
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header(HttpHeaders.LOCATION, redirectTo).build();
    }

    @RequestMapping("/api/processLogin")
    public void processLogin(HttpServletRequest request) throws OpenIDConsumerException {
        OpenIDAuthenticationToken token = consumer.endConsumption(request);
        System.out.println();
    }
}
