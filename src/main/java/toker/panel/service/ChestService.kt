package toker.panel.service

import org.springframework.stereotype.Service
import toker.panel.entity.Chest
import toker.panel.entity.pk.ChestPK
import toker.panel.repository.ChestRepository
import toker.panel.repository.ChestSlotRepository
import toker.panel.repository.ItemRepository
import java.util.*
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Root
import javax.transaction.Transactional

@Service
@Transactional
class ChestService(private val chestRepository: ChestRepository,
                   private val chestSlotRepository: ChestSlotRepository,
                   private val itemRepository: ItemRepository) {
    fun getChest(index: Int, serverId: Int): Optional<Chest> {
        return chestRepository.findOne { root: Root<Chest?>, _, builder: CriteriaBuilder ->
            root.fetch<Any, Any>("slots", JoinType.LEFT)
            builder.and(
                    builder.equal(root.get<Any>("index"), index),
                    builder.equal(root.get<Any>("server").get<Any>("id"), serverId)
            )
        }
    }

    fun updateChestSlot(index: Int, serverId: Int,
                        slot: Int, itemId: Int, ammo: Int) {
        chestSlotRepository.updateChestSlot(chestRepository.getOne(ChestPK(index, serverId)),
                slot, itemRepository.getOne(itemId), ammo)
    }
}