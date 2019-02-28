package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByAuthor(String author);

    Book findByTitle(String title);

    Book findByIsbn(String isbn);

    @Query(value = "select b from Books b where b.publisher=:publisher or b.year=:genre")
    List<Book> findByNameOrNameIsNull(@Param("publisher") String publisher, @Param("genre") String genre);

}
