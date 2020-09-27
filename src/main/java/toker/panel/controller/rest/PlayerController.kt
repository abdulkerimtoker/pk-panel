package toker.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import toker.panel.entity.*;
import toker.panel.service.PlayerService;

import javax.persistence.OptimisticLockException;
import java.util.List;

@RestController
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    interface PlayerView extends Player.View.Faction,
            Player.View.Troop, Player.View.Items {}

    @GetMapping("/api/player/{playerId}")
    @JsonView(PlayerView.class)
    public Player player(@PathVariable int playerId) throws ChangeSetPersister.NotFoundException {
        return playerService.getPlayer(playerId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @PutMapping("/api/player")
    @PreAuthorize("@authService.canModifyPlayer(#player.id)")
    @JsonView(PlayerView.class)
    public Player updatePlayer(@RequestBody Player player) {
        return playerService.savePlayer(player);
    }

    interface PlayerSearchView extends Player.View.Faction, Player.View.Troop {}

    @GetMapping("/api/player/search")
    @JsonView(PlayerSearchView.class)
    public List<Player> search(@RequestParam String search) {
        return playerService.searchPlayers(search);
    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Wrong version")
    public void versionConflict() {}

    @GetMapping("/api/player/{playerId}/inventory")
    public Inventory inventory(@PathVariable int playerId) {
        return playerService.getPlayerInventory(playerId);
    }

    @PutMapping("/api/inventory/{inventoryId}")
    @PreAuthorize("@authService.canModifyPlayer(@playerService.getInventory(#inventoryId).player.id)")
    public InventorySlot inventorySlot(@PathVariable int inventoryId,
                                       @RequestBody InventorySlot inventorySlot) {
        return playerService.updateInventorySlot(inventoryId, inventorySlot);
    }

    @GetMapping("/api/player/{playerId}/doorKeys")
    @JsonView(DoorKey.View.Door.class)
    public List<DoorKey> doorKeys(@PathVariable int playerId) {
        return playerService.getPlayerDoorKeys(playerId);
    }

    @GetMapping("/api/player/{playerId}/boardAccesses")
    @JsonView(NoticeBoardAccess.View.Board.class)
    public List<NoticeBoardAccess> boardAccesses(@PathVariable int playerId) {
        return playerService.getPlayerBoardAccesses(playerId);
    }

    @GetMapping("/api/player/{playerId}/professionAssignments")
    @JsonView(ProfessionAssignment.View.Profession.class)
    public List<ProfessionAssignment> professions(@PathVariable int playerId) {
        return playerService.getPlayerProfessions(playerId);
    }

    public interface PlayerCraftingRequestView extends
            CraftingRequest.View.CraftingRecipe,
            CraftingRequest.View.CraftingStationInstance,
            CraftingRecipe.View.Item {}

    @GetMapping("/api/player/{playerId}/craftingRequests")
    @JsonView(PlayerCraftingRequestView.class)
    public List<CraftingRequest> craftingRequests(@PathVariable int playerId) {
        return playerService.getPlayerCraftingRequests(playerId);
    }

    @GetMapping("/api/player/{playerId}/languageProficiencies")
    @JsonView(LanguageProficiency.View.Language.class)
    public List<LanguageProficiency> languageProficiencies(@PathVariable int playerId) {
        return playerService.getLanguageProficiencies(playerId);
    }

    @PostMapping("/api/player/{playerId}/languageProficiencies/{languageId}")
    @JsonView(LanguageProficiency.View.Language.class)
    public List<LanguageProficiency> assignLanguageProficiency(@PathVariable int playerId, @PathVariable int languageId) {
        playerService.assignLanguageProficiency(playerId, languageId);
        return languageProficiencies(playerId);
    }

    @DeleteMapping("/api/player/{playerId}/languageProficiencies/{languageId}")
    @JsonView(LanguageProficiency.View.Language.class)
    public List<LanguageProficiency> revokeLanguageProficiency(@PathVariable int playerId, @PathVariable int languageId) {
        playerService.revokeLanguageProficiency(playerId, languageId);
        return languageProficiencies(playerId);
    }
}
