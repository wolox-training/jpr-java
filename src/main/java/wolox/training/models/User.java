package wolox.training.models;

import org.springframework.beans.factory.annotation.Autowired;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.repositories.BookRepository;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class User {

    @Autowired
    private BookRepository bookRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique=true)
    private  String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH})
    private List<Book> books = new ArrayList<>();

    public User(){ }

    public User(String username, String name, LocalDate birthdate) {
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
    }

    public Long getId() {
        return id;
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
        if (this.books.isEmpty()){
            throw new BookNotFoundException("The book searched wasn't found in the DB");
        }else{
            Optional bookToDelete = this.books.stream().filter(book -> book.getId().equals(bookId))
                    .findFirst();
            if (bookToDelete.isPresent()) {
                this.books.remove(bookToDelete);
            }else{ throw new BookNotFoundException("The book searched wasn't found in the DB"); }
        }
    }
}
