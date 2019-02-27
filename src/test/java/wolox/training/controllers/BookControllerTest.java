package wolox.training.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository service;

    @MockBean
    private Book someBook;


    @Test
    public void findAll() throws Exception {
        Book book = new Book();
        book.setTitle("title1");
        book.setGenre("genre1");
        book.setAuthor("author1");
        book.setImage("image1");
        book.setIsbn("isbn1");
        book.setPages(10000);
        book.setSubtitle("subtitle1");
        book.setPublisher("publisher1");
        book.setYear("1999");

        Book book2 = new Book();
        book2.setTitle("title2");
        book2.setGenre("genre2");
        book2.setAuthor("author2");
        book2.setImage("image2");
        book2.setIsbn("isbn2");
        book2.setPages(20000);
        book2.setSubtitle("subtitle2");
        book2.setPublisher("publisher2");
        book2.setYear("2000");

        List<Book> allBooks = Arrays.asList(book, book2);

        given(service.findAll()).willReturn(allBooks);
        mvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void findOne() throws Exception {
        someBook = new Book();
        someBook.setTitle("title test");
        someBook.setGenre("genre test");
        someBook.setAuthor("author test");
        someBook.setImage("image test");
        someBook.setIsbn("isbn test");
        someBook.setPages(4321);
        someBook.setSubtitle("subtitle test");
        someBook.setPublisher("publisher test");
        someBook.setYear("year test");

        given(service.findById(1l)).willReturn(Optional.of(someBook));
        mvc.perform(get("/api/books/{id}", 1l))
                .andExpect(status().isOk());
    }

    @Test
    public void findOneFail() throws Exception {
        mvc.perform(get("/api/books/{id}", 1l))
                .andExpect(status().isNotFound());
    }

    @Test
    public void create() throws Exception {
        Book book = new Book();
        book.setTitle("title1");
        book.setGenre("genre1");
        book.setAuthor("author1");
        book.setImage("image1");
        book.setIsbn("isbn1");
        book.setPages(9999);
        book.setSubtitle("subtitle1");
        book.setPublisher("publisher1");
        book.setYear("1800");

        mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.asJsonString(book)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createBookBadRequest() throws Exception {
        Book book = new Book();
        book.setTitle("title1");

        mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.asJsonString(book)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteOne() throws Exception {
        given(someBook.getId()).willReturn(1l);
        given(service.findById(someBook.getId())).willReturn(Optional.of(someBook));
        mvc.perform(delete("/api/books/{id}", someBook.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOneNotFound() throws Exception {
        mvc.perform(delete("/api/books/{id}", 2l))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateSuccess() throws Exception {
        String bodyBookJson = "{\n\t\"id\": 1,\n\t\"genre\": \"someGenre\",\n\t\"author\": \"someAuthor\",\n\t\"image\": \"someImage\",\n\t\"title\": \"someTitle\",\n\t\"subtitle\": \"someSubtitle\",\n\t\"publisher\": \"somePublisher\",\n\t\"year\":\"1999\",\n\t\"pages\":5000,\n\t\"isbn\":\"someIsbn\"\n}";
        given(service.findById(1l)).willReturn(Optional.of(someBook));
        mvc.perform(put("/api/books/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyBookJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBadRequest() throws Exception {
        given(someBook.getId()).willReturn(1l);
        String bodyBookJson = "{\"id\":\1\"}";
        mvc.perform(put("/api/books/{id}", 2l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyBookJson))
                .andExpect(status().is4xxClientError());

    }
}
