package ru.cherry.springhomework.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.dto.BookDto;
import ru.cherry.springhomework.service.AuthorService;
import ru.cherry.springhomework.service.BookService;
import ru.cherry.springhomework.service.CommentService;
import ru.cherry.springhomework.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookRestController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    public BookRestController(BookService bookService, AuthorService authorService, GenreService genreService, CommentService commentService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/books/view/{id}")
    public BookDto getBook(@PathVariable Long id) {
        Book book = bookService.getById(id);
        BookDto bookDto = new BookDto();
        if (null != book) {
            bookDto = BookDto.toDto(book);
        }
        return bookDto;
    }

    @GetMapping("/api/books/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        Book book = bookService.getById(id);
        if (null != book) {
            bookService.deleteBook(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/books/save")
    public ResponseEntity<String> saveBook(@RequestBody BookDto bookDto) {
        Book book = bookService.getById(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/books/add")
    public ResponseEntity<String> addBook(@RequestBody BookDto bookDto) {
        bookService.addBook(bookDto.getTitle(), bookDto.getAuthorName(), bookDto.getGenreName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
