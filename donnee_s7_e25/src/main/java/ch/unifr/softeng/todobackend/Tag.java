package ch.unifr.softeng.todobackend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/*
 * Quentin Nater - Backend - GL - 2025
 */
@Entity
public class Tag {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String title;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnoreProperties("tags")
    //@JsonBackReference
    private Set<Todo> todos = new HashSet<>();

    @JsonCreator
    public Tag(@JsonProperty("title") String title) {
        this.title = title;
    }

    protected Tag() {
    }

    public String getTitle() {
        return this.title;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return linkTo(TagController.class).slash(this.getId()).withSelfRel().getHref();
    }

    Tag merge(Tag updatedTag) {
        title = Optional.ofNullable(updatedTag.title).orElse(title);
        return this;
    }

    public Collection<Todo> getTodos() {
        return todos;
    }
}
