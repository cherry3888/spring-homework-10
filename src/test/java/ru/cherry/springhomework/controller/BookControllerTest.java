package ru.cherry.springhomework.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Comment;
import ru.cherry.springhomework.domain.Genre;
import ru.cherry.springhomework.service.AuthorService;
import ru.cherry.springhomework.service.BookService;
import ru.cherry.springhomework.service.CommentService;
import ru.cherry.springhomework.service.GenreService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookRestController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookService bookService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    CommentService commentService;

    @Test
    void getAllBooks() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book1 = new Book(1L, "Book-1", author, genre);
        Book book2 = new Book(1L, "Book-2", author, genre);
        List<Book> books = List.of(book1, book2);
        given(bookService.getAllBooks()).willReturn(books);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void editBook() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);
        given(bookService.getById(1L)).willReturn(book);
        given(bookService.save(book)).willReturn(book);

        mockMvc.perform( MockMvcRequestBuilders
                .post("/api/books/save")
                .content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

}