package toker.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import toker.panel.bean.SelectedServerId;
import toker.panel.entity.*;
import toker.panel.repository.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
public class CraftingController {

    private CraftingStationRepository stationRepo;
    private CraftingStationInstanceRepository instanceRepo;
    private CraftingRecipeRepository recipeRepo;
    private CraftingRequestRepository requestRepo;
    private CraftingRecipeItemRequirementRepository requirementRepo;
    private ServerRepository serverRepo;

    public CraftingController(CraftingStationRepository stationRepo,
                              CraftingStationInstanceRepository instanceRepo,
                              CraftingRecipeRepository recipeRepo,
                              CraftingRequestRepository requestRepo,
                              CraftingRecipeItemRequirementRepository requirementRepo,
                              ServerRepository serverRepo) {
        this.stationRepo = stationRepo;
        this.instanceRepo = instanceRepo;
        this.recipeRepo = recipeRepo;
        this.requestRepo = requestRepo;
        this.requirementRepo = requirementRepo;
        this.serverRepo = serverRepo;
    }

    @GetMapping("/api/craftingStations")
    @JsonView(CraftingStation.View.None.class)
    public List<CraftingStation> craftingStations() {
        return stationRepo.findAll((root, query, builder) ->
                builder.equal(root.get(CraftingStation_.server).get(Server_.id), SelectedServerId.get()));
    }

