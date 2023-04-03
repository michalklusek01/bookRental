package pl.klusek.michal.bookRental;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CommonController {
    private static final Logger logger = Logger.getLogger(CommonController.class);

    @Autowired
    Database database;

    @GetMapping("/api/books")
    public List<Book> getAllBooks(){
        List<Book> booksFromDb = database.getBooks();
        if(!(booksFromDb == null)){
            logger.info("Success to get books from Db");
        }else{
            logger.error("Failed to get books from Db");
        }
        return booksFromDb;
    }

    @PostMapping("/api/books")
    public boolean addBook(@RequestBody Book book){
        boolean result =  database.addBook(book);
        if(result){
            logger.info("Adding new book: " + book.getTitle());
        }else{
            logger.error("Failed to add new book: " + book.getTitle());
        }
        return result;
    }

    @GetMapping("/api/books/{id}")
    public Book getBookById(@PathVariable String id){
        int parseId = Integer.parseInt(id);
        Book book;
        try{
            book = database.books.get(parseId);
            logger.info("Success to get book by id " + id);
            return book;
        }catch (IndexOutOfBoundsException e){
            logger.error("Failed to get book by id " + id);
            return null;
        }
    }

    @PutMapping("/api/books/{id}")
    public boolean updateBook(@PathVariable String id, @RequestBody Book updateBook){
        int parseId = Integer.parseInt(id);
        boolean result = this.database.editBook(updateBook, parseId);
        if(result){
            logger.info("Success to edit book by id " + id);
        }else{
            logger.error("Failed to edit book by id " + id);
        }

        return this.database.editBook(updateBook, parseId);
    }

    @GetMapping("/api/books/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author){
        List<Book> booksFromDb = database.getBooksByAuthor(author);
        if(!(booksFromDb == null) && !(booksFromDb.isEmpty())){
            logger.info("Success to get books from Db by author: " + author);
        }else{
            logger.error("Failed to get books from Db by author: " + author);
        }
        return database.getBooksByAuthor(author);
    }
}
