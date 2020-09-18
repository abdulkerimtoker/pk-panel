package toker.panel.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book_page")
public class BookPage {
    private Integer id;
    private Integer pageNumber;
    private String content;
    private Book book;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    public Book getBook() {
        return book;
    }

    public void setBook(Book bookByBookId) {
        this.book = bookByBookId;
    }

    @Basic
    @Column(name = "page_number", nullable = false)
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Basic
    @Column(name = "content", nullable = false, length = 2048)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookPage bookPage = (BookPage) o;
        return Objects.equals(id, bookPage.id) &&
                Objects.equals(pageNumber, bookPage.pageNumber) &&
                Objects.equals(content, bookPage.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pageNumber, content);
    }
}
