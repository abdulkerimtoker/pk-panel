package toker.panel.controller.rest

import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import toker.panel.entity.Item
import toker.panel.repository.ItemRepository

@RestController
class ItemController(private val itemRepository: ItemRepository) {
    @GetMapping("/api/item")
    @Cacheable("items")
    fun items(): List<Item> {
        return itemRepository.findAll()
    }
}