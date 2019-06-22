package toker.warbandscripts.panel.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.entity.Troop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping("home")
    public String home() {
        return "home";
    }

    @RequestMapping("/")
    public String index() {
        return "home";
    }
}
