package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import toker.panel.entity.*
import toker.panel.entity.NoticeBoardAccess.View.Board
import toker.panel.service.PlayerService
import javax.persistence.OptimisticLockException

@RestController
class PlayerController(private val playerService: PlayerService) {
    internal interface PlayerView : Player.View.Faction, Player.View.Troop, Player.View.Items

    @GetMapping("/api/player/{playerId}")
    @JsonView(PlayerView::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun player(@PathVariable playerId: Int): Player {
        return playerService.getPlayer(playerId).orElseThrow { ChangeSetPersister.NotFoundException() }
    }

    @PutMapping("/api/player")
    @PreAuthorize("@authService.canModifyPlayer(#player.id)")
    @JsonView(PlayerView::class)
    fun updatePlayer(@RequestBody player: Player): Player {
        return playerService.savePlayer(player)
    }

    internal interface PlayerSearchView : Player.View.Faction, Player.View.Troop

    @GetMapping("/api/player/search")
    @JsonView(PlayerSearchView::class)
    fun search(@RequestParam search: String): List<Player> {
        return playerService.searchPlayers(search)
    }

    @ExceptionHandler(OptimisticLockException::class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Wrong version")
    fun versionConflict() { }

    @GetMapping("/api/player/{playerId}/inventory")
    fun inventory(@PathVariable playerId: Int): Inventory {
        return playerService.getPlayerInventory(playerId)
    }

    @PutMapping("/api/inventory/{inventoryId}")
    @PreAuthorize("@authService.canModifyPlayer(@playerService.getInventory(#inventoryId).player.id)")
    fun inventorySlot(@PathVariable inventoryId: Int,
                      @RequestBody inventorySlot: InventorySlot): InventorySlot {
        return playerService.updateInventorySlot(inventoryId, inventorySlot)
    }

    @GetMapping("/api/player/{playerId}/doorKeys")
    @JsonView(DoorKey.View.Door::class)
    fun doorKeys(@PathVariable playerId: Int): List<DoorKey> {
        return playerService.getPlayerDoorKeys(playerId)
    }

    @GetMapping("/api/player/{playerId}/boardAccesses")
    @JsonView(Board::class)
    fun boardAccesses(@PathVariable playerId: Int): List<NoticeBoardAccess> {
        return playerService.getPlayerBoardAccesses(playerId)
    }

    @GetMapping("/api/player/{playerId}/professionAssignments")
    @JsonView(ProfessionAssignment.View.Profession::class)
    fun professions(@PathVariable playerId: Int): List<ProfessionAssignment> {
        return playerService.getPlayerProfessions(playerId)
    }

    interface PlayerCraftingRequestView : CraftingRequest.View.CraftingRecipe,
            CraftingRequest.View.CraftingStationInstance, CraftingRecipe.View.Item

    @GetMapping("/api/player/{playerId}/craftingRequests")
    @JsonView(PlayerCraftingRequestView::class)
    fun craftingRequests(@PathVariable playerId: Int): List<CraftingRequest> {
        return playerService.getPlayerCraftingRequests(playerId)
    }

    @GetMapping("/api/player/{playerId}/languageProficiencies")
    @JsonView(LanguageProficiency.View.Language::class)
    fun languageProficiencies(@PathVariable playerId: Int): List<LanguageProficiency> {
        return playerService.getLanguageProficiencies(playerId)
    }

    @PostMapping("/api/player/{playerId}/languageProficiencies/{languageId}")
    @JsonView(LanguageProficiency.View.Language::class)
    fun assignLanguageProficiency(@PathVariable playerId: Int,
                                  @PathVariable languageId: Int): List<LanguageProficiency> {
        playerService.assignLanguageProficiency(playerId, languageId)
        return languageProficiencies(playerId)
    }

    @DeleteMapping("/api/player/{playerId}/languageProficiencies/{languageId}")
    @JsonView(LanguageProficiency.View.Language::class)
    fun revokeLanguageProficiency(@PathVariable playerId: Int,
                                  @PathVariable languageId: Int): List<LanguageProficiency> {
        playerService.revokeLanguageProficiency(playerId, languageId)
        return languageProficiencies(playerId)
    }
}