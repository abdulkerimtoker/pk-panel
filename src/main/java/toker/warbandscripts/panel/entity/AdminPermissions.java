package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "admin_permissions")
@IdClass(AdminPermissionsPK.class)
public class AdminPermissions {

    private Integer uniqueId;
    private Server server;
    private Boolean panel;
    private Boolean gold;
    private Boolean kick;
    private Boolean temporaryBan;
    private Boolean permanentBan;
    private Boolean killFade;
    private Boolean freeze;
    private Boolean teleportSelf;
    private Boolean adminItems;
    private Boolean healSelf;
    private Boolean godlikeTroop;
    private Boolean ships;
    private Boolean announce;
    private Boolean overridePoll;
    private Boolean allItems;
    private Boolean mute;
    private Boolean animals;
    private Boolean joinFactions;
    private Boolean factions;

    @Id
    @Column(name = "unique_id", nullable = false)
    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Basic
    @Column(name = "panel", nullable = true)
    public Boolean getPanel() {
        return panel;
    }

    public void setPanel(Boolean panel) {
        this.panel = panel;
    }

    @Basic
    @Column(name = "gold", nullable = true)
    public Boolean getGold() {
        return gold;
    }

    public void setGold(Boolean gold) {
        this.gold = gold;
    }

    @Basic
    @Column(name = "kick", nullable = true)
    public Boolean getKick() {
        return kick;
    }

    public void setKick(Boolean kick) {
        this.kick = kick;
    }

    @Basic
    @Column(name = "temporary_ban", nullable = true)
    public Boolean getTemporaryBan() {
        return temporaryBan;
    }

    public void setTemporaryBan(Boolean temporaryBan) {
        this.temporaryBan = temporaryBan;
    }

    @Basic
    @Column(name = "permanent_ban", nullable = true)
    public Boolean getPermanentBan() {
        return permanentBan;
    }

    public void setPermanentBan(Boolean permanentBan) {
        this.permanentBan = permanentBan;
    }

    @Basic
    @Column(name = "kill_fade", nullable = true)
    public Boolean getKillFade() {
        return killFade;
    }

    public void setKillFade(Boolean killFade) {
        this.killFade = killFade;
    }

    @Basic
    @Column(name = "can_freeze", nullable = true)
    public Boolean getFreeze() {
        return freeze;
    }

    public void setFreeze(Boolean freeze) {
        this.freeze = freeze;
    }

    @Basic
    @Column(name = "teleport_self", nullable = true)
    public Boolean getTeleportSelf() {
        return teleportSelf;
    }

    public void setTeleportSelf(Boolean teleportSelf) {
        this.teleportSelf = teleportSelf;
    }

    @Basic
    @Column(name = "admin_items", nullable = true)
    public Boolean getAdminItems() {
        return adminItems;
    }

    public void setAdminItems(Boolean adminItems) {
        this.adminItems = adminItems;
    }

    @Basic
    @Column(name = "heal_self", nullable = true)
    public Boolean getHealSelf() {
        return healSelf;
    }

    public void setHealSelf(Boolean healSelf) {
        this.healSelf = healSelf;
    }

    @Basic
    @Column(name = "godlike_troop", nullable = true)
    public Boolean getGodlikeTroop() {
        return godlikeTroop;
    }

    public void setGodlikeTroop(Boolean godlikeTroop) {
        this.godlikeTroop = godlikeTroop;
    }

    @Basic
    @Column(name = "ships", nullable = true)
    public Boolean getShips() {
        return ships;
    }

    public void setShips(Boolean ships) {
        this.ships = ships;
    }

    @Basic
    @Column(name = "announce", nullable = true)
    public Boolean getAnnounce() {
        return announce;
    }

    public void setAnnounce(Boolean announce) {
        this.announce = announce;
    }

    @Basic
    @Column(name = "override_poll", nullable = true)
    public Boolean getOverridePoll() {
        return overridePoll;
    }

    public void setOverridePoll(Boolean overridePoll) {
        this.overridePoll = overridePoll;
    }

    @Basic
    @Column(name = "all_items", nullable = true)
    public Boolean getAllItems() {
        return allItems;
    }

    public void setAllItems(Boolean allItems) {
        this.allItems = allItems;
    }

    @Basic
    @Column(name = "mute", nullable = true)
    public Boolean getMute() {
        return mute;
    }

    public void setMute(Boolean mute) {
        this.mute = mute;
    }

    @Basic
    @Column(name = "animals", nullable = true)
    public Boolean getAnimals() {
        return animals;
    }

    public void setAnimals(Boolean animals) {
        this.animals = animals;
    }

    @Basic
    @Column(name = "join_factions", nullable = true)
    public Boolean getJoinFactions() {
        return joinFactions;
    }

    public void setJoinFactions(Boolean joinFactions) {
        this.joinFactions = joinFactions;
    }

    @Basic
    @Column(name = "factions", nullable = true)
    public Boolean getFactions() {
        return factions;
    }

    public void setFactions(Boolean factions) {
        this.factions = factions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminPermissions that = (AdminPermissions) o;
        return Objects.equals(server, that.server) &&
                Objects.equals(uniqueId, that.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, server.getId(), panel, gold, kick, temporaryBan, permanentBan, killFade, freeze, teleportSelf, adminItems, healSelf, godlikeTroop, ships, announce, overridePoll, allItems, mute, animals, joinFactions, factions);
    }
}

class AdminPermissionsPK implements Serializable {

    private Integer uniqueId;
    private Integer server;

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }
}