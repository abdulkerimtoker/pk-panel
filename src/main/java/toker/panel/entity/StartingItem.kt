package toker.panel.entity;

import toker.panel.entity.pk.StartingItemPK;

import javax.persistence.*;

@Entity
@Table(name = "starting_item")
@IdClass(StartingItemPK.class)
public class StartingItem {

    private Server server;
    private Item item;

    @Id
    @ManyToOne
    @JoinColumn(name = "server_id", referencedColumnName = "id")
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
