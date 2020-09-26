package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonView
import toker.panel.annotation.DefaultFactionValue
import toker.panel.annotation.DefaultItemValue
import toker.panel.annotation.DefaultTroopValue
import toker.panel.util.Constants
import java.sql.Timestamp
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "player")
data class Player(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "unique_id", nullable = false)
        var uniqueId: Int? = null,

        @Column(name = "name", nullable = false, length = 32)
        var name: String? = null,

        @Column(name = "gold", nullable = false)
        var gold:Int  = 0,

        @Column(name = "hp", nullable = false)
        var hp:Int  = 100,

        @Column(name = "food", nullable = false)
        var food:Int  = 100,

        @Column(name = "fatigue", nullable = false)
        var fatigue:Int  = 1000,

        @Column(name = "pos_x", nullable = false)
        var posX:Int  = -1,

        @Column(name = "pos_y", nullable = false)
        var posY:Int  = -1,

        @Column(name = "pos_z", nullable = false)
        var posZ:Int = -1,

        @ManyToOne
        @JoinColumns(JoinColumn(name = "faction_index", referencedColumnName = "index", nullable = false), JoinColumn(name = "server_id", referencedColumnName = "server_id", nullable = false))
        @get:DefaultFactionValue(index = 0)
        var faction: Faction? = null,

        @ManyToOne
        @JoinColumn(name = "troop_id", referencedColumnName = "id", nullable = false)
        @get:DefaultTroopValue(id = Constants.DEFAULT_TROOP_ID)
        var troop: Troop? = null,

        @Column(name = "scene_name", nullable = false, length = 32)
        var sceneName: String = "None",

        @Column(name = "description_1", nullable = false, length = 2048)
        var description1: String = "<Not Set>",

        @Column(name = "description_2", nullable = false, length = 2048)
        var description2: String = "<Not Set>",

        @Column(name = "description_3", nullable = false, length = 2048)
        var description3: String = "<Not Set>",

        @ManyToOne
        @JoinColumn(name = "body_armor_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 0)
        @JsonView(View.Items::class)
        var bodyArmor: Item? = null,

        @ManyToOne
        @JoinColumn(name = "head_armor_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 3)
        @JsonView(View.Items::class)
        var headArmor: Item? = null,

        @ManyToOne
        @JoinColumn(name = "foot_armor_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 0)
        @JsonView(View.Items::class)
        var footArmor: Item? = null,

        @ManyToOne
        @JoinColumn(name = "hand_armor_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 0)
        @JsonView(View.Items::class)
        var handArmor: Item? = null,

        @ManyToOne
        @JoinColumn(name = "item_0_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 0)
        @JsonView(View.Items::class)
        var item_0: Item? = null,

        @JsonView(View.Items::class)
        @get:DefaultItemValue(itemId = 0)
        @JoinColumn(name = "item_1_id", referencedColumnName = "id", nullable = false)
        @ManyToOne
        var item_1: Item? = null,

        @ManyToOne
        @JoinColumn(name = "item_2_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 0)
        @JsonView(View.Items::class)
        var item_2: Item? = null,

        @ManyToOne
        @JoinColumn(name = "item_3_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 0)
        @JsonView(View.Items::class)
        var item_3: Item? = null,

        @Column(name = "ammo_0", nullable = false)
        var ammo_0: Int = 0,

        @Column(name = "ammo_1", nullable = false)
        var ammo_1: Int = 0,

        @Column(name = "ammo_2", nullable = false)
        var ammo_2: Int = 0,

        @Column(name = "ammo_3", nullable = false)
        var ammo_3: Int = 0,

        @ManyToOne
        @JoinColumn(name = "horse_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 0)
        @JsonView(View.Items::class)
        var horse: Item? = null,

        @Column(name = "horse_slot", nullable = false)
        var horseSlot: Int = -1,

        @ManyToOne
        @JoinColumn(name = "horse_0_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 0)
        @JsonView(View.Items::class)
        var horse_0: Item? = null,

        @ManyToOne
        @JoinColumn(name = "horse_1_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 0)
        @JsonView(View.Items::class)
        var horse_1: Item? = null,

        @ManyToOne
        @JoinColumn(name = "horse_2_id", referencedColumnName = "id", nullable = false)
        @get:DefaultItemValue(itemId = 0)
        @JsonView(View.Items::class)
        var horse_2: Item? = null,

        @Column(name = "horse_1_wound_time")
        var horse_1_woundTime: Timestamp? = null,

        @Column(name = "horse_2_wound_time")
        var horse_2_woundTime: Timestamp? = null,

        @Column(name = "horse_3_wound_time")
        var horse_3_woundTime: Timestamp? = null,

        @Column(name = "horse_1_wound_duration", nullable = false)
        var horse_1_woundDuration: Int = 0,

        @Column(name = "horse_2_wound_duration", nullable = false)
        var horse_2_woundDuration: Int = 0,

        @Column(name = "horse_3_wound_duration", nullable = false)
        var horse_3_woundDuration: Int = 0,

        @Column(name = "horse_1_stable_id", nullable = false)
        var horse_1_stableId: Int = -1,

        @Column(name = "horse_2_stable_id", nullable = false)
        var horse_2_stableId: Int = -1,

        @Column(name = "horse_3_stable_id", nullable = false)
        var horse_3_stableId: Int = -1,

        @Column(name = "riding_tier", nullable = false)
        var ridingTier: Int = 0,

        @Column(name = "wound_time")
        var woundTime: Timestamp? = null,

        @Column(name = "wound_duration", nullable = false)
        var woundDuration: Int = 0,

        @Column(name = "served_wound_time", nullable = false)
        var servedWoundTime: Int = 0,

        @Column(name = "treatment_time")
        var treatmentTime: Timestamp? = null,

        @Column(name = "last_log_time")
        var lastLogTime: Timestamp? = null,

        @Version
        @Column(name = "version", nullable = false)
        var version: Int? = null,

        @OneToMany(mappedBy = "player")
        @JsonIgnore
        var doorKeys: MutableSet<DoorKey>? = null,

        @OneToMany(mappedBy = "player")
        @JsonIgnore
        var inventories: MutableSet<Inventory>? = null,

        @OneToMany(mappedBy = "player")
        @JsonIgnore
        var noticeBoardAccesses: MutableSet<NoticeBoardAccess>? = null,
        
        @OneToMany(mappedBy = "player")
        @JsonIgnore
        var professionAssignments: MutableSet<ProfessionAssignment>? = null,

        @OneToMany(mappedBy = "player")
        @JsonIgnore
        var craftingRequests: MutableSet<CraftingRequest>? = null
) {
    @get:Transient
    val isWounded: Boolean
        get() {
            if (woundTime == null) return false
            if (treatmentTime == null || treatmentTime!!.before(woundTime)) {
                return servedWoundTime < woundDuration * 60
            }
            val calendar = Calendar.getInstance()
            calendar.time = woundTime
            calendar.add(Calendar.HOUR, woundDuration)
            return calendar.toInstant().isAfter(Instant.now())
        }

    interface View {
        interface Server
        interface Faction
        interface Troop
        interface Items
        interface None
    }
}