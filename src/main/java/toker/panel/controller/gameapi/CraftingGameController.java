package toker.panel.controller.gameapi;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.panel.entity.*;
import toker.panel.repository.*;
import toker.panel.service.PlayerService;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@Transactional
public class CraftingGameController {

    private PlayerService playerService;

    private CraftingStationInstanceRepository instanceRepo;
    private CraftingRecipeRepository recipeRepo;
    private CraftingRequestRepository requestRepo;
    private ItemRepository itemRepo;
    private InventorySlotRepository inventorySlotRepo;

    public CraftingGameController(PlayerService playerService,
                                  CraftingStationInstanceRepository instanceRepo,
                                  CraftingRecipeRepository recipeRepo,
                                  CraftingRequestRepository requestRepo,
                                  ItemRepository itemRepo,
                                  InventorySlotRepository inventorySlotRepo) {
        this.playerService = playerService;
        this.instanceRepo = instanceRepo;
        this.recipeRepo = recipeRepo;
        this.requestRepo = requestRepo;
        this.itemRepo = itemRepo;
        this.inventorySlotRepo = inventorySlotRepo;
    }

    @GetMapping("/gameapi/loadcraftingstationrecipes")
    public String loadCraftingStationRecipes(int id, int instanceid) throws ChangeSetPersister.NotFoundException {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        CraftingStationInstance instance = instanceRepo.findOne((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(CraftingStationInstance_.id), id))
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<CraftingRecipe> recipes = recipeRepo.findAll((root, query, builder) -> {
            Join<CraftingRecipe, CraftingStation> join = root.join(CraftingRecipe_.craftingStation);
            query.orderBy(
                    builder.asc(root.get(CraftingRecipe_.profession).get(Profession_.id)),
                    builder.asc(root.get(CraftingRecipe_.professionTier))
            );
            query.distinct(true);
            return builder.and(
                    builder.equal(join.get(CraftingStation_.index), instance.getCraftingStation().getIndex()),
                    builder.equal(join.get(CraftingStation_.server).get(Server_.id), server.getId())
            );
        });

        StringBuilder sb = new StringBuilder(String.format("%d|%d|%d", id, instanceid, recipes.size()));

        recipes.forEach(recipe -> { sb.append('|'); sb.append(recipe.getId()); });

        return sb.toString();
    }

    @GetMapping("/gameapi/loadcraftingstationitems")
    public String loadCraftingStationItems(int id, int instanceid) throws ChangeSetPersister.NotFoundException {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        CraftingStationInstance instance = instanceRepo.findOne((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(CraftingStationInstance_.id), id))
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<CraftingRecipe> recipes = recipeRepo.findAll((root, query, builder) -> {
            Join<CraftingRecipe, CraftingStation> join = root.join(CraftingRecipe_.craftingStation);
            query.orderBy(
                    builder.asc(root.get(CraftingRecipe_.profession).get(Profession_.id)),
                    builder.asc(root.get(CraftingRecipe_.professionTier))
            );
            query.distinct(true);
            return builder.and(
                    builder.equal(join.get(CraftingStation_.index), instance.getCraftingStation().getIndex()),
                    builder.equal(join.get(CraftingStation_.server).get(Server_.id), server.getId())
            );
        });

        StringBuilder sb = new StringBuilder(String.format("%d|%d|%d", id, instanceid, recipes.size()));

        recipes.forEach(recipe -> { sb.append('|'); sb.append(recipe.getItem().getId()); });

        return sb.toString();
    }

    @GetMapping("/gameapi/craftitem")
    public String craftItem(int playerid, String playername,
                            int recipeid, int stationinstanceid)
            throws ChangeSetPersister.NotFoundException {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Player player = playerService.getPlayer(playername, server.getId(), Player_.professionAssignments)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        CraftingRecipe recipe = recipeRepo.findOne((root, query, builder) -> {
            root.fetch(CraftingRecipe_.itemRequirements, JoinType.LEFT);
            return builder.equal(root.get(CraftingRecipe_.id), recipeid);
        }).orElseThrow(ChangeSetPersister.NotFoundException::new);

        boolean isAble = player.getProfessionAssignments().stream().anyMatch(pa ->
                pa.getProfession().equals(recipe.getProfession()) &&
                pa.getTier() >= recipe.getProfessionTier());

        if (!isAble)
            return String.format("%d|%d|%d", playerid, 2, recipe.getPrice());

        List<CraftingRequest> craftingRequests = requestRepo.findAll((root, query, builder) -> builder.and(
                builder.equal(root.get(CraftingRequest_.player).get(Player_.id), player.getId()),
                builder.equal(root.get(CraftingRequest_.isTaken), false)
        ));

        if (!craftingRequests.isEmpty())
            return String.format("%d|%d|%d", playerid, 3, recipe.getPrice());

        Inventory inventory = playerService.getPlayerInventory(player.getId());

        AtomicBoolean fail = new AtomicBoolean(false);

        recipe.getItemRequirements().forEach(requirement -> {
            long amount = inventory.getSlots().stream()
                    .filter(slot -> slot.getItem().equals(requirement.getItem())).count();
            if (amount < requirement.getAmount())
                fail.set(true);
        });

        if (fail.get())
            return String.format("%d|%d|%d", playerid, 1, recipe.getPrice());

        recipe.getItemRequirements().forEach(requirement -> {
            int amountToTake = requirement.getAmount();
            for (InventorySlot slot : inventory.getSlots()) {
                if (amountToTake == 0)
                    break;
                if (slot.getItem().equals(requirement.getItem())) {
                    slot.setItem(itemRepo.getOne(0));
                    inventorySlotRepo.save(slot);
                    amountToTake--;
                }
            }
        });

        CraftingRequest craftingRequest = new CraftingRequest();
        craftingRequest.setPlayer(player);
        craftingRequest.setCraftingRecipe(recipe);
        craftingRequest.setCraftingStationInstance(instanceRepo.getOne(stationinstanceid));
        craftingRequest.setTaken(false);
        craftingRequest.setCreationTime(Timestamp.from(Instant.now()));

        requestRepo.save(craftingRequest);

        return String.format("%d|0", playerid);
    }

