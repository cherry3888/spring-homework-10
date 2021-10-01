package ru.cherry.springhomework.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Comment;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class BookDto {

    private Long id;
    private String title;
    private String authorName;
    private String genreName;
    private List<String> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public static BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthorName(book.getAuthor().getName());
        dto.setGenreName(book.getGenre().getName());
        if (!CollectionUtils.isEmpty(book.getComments())) {
            dto.setComments(book.getComments().stream().map(Comment::getContent).collect(Collectors.toList()));
        }
        return dto;
    }

}
