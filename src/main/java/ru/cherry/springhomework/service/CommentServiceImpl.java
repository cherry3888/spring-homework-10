package ru.cherry.springhomework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cherry.springhomework.dao.BookRepository;
import ru.cherry.springhomework.dao.CommentRepository;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public Comment save(Long bookId, String content) {
        Optional<Book> bookO = bookRepository.findById(bookId);
        if (bookO.isPresent()) {
            Comment comment = new Comment(bookO.get(), content);
            return commentRepository.save(comment);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getByBookId(Long id) {
        Optional<Book> bookO = bookRepository.findById(id);
        List<Comment> comments = new ArrayList<>();
        if (bookO.isPresent()) {
            comments = bookO.get().getComments();
        }
        return comments;
    }

}
