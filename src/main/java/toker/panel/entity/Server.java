package toker.panel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import toker.panel.annotation.DefaultStringValue;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "server")
public class Server {

    private Integer id;
    private String name;
    private Integer port;
    private String key;
    private String exePath;
    private String wsePath;
    private String frontCmd;
    private Boolean useScreen;
    private String moduleName;

    private Collection<ServerStartupCommand> startupCommands;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "port", nullable = false)
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Column(name = "key", nullable = false, length = 32)
    @JsonIgnore
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "exe_path")
    @JsonIgnore
    @DefaultStringValue("sead")
    public String getExePath() {
        return exePath;
    }

    public void setExePath(String exePath) {
        this.exePath = exePath;
    }

    @Column(name = "wse_path")
    @JsonIgnore
    public String getWsePath() {
        return wsePath;
    }

    public void setWsePath(String wsePath) {
        this.wsePath = wsePath;
    }

    @Column(name = "frond_cmd")
    @JsonIgnore
    public String getFrontCmd() {
        return frontCmd;
    }

    public void setFrontCmd(String frontCmd) {
        this.frontCmd = frontCmd;
    }

    @Column(name = "use_screen")
    @JsonIgnore
    public Boolean getUseScreen() {
        return useScreen;
    }

    public void setUseScreen(Boolean useScreen) {
        this.useScreen = useScreen;
    }

    @Column(name = "module_name", nullable = false)
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @OneToMany(mappedBy = "server")
    @JsonView(View.StartupCommands.class)
    public Collection<ServerStartupCommand> getStartupCommands() {
        return startupCommands;
    }

    public void setStartupCommands(Collection<ServerStartupCommand> startupCommands) {
        this.startupCommands = startupCommands;
    }

    public interface View {
        interface None {}
        interface StartupCommands {}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(id, server.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, port, key, exePath, moduleName, startupCommands);
    }
}
