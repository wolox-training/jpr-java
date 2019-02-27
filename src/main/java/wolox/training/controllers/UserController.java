package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.helpers.Password;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private BookRepository bookRepository;

    @GetMapping
    @ResponseBody
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("The user searched wasn't found in the DB"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}/password")
    public void updatePassword(@RequestBody Password updatePassword, @PathVariable Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("The ID sent didn't match any user in the DB"));
        user.setPassword(updatePassword.getPassword());
        userRepository.save(user);
    }

    @PostMapping("/{id}/addBook/{bookId}")
    public void addBookToCollection(@PathVariable Long id, @PathVariable Long bookId) throws UserNotFoundException, BookNotFoundException, BookAlreadyOwnedException {
        User user_requested = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("The user searched wasn't found in the DB"));
        Book book_sent = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("The book id sent wasn't found in the DB"));
        user_requested.addBookToCollection(book_sent);
    }

    @PostMapping("{id}/deleteBook/{bookId}")
    public void deleteBookFromCollection(@PathVariable Long id, @PathVariable Long bookId) throws BookNotFoundException, UserNotFoundException {
        User user_requested = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("The user searched wasn't found in the DB"));
        user_requested.deleteBookFromCollection(bookId);
    }
}
