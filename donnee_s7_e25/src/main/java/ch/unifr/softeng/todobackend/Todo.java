package ch.unifr.softeng.todobackend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/*
 * Quentin Nater - Backend - GL - 2025
 */
@Entity
public class Todo {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String title;
    private boolean completed;
    private Integer orderNumber; // "order" is reserved in H2

    @ManyToMany
    @JsonIgnoreProperties("todos")
    private Set<Tag> tags;

    @JsonCreator
    public Todo(@JsonProperty("title") String title,
                @JsonProperty("completed") boolean completed,
                @JsonProperty("order") Integer orderNumber) {
        this.title = title;
        this.completed = completed;
        this.orderNumber = orderNumber;
    }

    protected Todo() {
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public Long getId() {
        return id;
    }

    @JsonProperty("order")
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public String getUrl() {
        return linkTo(TodoController.class).slash(this.getId()).withSelfRel().getHref();
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    Todo merge(Todo updatedTodo) {
        title = Optional.ofNullable(updatedTodo.title).orElse(title);
        completed = updatedTodo.completed;
        orderNumber = Optional.ofNullable(updatedTodo.orderNumber).orElse(orderNumber);
        return this;
    }
}