    @GetMapping("/api/craftingStations/{index}")
    @JsonView(CraftingStation.View.None.class)
    public CraftingStation craftingStation(@PathVariable int index)
            throws ChangeSetPersister.NotFoundException {
        return stationRepo.findOne((root, query, builder) -> builder.and(
                builder.equal(root.get(CraftingStation_.index), index),
                builder.equal(root.get(CraftingStation_.server).get(Server_.id), SelectedServerId.get())
        )).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @PutMapping("/api/craftingStations")
    @JsonView(CraftingStation.View.None.class)
    public CraftingStation updateCraftingStation(@RequestBody CraftingStation craftingStation)
            throws ChangeSetPersister.NotFoundException {
        CraftingStation current = stationRepo.findOne((root, query, builder) -> builder.and(
                builder.equal(root.get(CraftingStation_.index), craftingStation.getIndex()),
                builder.equal(root.get(CraftingStation_.server).get(Server_.id), SelectedServerId.get())
        )).orElseThrow(ChangeSetPersister.NotFoundException::new);
        current.setName(craftingStation.getName());
        return stationRepo.save(current);
    }

    @GetMapping("/api/craftingStations/{index}/instances")
    @JsonView(CraftingStationInstance.View.None.class)
    public List<CraftingStationInstance> craftingStationInstances(@PathVariable int index) {
        return instanceRepo.findAll((root, query, builder) -> {
            Join<CraftingStationInstance, CraftingStation> join = root.join(CraftingStationInstance_.craftingStation);
            query.distinct(true);
            return builder.and(
                    builder.equal(join.get(CraftingStation_.index), index),
                    builder.equal(join.get(CraftingStation_.server).get(Server_.id), SelectedServerId.get())
            );
        });
    }

    interface CraftingRecipeView extends CraftingRecipe.View.Item,
            CraftingRecipe.View.Profession,
            CraftingRecipe.View.ItemRequirements {}

    @GetMapping("/api/craftingStations/{index}/recipes")
    @JsonView(CraftingRecipeView.class)
    public List<CraftingRecipe> craftingRecipes(@PathVariable int index) {
        return recipeRepo.findAll((root, query, builder) -> {
            root.fetch(CraftingRecipe_.itemRequirements, JoinType.LEFT);
            Join<CraftingRecipe, CraftingStation> join = root.join(CraftingRecipe_.craftingStation);
            query.distinct(true);
            return builder.and(
                    builder.equal(join.get(CraftingStation_.index), index),
                    builder.equal(join.get(CraftingStation_.server).get(Server_.id),
                            SelectedServerId.get())
            );
        });
    }

    @PutMapping("/api/craftingStations/recipes/{id}")
    @JsonView(CraftingRecipeView.class)
    public CraftingRecipe updateCraftingRecipe(@PathVariable int id,
                                               @RequestBody CraftingRecipe recipe)
            throws ChangeSetPersister.NotFoundException {
        if (recipe.getProfessionTier() < 1 || recipe.getProfessionTier() > 5)
            return null;

        CraftingRecipe current = recipeRepo.findOne((root, query, builder) ->
                builder.equal(root.get(CraftingRecipe_.id), id))
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        requirementRepo.deleteAllByCraftingRecipeId(current.getId());

        current.setItem(recipe.getItem());
        current.setProfession(recipe.getProfession());
        current.setProfessionTier(recipe.getProfessionTier());
        current.setPrice(recipe.getPrice());
        current.setHours(recipe.getHours());
        CraftingRecipe finalCurrent = current;
        Collection<CraftingRecipeItemRequirement> requirements = recipe.getItemRequirements().stream()
                .map(requirement -> {
                    requirement.setCraftingRecipe(finalCurrent);
                    return requirementRepo.save(requirement);
                })
                .collect(Collectors.toList());

        current = recipeRepo.save(current);
        current.setItemRequirements(requirements);
        return current;
    }

    @DeleteMapping("/api/craftingStations/recipes/{id}")
    public void deleteCraftingRecipe(@PathVariable int id) {
        recipeRepo.deleteById(id);
    }

    @PostMapping("/api/craftingStations")
    @JsonView(CraftingStation.View.None.class)
    public CraftingStation createCraftingStation(@RequestBody CraftingStation craftingStation) {
        craftingStation.setServer(serverRepo.getOne(SelectedServerId.get()));
        return stationRepo.save(craftingStation);
    }

    @PostMapping("/api/craftingStations/{index}/instances")
    @JsonView(CraftingStationInstance.View.None.class)
    public CraftingStationInstance createCraftingStationInstance(
            @PathVariable int index,
            @RequestBody CraftingStationInstance craftingStationInstance)
            throws ChangeSetPersister.NotFoundException {
        CraftingStation craftingStation = stationRepo.findOne((root, query, builder) -> builder.and(
                builder.equal(root.get(CraftingStation_.index), index),
                builder.equal(root.get(CraftingStation_.server).get(Server_.id), SelectedServerId.get())
        )).orElseThrow(ChangeSetPersister.NotFoundException::new);
        craftingStationInstance.setCraftingStation(craftingStation);
        return instanceRepo.save(craftingStationInstance);
    }

    @PostMapping("/api/craftingStations/{index}/recipes")
    @JsonView(CraftingStationInstance.View.None.class)
    public CraftingRecipe createCraftingRecipe(
            @PathVariable int index,
            @RequestBody CraftingRecipe craftingRecipe)
            throws ChangeSetPersister.NotFoundException {
        if (craftingRecipe.getProfessionTier() < 1 || craftingRecipe.getProfessionTier() > 5)
            return null;

        CraftingStation craftingStation = stationRepo.findOne((root, query, builder) -> builder.and(
                builder.equal(root.get(CraftingStation_.index), index),
                builder.equal(root.get(CraftingStation_.server).get(Server_.id), SelectedServerId.get())
        )).orElseThrow(ChangeSetPersister.NotFoundException::new);

        Collection<CraftingRecipeItemRequirement> requirements = craftingRecipe.getItemRequirements();
        craftingRecipe.setItemRequirements(null);
        craftingRecipe.setCraftingStation(craftingStation);
        craftingRecipe = recipeRepo.save(craftingRecipe);

        for (CraftingRecipeItemRequirement requirement : requirements) {
            requirement.setCraftingRecipe(craftingRecipe);
            requirementRepo.save(requirement);
        }

        craftingRecipe.setItemRequirements(requirements);

        return craftingRecipe;
    }
}
