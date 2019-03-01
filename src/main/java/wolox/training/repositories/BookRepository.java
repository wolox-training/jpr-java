package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wolox.training.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByAuthor(String author);

    Book findByTitle(String title);

    Book findByIsbn(String isbn);

    List<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year);
}
