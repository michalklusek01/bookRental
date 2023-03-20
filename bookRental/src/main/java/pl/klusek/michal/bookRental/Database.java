package pl.klusek.michal.bookRental;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Database {
    List<Book> books = new ArrayList<>();

    public Database(){
        books.add(new Book("Tomasz Jaśniewski", "C++ Zbiór zadań z rozwiązaniami", 20.0, "978-83-832-2202-8"));
        books.add(new Book("Cay Horstmann", "Java. Podstawy. Wydanie XII", 80.35, "978-83-283-9479-7"));
        books.add(new Book("Craig Walls", "Spring w akcji. Wydanie V", 48.95, "978-83-283-5606-1"));
    }

    public boolean addBook(Book book){
        return books.add(book);
    }

    public List<Book> getBooks(){
        return books;
    }
}
