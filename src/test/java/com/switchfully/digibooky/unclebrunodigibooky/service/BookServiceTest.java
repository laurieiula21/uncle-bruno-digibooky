package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.api.mapper.BookMapper;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Author;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.BookNotAvailableException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.IsbnDoesNotExistException;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookHistoryRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanHistoryRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookServiceTest {
    @Autowired
    BookService bookService;
    @Autowired
    BookLoanService bookLoanService;

    @Test
    void GivenANotExistingISBN_WhenGettingOneBook_ThenThrowAnException() {
        assertThatExceptionOfType(IsbnDoesNotExistException.class).isThrownBy(() -> bookService.getOneBook("wrongISBN)"));
    }

    @Test
    void GivenAnISBNQuery_WhenSearchingISBN_ThenReturnSingleBookContainingISBN() {

        String givenIsbn = "2";
        List<Book> expectedBookList = List.of(new Book("isbn2", "Title 2", new Author("Second", "Last"), "This is the summary of 69"));

        assertThat(expectedBookList.get(0).getIsbn()).isEqualTo(bookService.searchBookByISBN(givenIsbn).get(0).getIsbn());

    }

    @Test
    void GivenAnISBNQuery_WhenSearchingISBN_ThenReturnListOfBooksContainingISBN() {
        List<Book> expectedBookList = List.of(new Book("isbn1", "Title 1", new Author("First", "Last"), "This is the summary of 69"),
                new Book("isbn2", "Title 2", new Author("Second", "Last"), "This is the summary of 69"),
                new Book("isbn3", "Title 3", new Author("Third", "Last"), "This is the summary of 69"));

        String givenIsbn = "i";

        assertThat(expectedBookList.get(0).getIsbn()).isEqualTo(bookService.searchBookByISBN(givenIsbn).get(0).getIsbn());
        assertThat(expectedBookList.get(1).getIsbn()).isEqualTo(bookService.searchBookByISBN(givenIsbn).get(1).getIsbn());
        assertThat(expectedBookList.get(2).getIsbn()).isEqualTo(bookService.searchBookByISBN(givenIsbn).get(2).getIsbn());
    }

    @Test
    void GivenBook_WhenRegisteringBook_ThenBookIsAddedToRepository() {
        Book actualBook = new Book("isbn5", "registeredBook", new Author("Second", "Last"), "This book should be registered in the list");

        bookService.registerBook(actualBook);
        assertThat(bookService.getAllBooks()).contains(actualBook);
    }

    @Test
    void GivenAuthorName_WhenSearchingByAuthor_ThenReturnBooksOfThatAuthor() {
        String givenAuthorName = "First";
        List<Book> expectedList = List.of(
                new Book("isbn1", "Title 1", new Author("First", "Last"), "This is the summary of 69"),
                new Book("isbn4", "Title 4", new Author("First", "Last"), "This is the summary of 69")
        );
        assertThat(bookService.searchBookByAuthor(givenAuthorName).get(0).getIsbn()).isEqualTo(expectedList.get(0).getIsbn());
        assertThat(bookService.searchBookByAuthor(givenAuthorName).get(1).getIsbn()).isEqualTo(expectedList.get(1).getIsbn());
    }
    @Test
    void givenBookWithTitleInList_whenSearchingForBooksWithFullTitle_thenBookWithMatchInTitleIsInTheListReturned() {
        String myTitle1 = "exampleTitle";
        Book book1 = new Book("isbn500",myTitle1,null,null);
        String myTitle2 = "notAMatch";
        Book book2 = new Book("isbn501",myTitle2,null,null);
        bookService.registerBook(book1);
        bookService.registerBook(book2);
        Assertions.assertThat(bookService.searchBooksByTitle(myTitle1)).contains(book1);
        Assertions.assertThat(bookService.searchBooksByTitle(myTitle1)).doesNotContain(book2);
    }

    @Test
    void givenBookWithTitleInList_whenSearchingForBooksWithWildcardsTitle_thenBookWithMatchInTitleIsInTheListReturned() {
        String myTitle1 = "exampleTitle";
        Book book1 = new Book("isbn500",myTitle1,null,null);
        String myTitle2 = "notAMatch";
        Book book2 = new Book("isbn501",myTitle2,null,null);
        bookService.registerBook(book1);
        bookService.registerBook(book2);
        Assertions.assertThat(bookService.searchBooksByTitle("*Title")).contains(book1);
        Assertions.assertThat(bookService.searchBooksByTitle("*Title")).doesNotContain(book2);
    }

    @Test
    void givenBooks_whenSearchingForBooksWithOnlyWildcard_thenAllBooksInTheListReturned() {
        String myTitle1 = "exampleTitle1";
        Book book1 = new Book("isbn500",myTitle1,null,null);
        String myTitle2 = "exampleTitle2";
        Book book2 = new Book("isbn501",myTitle2,null,null);
        bookService.registerBook(book1);
        bookService.registerBook(book2);
        Assertions.assertThat(bookService.searchBooksByTitle("*")).contains(book1);
        Assertions.assertThat(bookService.searchBooksByTitle("*")).contains(book2);
    }

    @Test
    void givenExistingBook_whenUpdatingBookWithNewInformation_thenBooksInformationIsAllUpdated(){
        Author author = new Author("lastName","firstName");
        Book oldBook = new Book("realIsbn","title",author,"summary");
        bookService.registerBook(oldBook);

        Author newAuthor = new Author("newLastName","newFirstName");
        Book bookToUpdate = new Book("fakeIsbn","newTitle",newAuthor,"newSummary");
        Book updatedBook = bookService.updateBook(oldBook.getIsbn(),bookToUpdate);

        Assertions.assertThat(updatedBook.getIsbn()).isNotEqualTo(bookToUpdate.getIsbn());
        Assertions.assertThat(updatedBook.getTitle()).isEqualTo(bookToUpdate.getTitle());
        Assertions.assertThat(updatedBook.getAuthor()).isEqualTo(bookToUpdate.getAuthor());
        Assertions.assertThat(updatedBook.getSummary()).isEqualTo(bookToUpdate.getSummary());
    }

    @Test
    void givenExistingBook_whenDeletingThatBook_thenTheBookIsNotInTheBookRepositoryAnymore(){
        Author author = new Author("lastName","firstName");
        Book book = new Book("realIsbn","title",author,"summary");
        bookService.registerBook(book);
        String bookId = book.getId();

        bookService.deleteBookBy(bookId);

        Assertions.assertThat(!bookService.getAllBooks().contains(book)).isTrue();


    }

    @Test
    void givenExistingBook_whenDeletingThatBook_thenTheBookIInTheBookHistoryRepository(){
        Author author = new Author("lastName","firstName");
        Book book = new Book("realIsbn","title",author,"summary");
        bookService.registerBook(book);
        String bookId = book.getId();

        bookService.deleteBookBy(bookId);

        Assertions.assertThat(bookService.getBookHistory().contains(book)).isTrue();

    }

    @Test
    void givenExistingBookThatIsLentOut_whenDeletingThatBook_thenThrowBookNotAvailableException(){
        Author author = new Author("lastName","firstName");
        Book book = new Book("realIsbn","title",author,"summary");
        bookService.registerBook(book);
        String bookId = book.getId();
        bookLoanService.lendBook("realIsbn", "aUser");

        Assertions.assertThat(bookService.getAllBooks().contains(book)).isTrue();
        Assertions.assertThat(bookService.getBookHistory().contains(book)).isFalse();
        Assertions.assertThatThrownBy(() -> bookService.deleteBookBy(bookId))
                .isInstanceOf(BookNotAvailableException.class);

    }
    @Test
    void givenExistingBookInHistoryRepository_whenUpdatingBookWithNewInformation_thenBooksInformationIsAllUpdated(){
        Author author = new Author("lastName","firstName");
        Book oldBook = new Book("realIsbn","title",author,"summary");
        String oldBookId = bookService.registerBook(oldBook).getId();


        Author newAuthor = new Author("newLastName","newFirstName");
        Book bookToUpdate = new Book("fakeIsbn","newTitle",newAuthor,"newSummary");
        Book updatedBook = bookService.updateBook(oldBook.getIsbn(),bookToUpdate);

        Assertions.assertThat(updatedBook.getIsbn()).isNotEqualTo(bookToUpdate.getIsbn());
        Assertions.assertThat(updatedBook.getTitle()).isEqualTo(bookToUpdate.getTitle());
        Assertions.assertThat(updatedBook.getAuthor()).isEqualTo(bookToUpdate.getAuthor());
        Assertions.assertThat(updatedBook.getSummary()).isEqualTo(bookToUpdate.getSummary());
    }

}