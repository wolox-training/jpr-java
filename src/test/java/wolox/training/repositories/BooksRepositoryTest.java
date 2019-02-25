package wolox.training.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.Book;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BooksRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByAuthorTest() {
        Book testBook = new Book();
        testBook.setTitle("title1");
        testBook.setGenre("genre1");
        testBook.setAuthor("author1");
        testBook.setImage("image1");
        testBook.setIsbn("isbn1");
        testBook.setPages(10000);
        testBook.setSubtitle("subtitle1");
        testBook.setPublisher("publisher1");
        testBook.setYear("1999");
        entityManager.persistAndFlush(testBook);
        Book bookFound = bookRepository.findByAuthor(testBook.getAuthor());
        assertThat(bookFound).isEqualTo(testBook);
    }

    @Test
    public void findByTitleTest() {
        Book testBook = new Book();
        testBook.setTitle("title1");
        testBook.setGenre("genre1");
        testBook.setAuthor("author1");
        testBook.setImage("image1");
        testBook.setIsbn("isbn1");
        testBook.setPages(10000);
        testBook.setSubtitle("subtitle1");
        testBook.setPublisher("publisher1");
        testBook.setYear("1999");
        entityManager.persistAndFlush(testBook);
        Book bookFound = bookRepository.findByTitle(testBook.getTitle());
        assertThat(bookFound).isEqualTo(testBook);
    }

    @Test
    public void findAllTest() {
        Book testBook = new Book();
        testBook.setTitle("title1");
        testBook.setGenre("genre1");
        testBook.setAuthor("author1");
        testBook.setImage("image1");
        testBook.setIsbn("isbn1");
        testBook.setPages(10000);
        testBook.setSubtitle("subtitle1");
        testBook.setPublisher("publisher1");
        testBook.setYear("1999");

        Book testBook2 = new Book();
        testBook2.setTitle("title2");
        testBook2.setGenre("genre2");
        testBook2.setAuthor("author2");
        testBook2.setImage("image2");
        testBook2.setIsbn("isbn2");
        testBook2.setPages(20000);
        testBook2.setSubtitle("subtitle2");
        testBook2.setPublisher("publisher2");
        testBook2.setYear("2000");

        List<Book> booksList = Arrays.asList(testBook, testBook2);

        for(Book book : booksList) {
            entityManager.persistAndFlush(book);
        }

        List<Book> booksFound = bookRepository.findAll();
        assertThat(booksFound.size()).isEqualTo(2);
    }

    @Test
    public void save() {
        Book testBook = new Book();
        testBook.setTitle("title1");
        testBook.setGenre("genre1");
        testBook.setAuthor("author1");
        testBook.setImage("image1");
        testBook.setIsbn("isbn1");
        testBook.setPages(10000);
        testBook.setSubtitle("subtitle1");
        testBook.setPublisher("publisher1");
        testBook.setYear("1999");
        Book saved = bookRepository.save(testBook);
        assertThat(testBook).isEqualTo(saved);
    }

    @Test
    public void findById() {
        Book testBook = new Book();
        testBook.setTitle("title1");
        testBook.setGenre("genre1");
        testBook.setAuthor("author1");
        testBook.setImage("image1");
        testBook.setIsbn("isbn1");
        testBook.setPages(10000);
        testBook.setSubtitle("subtitle1");
        testBook.setPublisher("publisher1");
        testBook.setYear("1999");
        entityManager.persistAndFlush(testBook);
        Book bookFound = bookRepository.findById(testBook.getId()).get();
        assertThat(bookFound).isEqualTo(testBook);
    }
}
