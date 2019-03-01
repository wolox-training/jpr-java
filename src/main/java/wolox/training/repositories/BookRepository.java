package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wolox.training.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByAuthor(String author);

    Book findByTitle(String title);

    Book findByIsbn(String isbn);

    @Query(value = "select b from Book b where (b.publisher = :publisher or b.publisher is null)" +
            "AND (b.genre = :genre or b.genre is null) AND (b.year = :year or b.year is null)")
    List<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year);

    @Query(value = "select b from Book b where (b.title like %:bookTitle%) AND (b.genre like %:genre%) " +
                    "AND (b.author like %:author%)")
    Iterable findAll(String bookTitle, String genre, String author);

}
