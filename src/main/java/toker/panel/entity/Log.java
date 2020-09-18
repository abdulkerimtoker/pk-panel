package toker.panel.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "log")
public class Log {

    private Integer id;
    private Integer targetId;
    private PanelUser user;
    private Type type;
    private Timestamp time = Timestamp.from(Instant.now());
    private String data;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "target_id")
    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public PanelUser getUser() {
        return user;
    }

    public void setUser(PanelUser user) {
        this.user = user;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "data", length = 4096)
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public enum Type {
        LOG_FETCHING
    }
}
