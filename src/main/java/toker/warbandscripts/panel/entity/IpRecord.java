package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ip_record")
public class IpRecord {
    private Integer id;
    private Integer uniqueId;
    private String ipAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "unique_id", nullable = false)
    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Basic
    @Column(name = "ip_address", nullable = false, length = 16)
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpRecord ipRecord = (IpRecord) o;
        return Objects.equals(uniqueId, ipRecord.uniqueId) &&
                Objects.equals(ipAddress, ipRecord.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, ipAddress);
    }
}
