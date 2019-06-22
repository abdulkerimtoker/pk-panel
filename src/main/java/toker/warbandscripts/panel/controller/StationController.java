package toker.warbandscripts.panel.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.entity.*;
import toker.warbandscripts.panel.repository.CraftingRepository;
import toker.warbandscripts.panel.repository.ItemRepository;
import toker.warbandscripts.panel.repository.PlayerRepository;

@Controller
@RequestMapping("/station")
public class StationController {

    private CraftingRepository craftingRepository;
    private ItemRepository itemRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public StationController(CraftingRepository craftingRepository, ItemRepository itemRepository, PlayerRepository playerRepository) {
        this.craftingRepository = craftingRepository;
        this.itemRepository = itemRepository;
        this.playerRepository = playerRepository;
    }

    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute("stations", craftingRepository.getCraftingStations());
        return "station/list";
    }

    @Secured("ROLE_STATION_MANAGER")
    @PostMapping("create")
    public String create(@RequestParam String name) {
        CraftingStation craftingStation = new CraftingStation();
        craftingStation.setName(name);
        craftingRepository.createCraftingStation(craftingStation);
        return "redirect:list";
    }

    @Secured("ROLE_STATION_MANAGER")
    @GetMapping("manage/{stationId}")
    public String manage(Model model, @PathVariable int stationId) {
        model.addAttribute("station", craftingRepository.getCraftingStation(stationId, true));
        model.addAttribute("createRecipe", new CraftingRecipe());
        model.addAttribute("addInstance", new CraftingStationInstance());
        model.addAttribute("itemList", itemRepository.getItems());
        model.addAttribute("professionList", playerRepository.getProfessions());
        return "station/manage";
    }

    @Secured("ROLE_STATION_MANAGER")
    @PostMapping("manage")
    public String manageSubmit(@ModelAttribute("station") CraftingStation craftingStation, BindingResult bindingResult) {
        craftingRepository.updateCraftingStation(craftingStation);
        return "redirect:manage/" + craftingStation.getId();
    }

    @Secured("ROLE_STATION_MANAGER")
    @PostMapping("addinstance")
    public String addInstance(@ModelAttribute("addInstance") CraftingStationInstance stationInstance) {
        craftingRepository.createCraftingStationInstance(stationInstance);
        return "redirect:manage/" + stationInstance.getCraftingStationByStationId().getId();
    }

    @Secured("ROLE_STATION_MANAGER")
    @GetMapping("recipe/manage/{recipeId}")
    public String manageRecipe(Model model, @PathVariable int recipeId) {
        model.addAttribute("recipe", craftingRepository.getCraftingRecipe(recipeId));
        model.addAttribute("itemList", itemRepository.getItems());
        model.addAttribute("professionList", playerRepository.getProfessions());
        model.addAttribute("createItemRequirement", new CraftingRecipeItemRequirement());
        return "station/recipe/manage";
    }

    @Secured("ROLE_STATION_MANAGER")
    @PostMapping("recipe/manage")
    public String manageRecipeSubmit(@ModelAttribute("recipe") CraftingRecipe craftingRecipe) {
        craftingRepository.updateCraftingRecipe(craftingRecipe);
        return "redirect:manage/" + craftingRecipe.getId();
    }

    @Secured("ROLE_STATION_MANAGER")
    @PostMapping("recipe/create")
    public String createRecipe(@ModelAttribute("createRecipe") CraftingRecipe craftingRecipe) {
        CraftingRecipe created = craftingRepository.createCraftingRecipe(craftingRecipe);
        if (created != null) {
            return "redirect:manage/" + created.getId();
        } else {
            return "redirect:/station/manage/" + craftingRecipe.getCraftingStationByStationId().getId();
        }
    }

    @Secured("ROLE_STATION_MANAGER")
    @PostMapping("recipe/remove")
    public String removeRecipe(@RequestParam int recipeId) {
        CraftingRecipe recipe = craftingRepository.getCraftingRecipe(recipeId);
        int stationId = recipe.getCraftingStationByStationId().getId();
        craftingRepository.removeCraftingRecipe(recipeId);
        return "redirect:/station/manage/" + stationId;
    }

    @Secured("ROLE_STATION_MANAGER")
    @PostMapping("recipe/additemrequirement")
    public String addItemRequirement(@ModelAttribute("createItemRequirement") CraftingRecipeItemRequirement itemRequirement) {
        craftingRepository.createCraftingRecipeItemRequirement(itemRequirement);
        return "redirect:manage/" + itemRequirement.getCraftingRecipeByRecipeId().getId();
    }

    @Secured("ROLE_STATION_MANAGER")
    @PostMapping("recipe/removeitemrequirement")
    public String removeItemRequirement(@RequestParam int itemRequirementId, @RequestParam int recipeId) {
        craftingRepository.removeCraftingRecipeItemRequirement(itemRequirementId);
        return "redirect:manage/" + recipeId;
    }
}
