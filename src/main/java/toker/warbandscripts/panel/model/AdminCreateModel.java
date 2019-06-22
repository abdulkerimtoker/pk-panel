package toker.warbandscripts.panel.model;

import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AdminCreateModel {
    @Size(min = 3, max = 32) @Pattern(regexp = "^[A-Za-z0-9_-]+")
    private String username;
    @Size(min = 3, max = 32) @Pattern(regexp = "^[A-Za-z0-9_-]+")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
