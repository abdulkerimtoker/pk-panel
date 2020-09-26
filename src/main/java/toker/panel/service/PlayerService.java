package toker.panel.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toker.panel.bean.SelectedServerId;
import toker.panel.entity.*;
import toker.panel.repository.*;
import toker.panel.util.Constants;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.PluralAttribute;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private InventoryRepository inventoryRepository;
    private InventorySlotRepository inventorySlotRepository;
    private DoorKeyRepository doorKeyRepository;
    private NoticeBoadAccessRepository noticeBoadAccessRepository;
    private ProfessionAssignmentRepository professionAssignmentRepository;
    private CraftingRequestRepository craftingRequestRepository;
    private ItemRepository itemRepository;
    private AdminPermissionsRepository adminPermissionsRepository;
    private StartingItemRepository startingItemRepo;
    private LanguageProficiencyRepository proficiencyRepo;
    private LanguageRepository languageRepo;
    private ServerService serverService;

    public PlayerService(PlayerRepository playerRepository,
                         InventoryRepository inventoryRepository,
                         InventorySlotRepository inventorySlotRepository,
                         DoorKeyRepository doorKeyRepository,
                         NoticeBoadAccessRepository noticeBoadAccessRepository,
                         ProfessionAssignmentRepository professionAssignmentRepository,
                         CraftingRequestRepository craftingRequestRepository,
                         ItemRepository itemRepository,
                         AdminPermissionsRepository adminPermissionsRepository,
                         StartingItemRepository startingItemRepo,
                         LanguageProficiencyRepository proficiencyRepo,
                         LanguageRepository languageRepo,
                         ServerService serverService) {
        this.playerRepository = playerRepository;
        this.inventoryRepository = inventoryRepository;
        this.inventorySlotRepository = inventorySlotRepository;
        this.doorKeyRepository = doorKeyRepository;
        this.noticeBoadAccessRepository = noticeBoadAccessRepository;
        this.professionAssignmentRepository = professionAssignmentRepository;
        this.craftingRequestRepository = craftingRequestRepository;
        this.itemRepository = itemRepository;
        this.adminPermissionsRepository = adminPermissionsRepository;
        this.startingItemRepo = startingItemRepo;
        this.proficiencyRepo = proficiencyRepo;
        this.languageRepo = languageRepo;
        this.serverService = serverService;
    }

    public Optional<Player> getPlayer(int id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> getPlayer(String name, int serverId) {
        return playerRepository.findOne((root, query, builder) -> {
            Join<Player, Faction> join = root.join(Player_.faction);
            return builder.and(
                    builder.equal(root.get(Player_.name), name),
                    builder.equal(join.get(Faction_.server).get(Server_.id), serverId)
            );
        });
    }

    public Optional<Player> getPlayer(String name, int serverId, PluralAttribute<Player, ? extends Collection<?>, ?> with) {
        return playerRepository.findOne((root, query, builder) -> {
            root.fetch(with, JoinType.LEFT);
            Join<Player, Faction> join = root.join(Player_.faction);
            return builder.and(
                    builder.equal(root.get(Player_.name), name),
                    builder.equal(join.get(Faction_.server).get(Server_.id), serverId)
            );
        });
    }

    public Optional<Player> getPlayer(String name, int serverId, int uniqueId) {
        return playerRepository.findOne((root, query, builder) -> {
            Join<Player, Faction> join = root.join(Player_.faction);
            return builder.and(
                    builder.equal(root.get(Player_.name), name),
                    builder.equal(root.get(Player_.uniqueId), uniqueId),
                    builder.equal(join.get(Faction_.server).get(Server_.id), serverId)
            );
        });
    }

    public Optional<Player> getPlayer(String name, int serverId, int uniqueId,
                                      PluralAttribute<Player, ? extends Collection<?>, ?> with) {
        return playerRepository.findOne((root, query, builder) -> {
            root.fetch(with, JoinType.LEFT);
            Join<Player, Faction> join = root.join(Player_.faction);
            return builder.and(
                    builder.equal(root.get(Player_.name), name),
                    builder.equal(root.get(Player_.uniqueId), uniqueId),
                    builder.equal(join.get(Faction_.server).get(Server_.id), serverId)
            );
        });
    }

    public Player savePlayer(Player player) {
        return playerRepository.saveAndFlush(player);
    }

    public List<Player> searchPlayers(String searchTerm) {
        return playerRepository.likeSearch(searchTerm, SelectedServerId.get());
    }

    public boolean setPlayerField(int id, String field, Object value) {
        Player player = getPlayer(id).orElse(null);
        if (player != null) {
            String setterName = String.format("set%s", Character.toUpperCase(field.charAt(0)) + field.substring(1).toLowerCase());
            try {
                Player.class.getDeclaredMethod(setterName, value.getClass())
                    .invoke(player, value);
            } catch (NoSuchMethodException e) {
                return false;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return false;
            }
            playerRepository.save(player);
            return true;
        }
        return false;
    }

    public Inventory getPlayerInventory(int playerId) {
        return inventoryRepository.findOne(((Specification<Inventory>) (root, query, builder) -> {
            root.fetch("slots", JoinType.LEFT);
            return builder.equal(root.get("player").get("id"), playerId);
        })).orElse(null);
    }

    public Inventory getInventory(int inventoryId) throws ChangeSetPersister.NotFoundException {
        return inventoryRepository.findOne(((root, query, builder) -> {
            root.fetch("slots", JoinType.LEFT);
            return builder.equal(root.get("id"), inventoryId);
        })).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public InventorySlot updateInventorySlot(int inventoryId, InventorySlot inventorySlot) {
        inventorySlot.setInventory(inventoryRepository.getOne(inventoryId));
        return inventorySlotRepository.saveAndFlush(inventorySlot);
    }

    public void updateInventorySlot(int inventoryId, int slot, int itemId, int ammo) {
        inventorySlotRepository.updateSlot(inventoryRepository.getOne(inventoryId),
                slot, itemRepository.getOne(itemId), ammo);
    }

    public List<DoorKey> getPlayerDoorKeys(int playerId) {
        return doorKeyRepository.findAllByPlayerId(playerId);
    }

    public List<NoticeBoardAccess> getPlayerBoardAccesses(int playerId) {
        return noticeBoadAccessRepository.findAllByPlayerId(playerId);
    }

    public List<ProfessionAssignment> getPlayerProfessions(int playerId) {
        return professionAssignmentRepository.findAllByPlayerId(playerId);
    }

    public List<CraftingRequest> getPlayerCraftingRequests(int playerId) {
        return craftingRequestRepository.findAllByPlayerId(playerId);
    }

    public void woundPlayer(Player player) {
        int woundDuration = 48;
        ServerConfiguration conf = serverService.getServerConfiguration(
                player.getFaction().getServer().getId(), "CONF_WOUND_TIME").orElse(null);
        if (conf != null) {
            woundDuration = Integer.parseInt(conf.getValue());
        }
        player.setWoundDuration(woundDuration);
        player.setServedWoundTime(0);
        player.setWoundTime(Timestamp.from(Instant.now()));
        savePlayer(player);
    }

    public boolean treatPlayer(Player player, Player patient) {
        if (patient.getTreatmentTime() != null &&
                patient.getTreatmentTime().after(patient.getWoundTime())) {
            return false;
        }

        int healingConstant = 12;
        int tierMultiplier = 0;
        AtomicBoolean treated = new AtomicBoolean(false);

        ServerConfiguration healingConf = serverService.getServerConfiguration(
                player.getFaction().getServer().getId(), "CONF_TREATMENT_CONSTANT").orElse(null);
        if (healingConf != null) {
            healingConstant = Integer.parseInt(healingConf.getValue());
        }

        ServerConfiguration multiplierConf = serverService.getServerConfiguration(
                player.getFaction().getServer().getId(), "CONF_TREATMENT_TIER_MULTIPLIER").orElse(null);
        if (multiplierConf != null) {
            tierMultiplier = Integer.parseInt(multiplierConf.getValue());
        }

        Collection<ProfessionAssignment> professionAssignments = getPlayerProfessions(player.getId());
        int finalHealingConstant = healingConstant;
        int finalTierMultiplier = tierMultiplier;

        professionAssignments.forEach(professionAssignment -> {
            if (professionAssignment.getProfession().getName().equals("Surgeon")) {
                getPlayerInventory(player.getId()).getSlots().forEach(inventorySlot -> {
                    if (!treated.get() && inventorySlot.getItem().getId() == 617) {
                        inventorySlot.setItem(itemRepository.getOne(0));
                        inventorySlotRepository.save(inventorySlot);
                        int newWoundDuration = patient.getWoundDuration() -
                                (finalHealingConstant + finalTierMultiplier * professionAssignment.getTier());
                        patient.setTreatmentTime(Timestamp.from(Instant.now()));
                        patient.setWoundDuration(newWoundDuration);
                        patient.setServedWoundTime(0);
                        savePlayer(patient);
                        treated.set(true);
                    }
                });
            }
        });

        return treated.get();
    }

    public Inventory createInventory(Player player) {
        Inventory inventory = new Inventory();
        inventory.setPlayer(player);
        inventory.setSize(20);
        LinkedHashSet<InventorySlot> slots = new LinkedHashSet<>();
        for (int i = 1; i <= 20; i++) {
            InventorySlot slot = new InventorySlot();
            slot.setInventory(inventory);
            slot.setItem(itemRepository.getOne(0));
            slot.setAmmo(0);
            slot.setSlot(i);
            slots.add(slot);
        }
        inventory.setSlots(slots);
        return inventoryRepository.saveAndFlush(inventory);
    }

    public Optional<AdminPermissions> getAdminPermissions(int serverId, int uniqueId) {
        return adminPermissionsRepository.findByServerIdAndUniqueId(serverId, uniqueId);
    }

    public Player spawnStartingGear(Player player, int serverId) {
        List<StartingItem> startingItems = startingItemRepo.findAll(((root, query, builder) ->
                builder.equal(root.get(StartingItem_.server).get(Server_.id), serverId)));

        List<Item> headArmors = startingItems.stream()
                .map(StartingItem::getItem)
                .filter(item -> item.getType().getId() == Constants.HEAD_ARMOR)
                .collect(Collectors.toList());
        List<Item> bodyArmors = startingItems.stream()
                .map(StartingItem::getItem)
                .filter(item -> item.getType().getId() == Constants.BODY_ARMOR)
                .collect(Collectors.toList());
        List<Item> footArmors = startingItems.stream()
                .map(StartingItem::getItem)
                .filter(item -> item.getType().getId() == Constants.FOOT_ARMOR)
                .collect(Collectors.toList());
        List<Item> handArmors = startingItems.stream()
                .map(StartingItem::getItem)
                .filter(item -> item.getType().getId() == Constants.HAND_ARMOR)
                .collect(Collectors.toList());

        Random random = new Random();

        if (!headArmors.isEmpty())
            player.setHeadArmor(headArmors.get(random.nextInt(headArmors.size() - 1)));
        if (!bodyArmors.isEmpty())
            player.setBodyArmor(bodyArmors.get(random.nextInt(bodyArmors.size() - 1)));
        if (!footArmors.isEmpty())
            player.setFootArmor(footArmors.get(random.nextInt(footArmors.size() - 1)));
        if (!handArmors.isEmpty())
            player.setHandArmor(handArmors.get(random.nextInt(handArmors.size() - 1)));

        return player;
    }

    public List<LanguageProficiency> getLanguageProficiencies(int playerId) {
        return proficiencyRepo.findAll((root, query, builder) ->
                builder.equal(root.get(LanguageProficiency_.player).get(Player_.id), playerId));
    }

    public LanguageProficiency assignLanguageProficiency(int playerId, int languageId) {
        LanguageProficiency proficiency = new LanguageProficiency();
        proficiency.setPlayer(playerRepository.getOne(playerId));
        proficiency.setLanguage(languageRepo.getOne(languageId));
        return proficiencyRepo.saveAndFlush(proficiency);
    }

    public void revokeLanguageProficiency(int playerId, int languageId) {
        proficiencyRepo.deleteByPlayerIdAndLanguageId(playerId, languageId);
    }
}
