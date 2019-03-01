package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import wolox.training.models.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Book findByAuthor(String author);

    Book findByTitle(String title);

    Book findByIsbn(String isbn);

    @Query(value = "select b from Book b where (b.publisher = :publisher or b.publisher is null)" +
            "AND (b.genre = :genre or b.genre is null) AND (b.year = :year or b.year is null)")
    Page<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year, Pageable pageable);

    @Query(value = "select b from Book b where (b.title like %:bookTitle%) AND (b.genre like %:genre%) " +
                    "AND (b.author like %:author%)")
    Page<Book> findAll(String bookTitle, String genre, String author, Pageable pageable);

}
