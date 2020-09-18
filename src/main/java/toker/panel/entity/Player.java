package toker.panel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import toker.panel.util.Constants;
import toker.panel.annotation.DefaultFactionValue;
import toker.panel.annotation.DefaultItemValue;
import toker.panel.annotation.DefaultTroopValue;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "player")
public class Player {

    private Integer id;
    private Integer uniqueId;
    private String name;
    private Integer gold = 0;
    private Integer hp = 100;
    private Integer food = 100;
    private Integer fatigue = 1000;
    private Integer posX = -1;
    private Integer posY = -1;
    private Integer posZ = -1;
    private Faction faction;
    private Troop troop;
    private String sceneName = "None";
    private String description1 = "<Not Set>";
    private String description2 = "<Not Set>";
    private String description3 = "<Not Set>";
    private Item bodyArmor;
    private Item headArmor;
    private Item footArmor;
    private Item handArmor;
    private Item item_0;
    private Item item_1;
    private Item item_2;
    private Item item_3;
    private Integer ammo_0 = 0;
    private Integer ammo_1 = 0;
    private Integer ammo_2 = 0;
    private Integer ammo_3 = 0;
    private Item horse;
    private Integer horseSlot = -1;
    private Item horse_0;
    private Item horse_1;
    private Item horse_2;
    private Timestamp horse_1_woundTime;
    private Timestamp horse_2_woundTime;
    private Timestamp horse_3_woundTime;
    private Integer horse_1_woundDuration = 0;
    private Integer horse_2_woundDuration = 0;
    private Integer horse_3_woundDuration = 0;
    private Integer horse_1_stableId = -1;
    private Integer horse_2_stableId = -1;
    private Integer horse_3_stableId = -1;
    private Integer ridingTier = 0;
    private Timestamp woundTime;
    private Integer woundDuration = 0;
    private Integer servedWoundTime = 0;
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

