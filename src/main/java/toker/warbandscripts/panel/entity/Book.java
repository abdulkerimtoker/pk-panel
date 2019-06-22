package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "pax", catalog = "")
public class Book {
    private Integer id;
    private String name;
    private Player playerByOwnerId;
    private Collection<BookPage> bookPagesById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    public Player getPlayerByOwnerId() {
        return playerByOwnerId;
    }

    public void setPlayerByOwnerId(Player playerByOwnerId) {
        this.playerByOwnerId = playerByOwnerId;
    }

    @OneToMany(mappedBy = "bookByBookId")
    public Collection<BookPage> getBookPagesById() {
        return bookPagesById;
    }

    public void setBookPagesById(Collection<BookPage> bookPagesById) {
        this.bookPagesById = bookPagesById;
    }
}
