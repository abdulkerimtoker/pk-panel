package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "player")
public class Player {
    private Integer id;
    private Integer uniqueId;
    private String name;
    private Integer gold;
    private Integer hp;
    private Integer food;
    private Integer fatigue;
    private Integer posX;
    private Integer posY;
    private Integer posZ;
    private Faction faction;
    private Troop troop;
    private String sceneName;
    private String description1;
    private String description2;
    private String description3;
    private Integer bodyArmor;
    private Integer headArmor;
    private Integer footArmor;
    private Integer glovesArmor;
    private Integer item0;
    private Integer item1;
    private Integer item2;
    private Integer item3;
    private Integer ammo0;
    private Integer ammo1;
    private Integer ammo2;
    private Integer ammo3;
    private Integer horseId;
    private Integer horse1;
    private Integer horse2;
    private Integer horse3;
    private Timestamp horse1WoundTime;
    private Timestamp horse2WoundTime;
    private Timestamp horse3WoundTime;
    private Integer horse1WoundDuration;
    private Integer horse2WoundDuration;
    private Integer horse3WoundDuration;
    private Integer horse1StableId;
    private Integer horse2StableId;
    private Integer horse3StableId;
    private Integer ridingTier;
    private Timestamp woundTime;
    private Integer woundDuration;
    private Integer servedWoundTime;
    private Timestamp treatmentTime;
    private Timestamp lastLogTime;
    private Integer version;
    private Collection<DoorKey> doorKeys;
    private Collection<Inventory> inventories;
    private Collection<NoticeBoardAccess> noticeBoardAccesses;
    private Collection<ProfessionAssignment> professionAssignments;
    private Collection<CraftingRequest> craftingRequests;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
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
    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "gold", nullable = false)
    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    @Basic
    @Column(name = "hp", nullable = false)
    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    @Basic
    @Column(name = "food", nullable = false)
    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    @Basic
    @Column(name = "pos_x", nullable = false)
    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    @Basic
    @Column(name = "pos_y", nullable = false)
    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    @Basic
    @Column(name = "pos_z", nullable = false)
    public Integer getPosZ() {
        return posZ;
    }

    public void setPosZ(Integer posZ) {
        this.posZ = posZ;
    }

    @Basic
    @Column(name = "scene_name", nullable = false, length = 32)
    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    @Basic
    @Column(name = "description_1", nullable = false, length = 128)
    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    @Basic
    @Column(name = "description_2", nullable = false, length = 128)
    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    @Basic
    @Column(name = "description_3", nullable = false, length = 128)
    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    @Basic
    @Column(name = "body_armor", nullable = false)
    public Integer getBodyArmor() {
        return bodyArmor;
    }

    public void setBodyArmor(Integer bodyArmor) {
        this.bodyArmor = bodyArmor;
    }

    @Basic
    @Column(name = "head_armor", nullable = false)
    public Integer getHeadArmor() {
        return headArmor;
    }

    public void setHeadArmor(Integer headArmor) {
        this.headArmor = headArmor;
    }

    @Basic
    @Column(name = "foot_armor", nullable = false)
    public Integer getFootArmor() {
        return footArmor;
    }

    public void setFootArmor(Integer footArmor) {
        this.footArmor = footArmor;
    }

    @Basic
    @Column(name = "gloves_armor", nullable = false)
    public Integer getGlovesArmor() {
        return glovesArmor;
    }

    public void setGlovesArmor(Integer glovesArmor) {
        this.glovesArmor = glovesArmor;
    }

    @Basic
    @Column(name = "item_0", nullable = false)
    public Integer getItem0() {
        return item0;
    }

    public void setItem0(Integer item0) {
        this.item0 = item0;
    }

    @Basic
    @Column(name = "item_1", nullable = false)
    public Integer getItem1() {
        return item1;
    }

    public void setItem1(Integer item1) {
        this.item1 = item1;
    }

    @Basic
    @Column(name = "item_2", nullable = false)
    public Integer getItem2() {
        return item2;
    }

    public void setItem2(Integer item2) {
        this.item2 = item2;
    }

    @Basic
    @Column(name = "item_3", nullable = false)
    public Integer getItem3() {
        return item3;
    }

    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    @Basic
    @Column(name = "ammo_0", nullable = false)
    public Integer getAmmo0() {
        return ammo0;
    }

    public void setAmmo0(Integer ammo0) {
        this.ammo0 = ammo0;
    }

    @Basic
    @Column(name = "ammo_1", nullable = false)
    public Integer getAmmo1() {
        return ammo1;
    }

    public void setAmmo1(Integer ammo1) {
        this.ammo1 = ammo1;
    }

    @Basic
    @Column(name = "ammo_2", nullable = false)
    public Integer getAmmo2() {
        return ammo2;
    }

    public void setAmmo2(Integer ammo2) {
        this.ammo2 = ammo2;
    }

    @Basic
    @Column(name = "ammo_3", nullable = false)
    public Integer getAmmo3() {
        return ammo3;
    }

    public void setAmmo3(Integer ammo3) {
        this.ammo3 = ammo3;
    }

    @Basic
    @Column(name = "horse_1", nullable = false)
    public Integer getHorse1() {
        return horse1;
    }

    public void setHorse1(Integer horse1) {
        this.horse1 = horse1;
    }

    @Basic
    @Column(name = "horse_2", nullable = false)
    public Integer getHorse2() {
        return horse2;
    }

    public void setHorse2(Integer horse2) {
        this.horse2 = horse2;
    }

    @Basic
    @Column(name = "horse_3", nullable = false)
    public Integer getHorse3() {
        return horse3;
    }

    public void setHorse3(Integer horse3) {
        this.horse3 = horse3;
    }

    @Basic
    @Column(name = "fatigue", nullable = false)
    public Integer getFatigue() {
        return fatigue;
    }

    public void setFatigue(Integer fatigue) {
        this.fatigue = fatigue;
    }

    @Basic
    @Column(name = "horse_id", nullable = false)
    public Integer getHorseId() {
        return horseId;
    }

    public void setHorseId(Integer horseId) {
        this.horseId = horseId;
    }

    @Basic
    @Column(name = "horse_1_wound_time")
    public Timestamp getHorse1WoundTime() {
        return horse1WoundTime;
    }

    public void setHorse1WoundTime(Timestamp horse1WoundTime) {
        this.horse1WoundTime = horse1WoundTime;
    }

    @Basic
    @Column(name = "horse_2_wound_time")
    public Timestamp getHorse2WoundTime() {
        return horse2WoundTime;
    }

    public void setHorse2WoundTime(Timestamp horse2WoundTime) {
        this.horse2WoundTime = horse2WoundTime;
    }

    @Basic
    @Column(name = "horse_3_wound_time")
    public Timestamp getHorse3WoundTime() {
        return horse3WoundTime;
    }

    public void setHorse3WoundTime(Timestamp horse3WoundTime) {
        this.horse3WoundTime = horse3WoundTime;
    }

    @Basic
    @Column(name = "horse_1_wound_duration", nullable = false)
    public Integer getHorse1WoundDuration() {
        return horse1WoundDuration;
    }

    public void setHorse1WoundDuration(Integer horse1WoundDuration) {
        this.horse1WoundDuration = horse1WoundDuration;
    }

    @Basic
    @Column(name = "horse_2_wound_duration", nullable = false)
    public Integer getHorse2WoundDuration() {
        return horse2WoundDuration;
    }

    public void setHorse2WoundDuration(Integer horse2WoundDuration) {
        this.horse2WoundDuration = horse2WoundDuration;
    }

    @Basic
    @Column(name = "horse_3_wound_duration", nullable = false)
    public Integer getHorse3WoundDuration() {
        return horse3WoundDuration;
    }

    public void setHorse3WoundDuration(Integer horse3WoundDuration) {
        this.horse3WoundDuration = horse3WoundDuration;
    }

    @Basic
    @Column(name = "horse_1_stable_id", nullable = false)
    public Integer getHorse1StableId() {
        return horse1StableId;
    }

    public void setHorse1StableId(Integer horse1StableId) {
        this.horse1StableId = horse1StableId;
    }

    @Basic
    @Column(name = "horse_2_stable_id", nullable = false)
    public Integer getHorse2StableId() {
        return horse2StableId;
    }

    public void setHorse2StableId(Integer horse2StableId) {
        this.horse2StableId = horse2StableId;
    }

    @Basic
    @Column(name = "horse_3_stable_id", nullable = false)
    public Integer getHorse3StableId() {
        return horse3StableId;
    }

    public void setHorse3StableId(Integer horse3StableId) {
        this.horse3StableId = horse3StableId;
    }

    @Basic
    @Column(name = "riding_tier", nullable = false)
    public Integer getRidingTier() {
        return ridingTier;
    }

    public void setRidingTier(Integer ridingTier) {
        this.ridingTier = ridingTier;
    }

    @Basic
    @Column(name = "wound_time")
    public Timestamp getWoundTime() {
        return woundTime;
    }

    public void setWoundTime(Timestamp woundTime) {
        this.woundTime = woundTime;
    }

    @Basic
    @Column(name = "wound_duration", nullable = false)
    public Integer getWoundDuration() {
        return woundDuration;
    }

    public void setWoundDuration(Integer woundDuration) {
        this.woundDuration = woundDuration;
    }

    @Basic
    @Column(name = "served_wound_time", nullable = false)
    public Integer getServedWoundTime() {
        return servedWoundTime;
    }

    public void setServedWoundTime(Integer servedWoundTime) {
        this.servedWoundTime = servedWoundTime;
    }

    @Basic
    @Column(name = "treatment_time")
    public Timestamp getTreatmentTime() {
        return treatmentTime;
    }

    public void setTreatmentTime(Timestamp treatmentTime) {
        this.treatmentTime = treatmentTime;
    }

    @Basic
    @Column(name = "last_log_time")
    public Timestamp getLastLogTime() {
        return lastLogTime;
    }

    public void setLastLogTime(Timestamp lastLogTime) {
        this.lastLogTime = lastLogTime;
    }

    @Column(name = "version", nullable = false)
    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    public Collection<DoorKey> getDoorKeys() {
        return doorKeys;
    }

    public void setDoorKeys(Collection<DoorKey> doorKeys) {
        this.doorKeys = doorKeys;
    }

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    public Collection<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(Collection<Inventory> inventories) {
        this.inventories = inventories;
    }

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    public Collection<NoticeBoardAccess> getNoticeBoardAccesses() {
        return noticeBoardAccesses;
    }

    public void setNoticeBoardAccesses(Collection<NoticeBoardAccess> noticeBoardAccesses) {
        this.noticeBoardAccesses = noticeBoardAccesses;
    }

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    public Collection<ProfessionAssignment> getProfessionAssignments() {
        return professionAssignments;
    }

    public void setProfessionAssignments(Collection<ProfessionAssignment> professionAssignments) {
        this.professionAssignments = professionAssignments;
    }

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    public Collection<CraftingRequest> getCraftingRequests() {
        return craftingRequests;
    }

    public void setCraftingRequests(Collection<CraftingRequest> craftingRequests) {
        this.craftingRequests = craftingRequests;
    }

    @ManyToOne
    @JoinColumn(name = "faction_id", referencedColumnName = "id", nullable = false)
    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    @ManyToOne
    @JoinColumn(name = "troop_id", referencedColumnName = "id", nullable = false)
    public Troop getTroop() {
        return troop;
    }

    public void setTroop(Troop troop) {
        this.troop = troop;
    }
}
