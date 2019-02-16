package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional findOne(@PathVariable Long id) throws BookNotFoundException {
        return userRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        return userRepository.save(user);
    }

    @PostMapping("/addBook/{username}")
    public User addBookToCollection(@RequestBody Book book, @PathVariable String username) {
        User user_requested = userRepository.findByUserName(username);
        user_requested.addBookToCollection(book);
        return userRepository.save(user_requested);
    }

    @PostMapping("/deleteBook/{username}")
    public User deleteBookFromCollection(@RequestBody Long bookId, @PathVariable String username) throws BookNotFoundException {
        User user_requested = userRepository.findByUserName(username);
        user_requested.deleteBookFromCollection(bookId);
        return userRepository.save(user_requested);
    }
}