    @Column(name = "unique_id", nullable = false)
    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "gold", nullable = false)
    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    @Column(name = "hp", nullable = false)
    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    @Column(name = "food", nullable = false)
    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    @Column(name = "pos_x", nullable = false)
    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    @Column(name = "pos_y", nullable = false)
    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    @Column(name = "pos_z", nullable = false)
    public Integer getPosZ() {
        return posZ;
    }

    public void setPosZ(Integer posZ) {
        this.posZ = posZ;
    }

    @Column(name = "scene_name", nullable = false, length = 32)
    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    @Column(name = "description_1", nullable = false, length = 2048)
    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    @Column(name = "description_2", nullable = false, length = 2048)
    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    @Column(name = "description_3", nullable = false, length = 2048)
    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    @ManyToOne
    @JoinColumn(name = "body_armor_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getBodyArmor() {
        return bodyArmor;
    }

    public void setBodyArmor(Item bodyArmor) {
        this.bodyArmor = bodyArmor;
    }

    @ManyToOne
    @JoinColumn(name = "head_armor_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 3)
    @JsonView(View.Items.class)
    public Item getHeadArmor() {
        return headArmor;
    }

    public void setHeadArmor(Item headArmor) {
        this.headArmor = headArmor;
    }

    @ManyToOne
    @JoinColumn(name = "foot_armor_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getFootArmor() {
        return footArmor;
    }

    public void setFootArmor(Item footArmor) {
        this.footArmor = footArmor;
    }

    @ManyToOne
    @JoinColumn(name = "hand_armor_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getHandArmor() {
        return handArmor;
    }

    public void setHandArmor(Item glovesArmor) {
        this.handArmor = glovesArmor;
    }

    @ManyToOne
    @JoinColumn(name = "item_0_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getItem_0() {
        return item_0;
    }

    public void setItem_0(Item item0) {
        this.item_0 = item0;
    }

    @ManyToOne
    @JoinColumn(name = "item_1_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getItem_1() {
        return item_1;
    }

    public void setItem_1(Item item1) {
        this.item_1 = item1;
    }

    @ManyToOne
    @JoinColumn(name = "item_2_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getItem_2() {
        return item_2;
    }

    public void setItem_2(Item item2) {
        this.item_2 = item2;
    }

    @ManyToOne
    @JoinColumn(name = "item_3_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getItem_3() {
        return item_3;
    }

    public void setItem_3(Item item3) {
        this.item_3 = item3;
    }

    @Column(name = "ammo_0", nullable = false)
    public Integer getAmmo_0() {
        return ammo_0;
    }

    public void setAmmo_0(Integer ammo0) {
        this.ammo_0 = ammo0;
    }

    @Column(name = "ammo_1", nullable = false)
    public Integer getAmmo_1() {
        return ammo_1;
    }

    public void setAmmo_1(Integer ammo1) {
        this.ammo_1 = ammo1;
    }

    @Column(name = "ammo_2", nullable = false)
    public Integer getAmmo_2() {
        return ammo_2;
    }

    public void setAmmo_2(Integer ammo2) {
        this.ammo_2 = ammo2;
    }

    @Column(name = "ammo_3", nullable = false)
    public Integer getAmmo_3() {
        return ammo_3;
    }

    public void setAmmo_3(Integer ammo3) {
        this.ammo_3 = ammo3;
    }

    @ManyToOne
    @JoinColumn(name = "horse_0_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getHorse_0() {
        return horse_0;
    }

    public void setHorse_0(Item horse1) {
        this.horse_0 = horse1;
    }

    @ManyToOne
    @JoinColumn(name = "horse_1_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getHorse_1() {
        return horse_1;
    }

    public void setHorse_1(Item horse2) {
        this.horse_1 = horse2;
    }

    @ManyToOne
    @JoinColumn(name = "horse_2_id", referencedColumnName = "id",nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getHorse_2() {
        return horse_2;
    }

    public void setHorse_2(Item horse3) {
        this.horse_2 = horse3;
    }

    @Column(name = "fatigue", nullable = false)
    public Integer getFatigue() {
        return fatigue;
    }

    public void setFatigue(Integer fatigue) {
        this.fatigue = fatigue;
    }

    @ManyToOne
    @JoinColumn(name = "horse_id", referencedColumnName = "id", nullable = false)
    @DefaultItemValue(itemId = 0)
    @JsonView(View.Items.class)
    public Item getHorse() {
        return horse;
    }

    public void setHorse(Item horseId) {
        this.horse = horseId;
    }

    @Column(name = "horse_slot", nullable = false)
    public Integer getHorseSlot() {
        return horseSlot;
    }

    public void setHorseSlot(Integer horseSlot) {
        this.horseSlot = horseSlot;
    }

    @Column(name = "horse_1_wound_time")
    public Timestamp getHorse_1_woundTime() {
        return horse_1_woundTime;
    }

    public void setHorse_1_woundTime(Timestamp horse1WoundTime) {
        this.horse_1_woundTime = horse1WoundTime;
    }

    @Column(name = "horse_2_wound_time")
    public Timestamp getHorse_2_woundTime() {
        return horse_2_woundTime;
    }

    public void setHorse_2_woundTime(Timestamp horse2WoundTime) {
        this.horse_2_woundTime = horse2WoundTime;
    }

    @Column(name = "horse_3_wound_time")
    public Timestamp getHorse_3_woundTime() {
        return horse_3_woundTime;
    }

    public void setHorse_3_woundTime(Timestamp horse3WoundTime) {
        this.horse_3_woundTime = horse3WoundTime;
    }

    @Column(name = "horse_1_wound_duration", nullable = false)
    public Integer getHorse_1_woundDuration() {
        return horse_1_woundDuration;
    }

    public void setHorse_1_woundDuration(Integer horse1WoundDuration) {
        this.horse_1_woundDuration = horse1WoundDuration;
    }

    @Column(name = "horse_2_wound_duration", nullable = false)
    public Integer getHorse_2_woundDuration() {
        return horse_2_woundDuration;
    }

    public void setHorse_2_woundDuration(Integer horse2WoundDuration) {
        this.horse_2_woundDuration = horse2WoundDuration;
    }

    @Column(name = "horse_3_wound_duration", nullable = false)
    public Integer getHorse_3_woundDuration() {
        return horse_3_woundDuration;
    }

    public void setHorse_3_woundDuration(Integer horse3WoundDuration) {
        this.horse_3_woundDuration = horse3WoundDuration;
    }

    @Column(name = "horse_1_stable_id", nullable = false)
    public Integer getHorse_1_stableId() {
        return horse_1_stableId;
    }

    public void setHorse_1_stableId(Integer horse1StableId) {
        this.horse_1_stableId = horse1StableId;
    }

    @Column(name = "horse_2_stable_id", nullable = false)
    public Integer getHorse_2_stableId() {
        return horse_2_stableId;
    }

    public void setHorse_2_stableId(Integer horse2StableId) {
        this.horse_2_stableId = horse2StableId;
    }

    @Column(name = "horse_3_stable_id", nullable = false)
    public Integer getHorse_3_stableId() {
        return horse_3_stableId;
    }

    public void setHorse_3_stableId(Integer horse3StableId) {
        this.horse_3_stableId = horse3StableId;
    }

    @Column(name = "riding_tier", nullable = false)
    public Integer getRidingTier() {
        return ridingTier;
    }

    public void setRidingTier(Integer ridingTier) {
        this.ridingTier = ridingTier;
    }

    @Column(name = "wound_time")
    public Timestamp getWoundTime() {
        return woundTime;
    }

    public void setWoundTime(Timestamp woundTime) {
        this.woundTime = woundTime;
    }

    @Column(name = "wound_duration", nullable = false)
    public Integer getWoundDuration() {
        return woundDuration;
    }

    public void setWoundDuration(Integer woundDuration) {
        this.woundDuration = woundDuration;
    }

    @Column(name = "served_wound_time", nullable = false)
    public Integer getServedWoundTime() {
        return servedWoundTime;
    }

    public void setServedWoundTime(Integer servedWoundTime) {
        this.servedWoundTime = servedWoundTime;
    }

    @Column(name = "treatment_time")
    public Timestamp getTreatmentTime() {
        return treatmentTime;
    }

    public void setTreatmentTime(Timestamp treatmentTime) {
        this.treatmentTime = treatmentTime;
    }

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
    @JoinColumns({
            @JoinColumn(name = "faction_index", referencedColumnName = "index", nullable = false),
            @JoinColumn(name = "server_id", referencedColumnName = "server_id", nullable = false)
    })
    @DefaultFactionValue(index = 0)
    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    @ManyToOne
    @JoinColumn(name = "troop_id", referencedColumnName = "id", nullable = false)
    @DefaultTroopValue(id = Constants.DEFAULT_TROOP_ID)
    public Troop getTroop() {
        return troop;
    }

    public void setTroop(Troop troop) {
        this.troop = troop;
    }

    @Transient
    public boolean isWounded() {
        if (getWoundTime() == null)
            return false;

        if (getTreatmentTime() == null || getTreatmentTime().before(getWoundTime())) {
            return getServedWoundTime() < getWoundDuration() * 60;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getWoundTime());
        calendar.add(Calendar.HOUR, getWoundDuration());

        return calendar.toInstant().isAfter(Instant.now());
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

    public interface View {
        interface Server {}
        interface Faction {}
        interface Troop {}
        interface Items {}
        interface None {}
    }
}
