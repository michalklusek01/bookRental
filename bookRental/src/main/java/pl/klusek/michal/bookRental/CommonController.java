package pl.klusek.michal.bookRental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommonController {
    @Autowired
    Database database;

    @GetMapping("/api/books")
    public List<Book> getAllBooks(){
        return database.getBooks();
    }

    @PostMapping("/api/books")
    public boolean addBook(@RequestBody Book book){
        return database.addBook(book);
    }

    @GetMapping("/api/books/{id}")
    public Book getBookById(@PathVariable String id){
        int parseId = Integer.parseInt(id);
        return database.books.get(parseId);
    }

    @PutMapping("/api/books/{id}")
    public boolean updateBook(@PathVariable String id, @RequestBody Book updateBook){
        int parseId = Integer.parseInt(id);
        return this.database.editBook(updateBook, parseId);
    }
}
