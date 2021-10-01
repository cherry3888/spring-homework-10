package ru.cherry.springhomework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cherry.springhomework.dao.*;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Genre;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Book getById(Long id) {
        Optional<Book> bookO =  bookRepository.findById(id);
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book addBook(String title, String authorName, String genreName) {
        Author author;
        Optional<Author> authorO = authorRepository.findByName(authorName);
        if (authorO.isEmpty()) {
            author = authorRepository.save(new Author(authorName));
        } else {
            author = authorO.get();
        }
        Genre genre;
        Optional<Genre> genreO = genreRepository.findByName(genreName);
        if (genreO.isEmpty()) {
            genre = genreRepository.save(new Genre(genreName));
        } else {
            genre = genreO.get();
        }
        Optional<Book> bookO = bookRepository.findBookByTitleAndAuthor_IdAndGenre_Id(title, author.getId(), genre.getId());
        if (bookO.isEmpty()) {
              return bookRepository.save(new Book(title, author, genre));
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findBook(String title) {
        return bookRepository.findBooksByTitle(title);
    }

    @Transactional
    @Override
    public Book editBook(Long id, String newTitle) {
        Optional<Book> bookO = bookRepository.findById((id));
        if (bookO.isPresent()) {
            Book book = bookO.get();
            book.setTitle(newTitle);
            return bookRepository.save(book);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteBook(Long id) {
        Optional<Book> bookO = bookRepository.findById(id);
        if (bookO.isPresent()) {
            bookRepository.delete(bookO.get());
            return true;
        } else {
            return false;
        }
    }
}
