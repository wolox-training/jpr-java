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
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.Utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository serviceUser;

    @MockBean
    private BookRepository serviceBook;

    @MockBean
    private User someUser;

    @MockBean
    private Book someBook;

    @Test
    public void findAll() throws Exception{

        User user = new User();
        user.setBirthDate(LocalDate.now());
        user.setName("name1");
        user.setUsername("username1");

        List<User> allUsers = Arrays.asList(user);

        given(serviceUser.findAll()).willReturn(allUsers);

        mvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void findOne() throws Exception {
        someUser = new User();
        someUser.setBirthDate(LocalDate.now());
        someUser.setName("name1");
        someUser.setUsername("username1");
        given(serviceUser.findById(1l)).willReturn(Optional.of(someUser));
        mvc.perform(get("/api/users/{id}", 1l))
                .andExpect(status().isOk());
    }

    @Test
    public void findOneFail() throws Exception {
        given(serviceUser.findById(1l)).willReturn(Optional.of(someUser));
        mvc.perform(get("/api/users/{id}", 1l))
                .andExpect(status().isNotFound());
    }

    @Test
    public void create() throws Exception {
        String bodyUserJson = "\n{\"username\":\"username1\",\"name\":\"name1\",\"birthDate\":\"1990-12-7\"}";
        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyUserJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void createUserBadRequest() throws Exception {
        User user = new User();
        user.setUsername("username1");

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.asJsonString(user)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteOne() throws Exception {
        given(someUser.getId()).willReturn(1l);
        given(serviceUser.findById(someUser.getId())).willReturn(Optional.of(someUser));
        mvc.perform(delete("/api/users/{id}", someUser.getId()))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteOneNotFound() throws Exception {
        mvc.perform(delete("/api/users/{id}", 2l))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateSuccess() throws Exception {
        String bodyUserJson = "\n{\"id\": 1,\"username\":\"username1\",\"name\":\"name1\",\"birthDate\":\"1999-8-5\"}";
        given(serviceUser.findById(1l)).willReturn(Optional.of(someUser));
        mvc.perform(put("/api/users/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyUserJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBadRequest() throws Exception {
        given(someUser.getId()).willReturn(1l);
        String  bodyUserJson = "{\"id\":\1\"}";
        mvc.perform(put("/api/users/{id}", 2l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyUserJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addBookToCollection() throws Exception {
        someBook = new Book();
        someBook.setTitle("title1");
        someBook.setGenre("genre1");
        someBook.setAuthor("author1");
        someBook.setImage("image1");
        someBook.setIsbn("isbn1");
        someBook.setPages(10000);
        someBook.setSubtitle("subtitle1");
        someBook.setPublisher("publisher1");
        someBook.setYear("year test");
        given(serviceBook.findById(1l)).willReturn(Optional.of(someBook));

        given(serviceUser.findById(1l)).willReturn(Optional.of(someUser));

        mvc.perform(put("/api/users/1/addBook/1"))
                .andExpect(status().is2xxSuccessful());
    }
}
