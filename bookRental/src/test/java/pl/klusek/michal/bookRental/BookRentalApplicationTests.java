package pl.klusek.michal.bookRental;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookRentalApplicationTests {

	@Autowired
	private Database database;

	@Test
	void editBookTest() {
		Book book = generateBook();
		boolean result = this.database.editBook(book, 1);

		Assert.assertTrue(result);
	}

	@Test
	void addBookTest(){
		Book book = generateBook();
		boolean result = this.database.addBook(book);

		Assert.assertTrue(result);
	}

	private Book generateBook(){
		return new Book("Bzyku", "Historie z Plechem", 69.69, "978-81-283-5742-1");
	}
}
