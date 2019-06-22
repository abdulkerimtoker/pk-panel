package toker.warbandscripts.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.authentication.PanelUserDetails;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.entity.Inventory;
import toker.warbandscripts.panel.entity.NoticeBoardAccess;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.model.*;
import toker.warbandscripts.panel.repository.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/player")
public class PlayerController {

    private PlayerRepository playerRepository;
    private ItemRepository itemRepository;
    private DoorRepository doorRepository;
    private BoardRepository boardRepository;
    private BanRepository banRepository;

    @Autowired
    public PlayerController(PlayerRepository playerRepository,
                            ItemRepository itemRepository,
                            DoorRepository doorRepository,
                            BoardRepository boardRepository,
                            BanRepository banRepository) {
        this.playerRepository = playerRepository;
        this.itemRepository = itemRepository;
        this.doorRepository = doorRepository;
        this.boardRepository = boardRepository;
        this.banRepository = banRepository;
    }

    @GetMapping("lookup")
    public String lookup(Model model) {
        PlayerLookupModel lookupModel = new PlayerLookupModel();
        lookupModel.setItemList(itemRepository.getItems());
        model.addAttribute("model", lookupModel);
        return "player/lookup";
    }

    @PostMapping("lookup")
    public String processLookup(Model model,
                                @ModelAttribute("model") @Valid PlayerLookupModel form,
                                BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            List<Player> players = playerRepository.getPlayersBySimilarNameOrId(form.getSearch());
            form.setSearchResult(players);
        }
        form.setItemList(itemRepository.getItems());
        model.addAttribute("model", form);
        return "player/lookup";
    }

    @PostMapping("associate")
    public String associateGUIDs(Model model, @ModelAttribute("model") PlayerLookupModel form) {
        form.setAssociationResult(playerRepository.getIPAssociatedGUIDs(form.getAssociateGUID()));
        form.setItemList(itemRepository.getItems());
        model.addAttribute("model", form);
        return "player/lookup";
    }

    @PostMapping("finditemowners")
    public String findItemOwners(Model model, @ModelAttribute("model") PlayerLookupModel form) {
        form.setSearchResult(playerRepository.findPlayersWithItem(form.getFindItemId()));
        form.setItemList(itemRepository.getItems());
        model.addAttribute("model", form);
        return "player/lookup";
    }

    @Secured("ROLE_PLAYER_MANAGER")
    @PostMapping("manage")
    public String processManage(@ModelAttribute PlayerManageModel form) {
        playerRepository.updatePlayer(form.getPlayer());
        return "redirect:layout/" + form.getPlayer().getId();
    }

    @Secured("ROLE_PLAYER_MANAGER")
    @PostMapping("inventory")
    public String processInventory(@ModelAttribute PlayerInventoryModel form) {
        playerRepository.updatePlayerInventory(form.getPlayerId(), form.getInventorySlots());
        return "redirect:layout/" + form.getPlayerId();
    }

    @Secured("ROLE_DOOR_MANAGER")
    @PostMapping("removedoorkey")
    public String removeDoorKey(@RequestParam int doorKeyId) {
        DoorKey doorKey = doorRepository.getDoorKeyById(doorKeyId);
        int playerId = doorKey.getPlayerByUserId().getId();
        doorRepository.removeDoorKey(doorKeyId);
        return "redirect:layout/" + playerId;
    }

    @Secured("ROLE_DOOR_MANAGER")
    @PostMapping("adddoorkey")
    public String addDoorKey(@RequestParam int playerId,
                             @RequestParam int doorId,
                             @RequestParam(required = false, defaultValue = "false") boolean isOwner) {
        doorRepository.addDoorKey(playerId, doorId, isOwner);
        return "redirect:layout/" + playerId;
    }

    @Secured("ROLE_BOARD_MANAGER")
    @PostMapping("removeboardaccess")
    public String removeBoardAccess(@RequestParam int boardAccessId) {
        NoticeBoardAccess boardAccess = boardRepository.getBoardAccessById(boardAccessId);
        int playerId = boardAccess.getPlayerByUserId().getId();
        boardRepository.removeBoardAccess(boardAccessId);
        return "redirect:layout/" + playerId;
    }

    @Secured("ROLE_BOARD_MANAGER")
    @PostMapping("addboardaccess")
    public String addBoardAccess(@RequestParam int playerId,
                                @RequestParam int boardId,
                                @RequestParam(required = false, defaultValue = "false") boolean isOwner) {
        boardRepository.addBoardAccess(playerId, boardId, isOwner);
        return "redirect:layout/" + playerId;
    }

    @Secured("ROLE_BAN_CREATOR")
    @PostMapping("createban")
    public String createBan(@RequestParam int playerId,
                            @RequestParam String expiryDate,
                            @RequestParam String expiryTime,
                            @RequestParam String reason) {

        Player player = playerRepository.getPlayerById(playerId);
        PanelUserDetails admin = (PanelUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Date expiry = new SimpleDateFormat("yy-MM-dd HH:mm").parse(String.format("%s %s", expiryDate, expiryTime));
            banRepository.createBan(player, admin.getPanelUser(), expiry, reason);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "redirect:layout/" + playerId;
    }

    @Secured("ROLE_BAN_REMOVER")
    @PostMapping("unban")
    public String unban(@RequestParam int banId, @RequestParam int playerId) {
        banRepository.unban(banId);
        return "redirect:layout/" + playerId;
    }

    @Secured({"ROLE_BAN_REMOVER", "ROLE_SELF_BAN_REMOVER"})
    @PostMapping("unbanself")
    public String unbanSelf(@RequestParam int banId, @RequestParam int playerId) {
        banRepository.unban(banId);
        return "redirect:layout/" + playerId;
    }

    @Secured("ROLE_BAN_REMOVER")
    @PostMapping("deleteban")
    public String deleteBan(@RequestParam int banId, @RequestParam int playerId) {
        banRepository.deleteBan(banId);
        return "redirect:layout/" + playerId;
    }

    @Secured({"ROLE_BAN_REMOVER", "ROLE_SELF_BAN_REMOVER"})
    @PostMapping("deleteselfban")
    public String deleteSelfBan(@RequestParam int banId, @RequestParam int playerId) {
        banRepository.deleteSelfBan(banId);
        return "redirect:layout/" + playerId;
    }

    @RequestMapping("layout/{playerId}")
    public String layout(Model model, @PathVariable int playerId) {
        Player player = playerRepository.getPlayerById(playerId);
        Collection<Inventory> inventories = player.getInventoriesById();
        Inventory inventory = null;

        if (!inventories.isEmpty()) {
             inventory = inventories.iterator().next();
        }

        player.getProfessionAssignmentsById().size();
        player.getCraftingRequests().size();

        PlayerManageModel manageModel = new PlayerManageModel();
        manageModel.setPlayer(player);
        manageModel.setHeadArmors(itemRepository.getItemsByType(1));
        manageModel.setBodyArmors(itemRepository.getItemsByType(2));
        manageModel.setFootArmors(itemRepository.getItemsByType(3));
        manageModel.setHandArmors(itemRepository.getItemsByType(4));
        manageModel.setHandItems(itemRepository.getItemsByType(5));
        manageModel.setHorses(itemRepository.getItemsByType(6));
        manageModel.setTroops(playerRepository.getPlayerTroops());
        manageModel.setFactions(playerRepository.getPlayerFactions());
        model.addAttribute("manageModel", manageModel);

        if (inventory != null) {
            PlayerInventoryModel inventoryModel = new PlayerInventoryModel();
            inventoryModel.setPlayerId(playerId);
            inventoryModel.setPlayerName(player.getName());
            inventoryModel.setInventorySlots(new ArrayList<>(inventory.getInventorySlotsById()));
            inventoryModel.setItems(new ArrayList<>(itemRepository.getItems()));
            model.addAttribute("inventoryModel", inventoryModel);
        }

        PlayerDoorKeysModel doorKeysModel = new PlayerDoorKeysModel();
        doorKeysModel.setDoorKeys(new ArrayList<>(player.getDoorKeysById()));
        doorKeysModel.setPlayer(player);
        model.addAttribute("doorKeysModel", doorKeysModel);
        model.addAttribute("doorList", doorRepository.getDoors());

        PlayerBoardAccessesModel boardAccessesModel = new PlayerBoardAccessesModel();
        boardAccessesModel.setBoardAccesses(new ArrayList<>(player.getNoticeBoardAccessesById()));
        boardAccessesModel.setPlayer(player);
        model.addAttribute("boardAccessesModel", boardAccessesModel);
        model.addAttribute("boardList", boardRepository.getBoards());

        PlayerBansModel bansModel = new PlayerBansModel();
        bansModel.setPlayer(player);
        bansModel.setActiveBans(banRepository.getActiveBansByUniqueId(player.getUniqueId()));
        bansModel.setPastBans(banRepository.getPastBansByUniqueId(player.getUniqueId()));
        model.addAttribute("bansModel", bansModel);

        model.addAttribute("professionList", playerRepository.getProfessions());

        return "player/layout";
    }

    @Secured("ROLE_PROFESSION_MANAGER")
    @PostMapping("assignprofession")
    public String assignProfession(@RequestParam int playerId, @RequestParam int professionId, @RequestParam int tier) {
        playerRepository.assignProfession(playerId, professionId, tier);
        return "redirect:layout/" + playerId;
    }

    @Secured("ROLE_PROFESSION_MANAGER")
    @PostMapping("revokeprofession")
    public String revokeProfession(@RequestParam int playerId, @RequestParam int professionId) {
        playerRepository.revokeProfession(playerId, professionId);
        return "redirect:layout/" + playerId;
    }
}