    @GetMapping("/gameapi/craftitemtakeprice")
    public String takePrice(int playerid, int recipeid, int stationinstanceid)
            throws ChangeSetPersister.NotFoundException {
        CraftingRecipe recipe = recipeRepo.findOne((root, query, builder) ->
                builder.equal(root.get(CraftingRecipe_.id), recipeid))
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        return String.format("%d|%d|%d|%d", playerid, recipe.getPrice(), recipeid, stationinstanceid);
    }

    @GetMapping("/gameapi/getcrafteditem")
    public String getCraftedItem(int playerid, String playername, int stationinstanceid)
            throws ChangeSetPersister.NotFoundException {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Player player = playerService.getPlayer(playername, server.getId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<CraftingRequest> craftingRequests = requestRepo.findAll((root, query, builder) -> builder.and(
                builder.equal(root.get(CraftingRequest_.player).get(Player_.id), player.getId()),
                builder.equal(root.get(CraftingRequest_.isTaken), false)
        ));

        if (craftingRequests.isEmpty())
            return "-1";

        CraftingRequest craftingRequest = craftingRequests.get(0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(craftingRequest.getCreationTime());
        calendar.add(Calendar.HOUR, craftingRequest.getCraftingRecipe().getHours());

        if (calendar.toInstant().isBefore(Instant.now())) {
            Inventory inventory = playerService.getPlayerInventory(player.getId());
            boolean hasSpace = inventory.getSlots().stream().anyMatch(slot -> slot.getItem().getId() < 5);

            if (hasSpace) {
                for (InventorySlot slot : inventory.getSlots()) {
                    if (slot.getItem().getId() < 5) {
                        slot.setItem(craftingRequest.getCraftingRecipe().getItem());
                        slot.setAmmo(switch (craftingRequest.getCraftingRecipe().getItem().getId()) {
                            case 483 -> 30;
                            case 484 -> 27;
                            case 485, 486 -> 24;
                            case 492 -> 29;
                            case 493 -> 26;
                            default -> 0;
                        });
                        inventorySlotRepo.save(slot);
                        craftingRequest.setTaken(true);
                        requestRepo.save(craftingRequest);
                        break;
                    }
                }
                return String.format("%d|Your crafted item has been spawned in your inventory!",
                        playerid);
            }
            else {
                return String.format("%d|There is no space in your inventory. " +
                        "Empty a slot and open this crafting station again to receive your crafted item.",
                        playerid);
            }
        }
        else {
            return String.format("%d|The crafting of %s will be finished at %s",
                    playerid,
                    craftingRequest.getCraftingRecipe().getItem().getName(),
                    Date.from(calendar.toInstant()).toString());
        }
    }

    @GetMapping("/gameapi/getrecipeinfo")
    public String getRecipeInfo(int playerid, int recipeid) throws ChangeSetPersister.NotFoundException {
        CraftingRecipe recipe = recipeRepo.findOne((root, query, builder) -> {
            root.fetch(CraftingRecipe_.itemRequirements, JoinType.LEFT);
            return builder.equal(root.get(CraftingRecipe_.id), recipeid);
        }).orElseThrow(ChangeSetPersister.NotFoundException::new);

        StringBuilder sb = new StringBuilder();

        sb.append(playerid);
        sb.append(String.format("|Profession Requirement: %s - Tier %d",
                recipe.getProfession().getName(), recipe.getProfessionTier()));
        sb.append(String.format("|Crafting Price: %d", recipe.getPrice()));
        sb.append(String.format("|Crafting Time: %d hours", recipe.getHours()));
        sb.append("|Required Materials:");

        recipe.getItemRequirements().forEach(requirement -> {
            sb.append(String.format(" [%d x %s]", requirement.getAmount(), requirement.getItem().getName()));
        });

        sb.append(recipe.getItemRequirements().size() > 0 ? "|4" : "None|4");

        return sb.toString();
    }
}
