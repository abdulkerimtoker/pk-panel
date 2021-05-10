package toker.panel.service

import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import toker.panel.bean.SelectedServerId
import toker.panel.entity.*
import toker.panel.entity.pk.LanguageProficiencyPK
import toker.panel.repository.*
import toker.panel.util.Constants
import java.sql.Timestamp
import java.time.Instant
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.persistence.EntityManager
import javax.persistence.LockModeType
import javax.persistence.criteria.*
import javax.persistence.metamodel.PluralAttribute
import kotlin.collections.LinkedHashSet

@Service
class PlayerService(private val playerRepository: PlayerRepository,
                    private val inventoryRepository: InventoryRepository,
                    private val inventorySlotRepository: InventorySlotRepository,
                    private val doorKeyRepository: DoorKeyRepository,
                    private val noticeBoadAccessRepository: NoticeBoadAccessRepository,
                    private val professionAssignmentRepository: ProfessionAssignmentRepository,
                    private val craftingRequestRepository: CraftingRequestRepository,
                    private val itemRepository: ItemRepository,
                    private val adminPermissionsRepository: AdminPermissionsRepository,
                    private val startingItemRepo: StartingItemRepository,
                    private val proficiencyRepo: LanguageProficiencyRepository,
                    private val languageRepo: LanguageRepository,
                    private val ipRecordRepository: IpRecordRepository,
                    private val serverService: ServerService) {

    fun getPlayer(id: Int): Optional<Player> {
        return playerRepository.findById(id)
    }

    fun getPlayer(name: String?, serverId: Int): Optional<Player> {
        return playerRepository.findOne { root: Root<Player>, query, builder: CriteriaBuilder ->
            val join: Join<Player, Faction> = root.join(Player_.faction)
            builder.and(
                    builder.equal(root.get(Player_.name), name),
                    builder.equal(join.get(Faction_.server).get(Server_.id), serverId)
            )
        }
    }

    fun getPlayer(name: String, serverId: Int, with: PluralAttribute<Player, out MutableCollection<*>?, *>): Optional<Player> {
        return playerRepository.findOne { root: Root<Player>, _, builder: CriteriaBuilder ->
            root.fetch(with, JoinType.LEFT)
            val join: Join<Player, Faction> = root.join(Player_.faction)
            builder.and(
                    builder.equal(root.get(Player_.name), name),
                    builder.equal(join.get(Faction_.server).get(Server_.id), serverId)
            )
        }
    }

    fun getPlayer(name: String, serverId: Int, uniqueId: Int): Optional<Player> {
        return playerRepository.findOne { root: Root<Player>, _, builder: CriteriaBuilder ->
            val join: Join<Player, Faction> = root.join(Player_.faction)
            builder.and(
                    builder.equal(root.get(Player_.name), name),
                    builder.equal(root.get(Player_.uniqueId), uniqueId),
                    builder.equal(join.get(Faction_.server).get(Server_.id), serverId)
            )
        }
    }

    fun getPlayer(name: String, serverId: Int, uniqueId: Int,
                  with: PluralAttribute<Player, out MutableCollection<*>, *>): Optional<Player> {
        return playerRepository.findOne { root: Root<Player>, _, builder: CriteriaBuilder ->
            root.fetch(with, JoinType.LEFT)
            val join: Join<Player, Faction> = root.join(Player_.faction)
            builder.and(
                    builder.equal(root.get(Player_.name), name),
                    builder.equal(root.get(Player_.uniqueId), uniqueId),
                    builder.equal(join.get(Faction_.server).get(Server_.id), serverId)
            )
        }
    }

    fun savePlayer(player: Player): Player {
        return playerRepository.saveAndFlush(player)
    }

    fun searchPlayers(searchTerm: String): List<Player> {
        return playerRepository.likeSearch(searchTerm, SelectedServerId)
    }

    fun getPlayerInventory(playerId: Int): Inventory? {
        return inventoryRepository.findOne { root: Root<Inventory>, _, builder: CriteriaBuilder ->
            root.fetch(Inventory_.slots, JoinType.LEFT)
            builder.equal(root.get<Any>("player").get<Any>("id"), playerId)
        }.orElse(null)
    }

    @Throws(ChangeSetPersister.NotFoundException::class)
    fun getInventory(inventoryId: Int): Inventory {
        return inventoryRepository.findOne { root: Root<Inventory>, _, builder: CriteriaBuilder ->
            root.fetch(Inventory_.slots, JoinType.LEFT)
            builder.equal(root.get<Any>("id"), inventoryId)
        }.orElseThrow { ChangeSetPersister.NotFoundException() }
    }

    fun updateInventorySlot(inventoryId: Int, inventorySlot: InventorySlot): InventorySlot {
        inventorySlot.inventory = inventoryRepository.getOne(inventoryId)
        return inventorySlotRepository.saveAndFlush(inventorySlot)
    }

    fun updateInventorySlot(inventoryId: Int, slot: Int, itemId: Int, ammo: Int) {
        inventorySlotRepository.updateSlot(inventoryRepository.getOne(inventoryId),
                slot, itemRepository.getOne(itemId), ammo)
    }

    fun getPlayerDoorKeys(playerId: Int): List<DoorKey> {
        return doorKeyRepository.findAllByPlayerId(playerId)
    }

    fun getPlayerBoardAccesses(playerId: Int): List<NoticeBoardAccess> {
        return noticeBoadAccessRepository.findAllByPlayerId(playerId)
    }

    fun getPlayerProfessions(playerId: Int): List<ProfessionAssignment> {
        return professionAssignmentRepository.findAllByPlayerId(playerId)
    }

    fun getPlayerCraftingRequests(playerId: Int): List<CraftingRequest> {
        return craftingRequestRepository.findAllByPlayerId(playerId)
    }

    fun woundPlayer(player: Player) {
        var woundDuration = 48
        val conf = serverService.getServerConfiguration(
                player.faction!!.server!!.id!!, "CONF_WOUND_TIME").orElse(null)
        if (conf != null) {
            woundDuration = conf.value!!.toInt()
        }
        playerRepository.wound(player.id!!, woundDuration)
    }

    fun treatPlayer(player: Player, patient: Player): Boolean {
        if (patient.treatmentTime != null &&
                patient.treatmentTime!!.after(patient.woundTime)) {
            return false
        }
        var healingConstant = 12
        var tierMultiplier = 0
        val treated = AtomicBoolean(false)
        val healingConf = serverService.getServerConfiguration(
                player.faction!!.server!!.id!!, "CONF_TREATMENT_CONSTANT").orElse(null)
        if (healingConf != null) {
            healingConstant = healingConf.value!!.toInt()
        }
        val multiplierConf = serverService.getServerConfiguration(
                player.faction!!.server!!.id!!, "CONF_TREATMENT_TIER_MULTIPLIER").orElse(null)
        if (multiplierConf != null) {
            tierMultiplier = multiplierConf.value!!.toInt()
        }
        val professionAssignments: Collection<ProfessionAssignment> = getPlayerProfessions(player.id!!)
        val finalHealingConstant = healingConstant
        val finalTierMultiplier = tierMultiplier
        professionAssignments.forEach(Consumer { professionAssignment ->
            if (professionAssignment.profession!!.name == "Surgeon") {
                getPlayerInventory(player.id!!)?.slots!!.forEach(Consumer { inventorySlot: InventorySlot ->
                    if (!treated.get() && inventorySlot.item!!.id == 617) {
                        inventorySlot.item = itemRepository.getOne(0)
                        inventorySlotRepository.save(inventorySlot)
                        val newWoundDuration = patient.woundDuration -
                                (finalHealingConstant + finalTierMultiplier * professionAssignment.tier!!)
                        patient.treatmentTime = Timestamp.from(Instant.now())
                        patient.woundDuration = newWoundDuration
                        savePlayer(patient)
                        treated.set(true)
                    }
                })
            }
        })
        return treated.get()
    }

    fun createInventory(player: Player): Inventory {
        val inventory = Inventory()
        inventory.player = player
        inventory.size = 20
        val slots = LinkedHashSet<InventorySlot>()
        for (i in 1..20) {
            val slot = InventorySlot()
            slot.inventory = inventory
            slot.item = itemRepository.getOne(0)
            slot.ammo = 0
            slot.slot = i
            slots.add(slot)
        }
        inventory.slots = slots
        return inventoryRepository.saveAndFlush(inventory)
    }

    fun getAdminPermissions(serverId: Int, uniqueId: Int): Optional<AdminPermissions> {
        return adminPermissionsRepository.findByServerIdAndUniqueId(serverId, uniqueId)
    }

    fun spawnStartingGear(player: Player, serverId: Int): Player {
        val startingItems = startingItemRepo.findAll { root: Root<StartingItem?>, _, builder: CriteriaBuilder -> builder.equal(root.get<Server>(toker.panel.entity.StartingItem_.server).get<Int>(Server_.id), serverId) }
        val headArmors: MutableList<Item> = startingItems.stream()
                .map(StartingItem::item)
                .filter { it!!.type!!.id == Constants.HEAD_ARMOR }
                .collect(Collectors.toList())
        val bodyArmors: MutableList<Item> = startingItems.stream()
                .map(StartingItem::item)
                .filter { it!!.type!!.id == Constants.BODY_ARMOR }
                .collect(Collectors.toList())
        val footArmors: MutableList<Item> = startingItems.stream()
                .map(StartingItem::item)
                .filter { it!!.type!!.id == Constants.FOOT_ARMOR }
                .collect(Collectors.toList())
        val handArmors: MutableList<Item> = startingItems.stream()
                .map(StartingItem::item)
                .filter { it!!.type!!.id == Constants.HAND_ARMOR }
                .collect(Collectors.toList())
        val random = Random()
        if (headArmors.isNotEmpty()) player.headArmor = headArmors[random.nextInt(headArmors.size - 1)]
        if (bodyArmors.isNotEmpty()) player.bodyArmor = bodyArmors[random.nextInt(bodyArmors.size - 1)]
        if (footArmors.isNotEmpty()) player.footArmor = footArmors[random.nextInt(footArmors.size - 1)]
        if (handArmors.isNotEmpty()) player.handArmor = handArmors[random.nextInt(handArmors.size - 1)]
        return player
    }

    fun getLanguageProficiencies(playerId: Int): List<LanguageProficiency> {
        return proficiencyRepo.findAll { root: Root<LanguageProficiency?>, _, builder: CriteriaBuilder ->
            builder.equal(root.get(LanguageProficiency_.player).get(Player_.id), playerId) }
    }

    fun assignLanguageProficiency(playerId: Int, languageId: Int): LanguageProficiency {
        val proficiency = LanguageProficiency()
        proficiency.player = playerRepository.getOne(playerId)
        proficiency.language = languageRepo.getOne(languageId)
        return proficiencyRepo.saveAndFlush(proficiency)
    }

    fun revokeLanguageProficiency(playerId: Int, languageId: Int) {
        proficiencyRepo.deleteById(LanguageProficiencyPK(playerId, languageId))
    }

    fun getAltCharacters(guid: Int, serverId: Int): List<Player> {
        val guids = findAltGuids(guid, serverId)
        return playerRepository.findAll { root, _, builder ->
            val join = root.join(Player_.faction).join(Faction_.server)
            val inClause = builder.`in`(root.get(Player_.uniqueId))
            guids.forEach { inClause.value(it) }
            builder.and(
                inClause,
                builder.equal(join.get(Server_.id), serverId)
            )
        }
    }

    fun findAltGuids(guid: Int, serverId: Int, set: MutableSet<Int> = LinkedHashSet()): MutableSet<Int> {
        set.add(guid)

        val ips = ipRecordRepository.findAll { root, _, builder ->
            val join = root.join(IpRecord_.server)
            builder.and(
                builder.equal(root.get(IpRecord_.uniqueId), guid),
                builder.equal(join.get(Server_.id), serverId)
            )
        }.stream()
            .map { it.ipAddress }
            .collect(Collectors.toSet())

        val guids = ipRecordRepository.findAll { root, _, builder ->
            val join = root.join(IpRecord_.server)
            val inClause = builder.`in`(root.get(IpRecord_.ipAddress))
            ips.forEach { inClause.value(it) }
            builder.and(
                inClause,
                builder.equal(join.get(Server_.id), serverId)
            )
        }.stream()
            .map { it.uniqueId }
            .collect(Collectors.toSet())

        guids.forEach {
            if (!set.contains(it)) findAltGuids(it!!, serverId, set)
        }

        return set
    }
}