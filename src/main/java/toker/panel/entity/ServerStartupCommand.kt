package toker.panel.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;
import toker.panel.entity.pk.ServerStartupCommandPK;

@Entity
@Table(name = "server_startup_command")
@IdClass(ServerStartupCommandPK.class)
public class ServerStartupCommand {

    private Server server;
    private String command;
    private String value;
    private Integer order;

    @Id
    @ManyToOne
    @JoinColumn(name = "server_id", referencedColumnName = "id")
    @JsonView(View.Server.class)
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Id
    @Column(name = "command", length = 64, nullable = false)
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "_order", nullable = false)
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public interface View {
        interface None {}
        interface Server {}
    }
}
