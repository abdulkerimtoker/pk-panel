package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.Item;
import toker.warbandscripts.panel.entity.Player;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class PlayerLookupModel {
    @Size(min = 1, max = 32)
    @Pattern(regexp = "^[A-Za-z0-9_-]+")
    private String search;
    private Integer associateGUID;
    private Integer findItemId;
    private List<Player> searchResult;
    private Set<Integer> associationResult;
    private List<Item> itemList;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getAssociateGUID() {
        return associateGUID;
    }

    public void setAssociateGUID(Integer associateGUID) {
        this.associateGUID = associateGUID;
    }

    public List<Player> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<Player> searchResult) {
        this.searchResult = searchResult;
    }

    public Set<Integer> getAssociationResult() {
        return associationResult;
    }

    public void setAssociationResult(Set<Integer> associationResult) {
        this.associationResult = associationResult;
    }

    public Integer getFindItemId() {
        return findItemId;
    }

    public void setFindItemId(Integer findItemId) {
        this.findItemId = findItemId;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
