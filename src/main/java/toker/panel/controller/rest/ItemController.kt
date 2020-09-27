package toker.panel.controller.rest;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.panel.entity.Item;
import toker.panel.repository.ItemRepository;

import java.util.List;

@RestController
public class ItemController {

    private ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/api/item")
    @Cacheable("items")
    public List<Item> items() {
        return itemRepository.findAll();
    }
}