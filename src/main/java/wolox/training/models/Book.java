package wolox.training.models;

import org.assertj.core.util.Preconditions;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String genre;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Integer pages;

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<>();

    public Book(){ }

    public Book(String author, String image, String title, String subtitle, String publisher, String year,
                String isbn, Integer pages) {
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.isbn = isbn;
        this.pages = pages;
    }

    public Long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = Preconditions.checkNotNull(author, "The author entered it's not permitted because it's null");
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = Preconditions.checkNotNull(image, "The image can't be null");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Preconditions.checkNotNull(title, "The title is mandatory");
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.genre = Preconditions.checkNotNull(genre, "The subtitle is null and is not accepte");
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = Preconditions.checkNotNull(year, "The year can't be null");
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = Preconditions.checkNotNull(isbn, "Isbn is null");
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = Preconditions.checkNotNull(pages, "Pages must be different than null");
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
