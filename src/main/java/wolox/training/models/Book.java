package wolox.training.models;

import com.google.common.base.Preconditions;
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
        Preconditions.checkArgument(!author.isEmpty() && author != null,
                "The author cannot be empty or null");
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        Preconditions.checkArgument(!image.isEmpty() && image != null,
                "The image cannot be empty or null");
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Preconditions.checkArgument(!title.isEmpty() && title != null,
                "The title cannot be empty or null");
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkArgument(!subtitle.isEmpty() && subtitle != null,
                "The subtitle cannot be empty or null");
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkArgument(!publisher.isEmpty() && publisher != null,
                "The subtitle cannot be empty or null");
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        Preconditions.checkArgument(!year.isEmpty() && year != null,
                "The year cannot be empty or null");
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkArgument(!isbn.isEmpty() && isbn != null,
                "isbn cannot be empty or null");
        this.isbn = isbn;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        Preconditions.checkArgument(pages > 0,
                "The number of pages must be greater than 0");
        this.pages = pages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
