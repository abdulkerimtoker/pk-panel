package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "download_token")
public class DownloadToken {

    private Integer id;
    private String token;
    private String file;
    private PanelUser user;
    private Timestamp time;
    private Boolean isUsed;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "token", nullable = false)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "file", nullable = false)
    @JsonIgnore
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    public PanelUser getUser() {
        return user;
    }

    public void setUser(PanelUser user) {
        this.user = user;
    }

    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "is_used", nullable = false)
    public Boolean isUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }
}
