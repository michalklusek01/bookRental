package pl.klusek.michal.bookRental;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Book {
    private String author;
    private String title;
    private double price;
    private String isbn;

    public Book(String author, String title, double price, String isbn) {
        this.author = author;
        this.title = title;
        this.price = price;
        this.isbn = isbn;
    }
}
