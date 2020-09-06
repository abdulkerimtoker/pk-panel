package toker.warbandscripts.panel.entity;

import toker.warbandscripts.panel.entity.pk.ServerConfigurationPK;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "server_configuration")
@IdClass(ServerConfigurationPK.class)
public class ServerConfiguration {

    private Server server;
    private String name;
    private Integer type;
    private String value;

    @Id
    @ManyToOne
    @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Id
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConfiguration that = (ServerConfiguration) o;
        return Objects.equals(server, that.server) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, name, type, value);
    }
}

