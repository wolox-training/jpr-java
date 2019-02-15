package wolox.training.models;

import org.springframework.beans.factory.annotation.Autowired;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.repositories.BookRepository;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class User {

    @Autowired
    private BookRepository bookRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private  String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "book_user",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"))
    private List<Book> books;

    public User(){ }

    public User(String username, String name, LocalDate birthdate) {
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBookToCollection(Book book){
        this.books.add(book);
    }

    public void deleteBookFromCollection(Long bookId) throws BookNotFoundException {
        this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("The book searched wasn't found in the DB"));
        this.bookRepository.deleteById(id);
    }
}
