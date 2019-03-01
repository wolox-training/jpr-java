package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.services.OpenLibraryService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OpenLibraryService openLibraryService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting" + " " + name;
    }

    @GetMapping
    @ResponseBody
    public Page<Book> findAll(@RequestParam(defaultValue = "") String publisher,
                              @RequestParam(defaultValue = "") String  genre,
                              @RequestParam(defaultValue = "") String year, Pageable pageable) {
        return bookRepository.findAll(publisher, genre, year, pageable);
    }

    @GetMapping("/title/{bookTitle}")
    public Book findByTitle(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("The book searched wasn't found in the DB"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws BookNotFoundException {
        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("The book searched wasn't found in the DB"));
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) throws BookIdMismatchException, BookNotFoundException {
        if (book.getId() != id) {
            throw new BookIdMismatchException("The ID entered doesn't match any Book ID in the DB");
        }
        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("The book searched wasn't found in the DB"));
        return bookRepository.save(book);
    }

    @GetMapping("isbn/{isbn}")
    public ResponseEntity<Book> findByIsbn(@PathVariable String isbn){
        Book requestedBook = this.bookRepository.findByIsbn(isbn);
        if (requestedBook == null){
            requestedBook = this.openLibraryService.renderBookWithExternalApi(isbn);
            if (requestedBook == null){
                return new ResponseEntity(System.getenv("There is no book in the system that has the ISBN entered"),
                        HttpStatus.BAD_REQUEST);
            }
            this.bookRepository.save(requestedBook);
        }
        return new ResponseEntity<>(requestedBook, HttpStatus.OK);
    }

    @GetMapping("/publishergenreyear")
    public Page<Book> findByPublisherGenreYear(@RequestParam(name="publisher", required=false) String publisher,
                                               @RequestParam(name="genre", required=false) String genre,
                                               @RequestParam(name="year", required=false) String year,
                                               Pageable pageable){
        return bookRepository.findByPublisherAndGenreAndYear(publisher, genre, year, pageable);
    }
}
