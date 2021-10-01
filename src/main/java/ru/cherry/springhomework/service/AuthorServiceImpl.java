package ru.cherry.springhomework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cherry.springhomework.dao.AuthorRepository;
import ru.cherry.springhomework.domain.Author;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    final private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional
    @Override
    public Author addAuthor(String name) {
        return authorRepository.save(new Author(name));
    }

    @Transactional(readOnly = true)
    @Override
    public Author getAuthor(String name) {
        Optional<Author> authorO = authorRepository.findByName(name);
        return authorO.orElse(null);
    }

    @Transactional
    @Override
    public Author editAuthor(Author author) {
        Optional<Author> authorO = authorRepository.findById(author.getId());
        if (authorO.isPresent()) {
            return authorRepository.save(author);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteAuthor(Long id) {
        Optional<Author> authorO = authorRepository.findById(id);
        if (authorO.isPresent()) {
            authorRepository.delete(authorO.get());
            return true;
        }
        return false;
    }

}
