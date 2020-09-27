package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.web.bind.annotation.*
import toker.panel.bean.SelectedServerId
import toker.panel.entity.*
import toker.panel.entity.CraftingRecipe.View.ItemRequirements
import toker.panel.repository.*
import java.util.stream.Collectors
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Root
import javax.transaction.Transactional

@RestController
@Transactional
class CraftingController(private val stationRepo: CraftingStationRepository,
                         private val instanceRepo: CraftingStationInstanceRepository,
                         private val recipeRepo: CraftingRecipeRepository,
                         private val requestRepo: CraftingRequestRepository,
                         private val requirementRepo: CraftingRecipeItemRequirementRepository,
                         private val serverRepo: ServerRepository) {
    @GetMapping("/api/craftingStations")
    @JsonView(CraftingStation.View.None::class)
    fun craftingStations(): List<CraftingStation> {
        return stationRepo.findAll { root: Root<CraftingStation?>, _, builder: CriteriaBuilder ->
            builder.equal(root.get(CraftingStation_.server).get(Server_.id), SelectedServerId) }
    }

    @GetMapping("/api/craftingStations/{index}")
    @JsonView(CraftingStation.View.None::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun craftingStation(@PathVariable index: Int): CraftingStation {
        return stationRepo.findOne { root: Root<CraftingStation?>, _, builder: CriteriaBuilder ->
            builder.and(
                    builder.equal(root.get(CraftingStation_.index), index),
                    builder.equal(root.get(CraftingStation_.server).get(Server_.id), SelectedServerId)
            )
        }.orElseThrow { ChangeSetPersister.NotFoundException() }
    }

    @PutMapping("/api/craftingStations")
    @JsonView(CraftingStation.View.None::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun updateCraftingStation(@RequestBody craftingStation: CraftingStation): CraftingStation {
        val current = stationRepo.findOne { root: Root<CraftingStation?>, _, builder: CriteriaBuilder ->
            builder.and(
                    builder.equal(root.get(CraftingStation_.index), craftingStation.index),
                    builder.equal(root.get(CraftingStation_.server).get(Server_.id), SelectedServerId)
            )
        }.orElseThrow { ChangeSetPersister.NotFoundException() }
        current.name = craftingStation.name
        return stationRepo.save(current)
    }

    @GetMapping("/api/craftingStations/{index}/instances")
    @JsonView(CraftingStationInstance.View.None::class)
    fun craftingStationInstances(@PathVariable index: Int): List<CraftingStationInstance> {
        return instanceRepo.findAll { root: Root<CraftingStationInstance?>, query: CriteriaQuery<*>, builder: CriteriaBuilder ->
            val join = root.join(CraftingStationInstance_.craftingStation)
            query.distinct(true)
            builder.and(
                    builder.equal(join.get(CraftingStation_.index), index),
                    builder.equal(join.get(CraftingStation_.server).get(Server_.id), SelectedServerId)
            )
        }
    }

    internal interface CraftingRecipeView : CraftingRecipe.View.Item, CraftingRecipe.View.Profession, ItemRequirements

    @GetMapping("/api/craftingStations/{index}/recipes")
    @JsonView(CraftingRecipeView::class)
    fun craftingRecipes(@PathVariable index: Int): List<CraftingRecipe> {
        return recipeRepo.findAll { root: Root<CraftingRecipe?>, query: CriteriaQuery<*>, builder: CriteriaBuilder ->
            root.fetch(CraftingRecipe_.itemRequirements, JoinType.LEFT)
            val join = root.join(CraftingRecipe_.craftingStation)
            query.distinct(true)
            builder.and(
                    builder.equal(join.get(CraftingStation_.index), index),
                    builder.equal(join.get(CraftingStation_.server).get(Server_.id), SelectedServerId)
            )
        }
    }

    @PutMapping("/api/craftingStations/recipes/{id}")
    @JsonView(CraftingRecipeView::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun updateCraftingRecipe(@PathVariable id: Int,
                             @RequestBody recipe: CraftingRecipe): CraftingRecipe? {
        if (recipe.professionTier!! < 1 || recipe.professionTier!! > 5) return null
        var current = recipeRepo.findOne { root: Root<CraftingRecipe?>, _, builder: CriteriaBuilder ->
            builder.equal(root.get(CraftingRecipe_.id), id) }
                .orElseThrow { ChangeSetPersister.NotFoundException() }
        requirementRepo.deleteAllByCraftingRecipeId(current.id!!)
        current.item = recipe.item
        current.profession = recipe.profession
        current.professionTier = recipe.professionTier
        current.price = recipe.price
        current.hours = recipe.hours
        val finalCurrent = current
        val requirements = recipe.itemRequirements!!.stream()
                .map { requirement: CraftingRecipeItemRequirement ->
                    requirement.craftingRecipe = finalCurrent
                    requirementRepo.save(requirement)
                }
                .collect(Collectors.toSet())
        current = recipeRepo.save(current)
        current.itemRequirements = requirements
        return current
    }

    @DeleteMapping("/api/craftingStations/recipes/{id}")
    fun deleteCraftingRecipe(@PathVariable id: Int) {
        recipeRepo.deleteById(id)
    }

    @PostMapping("/api/craftingStations")
    @JsonView(CraftingStation.View.None::class)
    fun createCraftingStation(@RequestBody craftingStation: CraftingStation): CraftingStation {
        craftingStation.server = serverRepo.getOne(SelectedServerId)
        return stationRepo.save(craftingStation)
    }

    @PostMapping("/api/craftingStations/{index}/instances")
    @JsonView(CraftingStationInstance.View.None::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun createCraftingStationInstance(
            @PathVariable index: Int,
            @RequestBody craftingStationInstance: CraftingStationInstance): CraftingStationInstance {
        val craftingStation = stationRepo.findOne { root: Root<CraftingStation?>, _, builder: CriteriaBuilder ->
            builder.and(
                    builder.equal(root.get(CraftingStation_.index), index),
                    builder.equal(root.get(CraftingStation_.server).get(Server_.id), SelectedServerId)
            )
        }.orElseThrow { ChangeSetPersister.NotFoundException() }
        craftingStationInstance.craftingStation = craftingStation
        return instanceRepo.save(craftingStationInstance)
    }

    @PostMapping("/api/craftingStations/{index}/recipes")
    @JsonView(CraftingStationInstance.View.None::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun createCraftingRecipe(
            @PathVariable index: Int,
            @RequestBody craftingRecipe: CraftingRecipe): CraftingRecipe? {
        var craftingRecipe = craftingRecipe
        if (craftingRecipe.professionTier!! < 1 || craftingRecipe.professionTier!! > 5) return null
        val craftingStation = stationRepo.findOne { root: Root<CraftingStation?>, _, builder: CriteriaBuilder ->
            builder.and(
                    builder.equal(root.get(CraftingStation_.index), index),
                    builder.equal(root.get(CraftingStation_.server).get(Server_.id), SelectedServerId)
            )
        }.orElseThrow { ChangeSetPersister.NotFoundException() }
        val requirements: MutableSet<CraftingRecipeItemRequirement>? = craftingRecipe.itemRequirements
        craftingRecipe.itemRequirements = null
        craftingRecipe.craftingStation = craftingStation
        craftingRecipe = recipeRepo.save(craftingRecipe)
        for (requirement in requirements!!) {
            requirement.craftingRecipe = craftingRecipe
            requirementRepo.save(requirement)
        }
        craftingRecipe.itemRequirements = requirements
        return craftingRecipe
    }
}