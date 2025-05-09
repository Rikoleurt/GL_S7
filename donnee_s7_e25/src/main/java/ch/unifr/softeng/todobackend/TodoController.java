package ch.unifr.softeng.todobackend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/*
 * Quentin Nater - Backend - GL - 2025
 */
@RestController
@RequestMapping(value = "/todos")
public class TodoController {
    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = POST)
    public @ResponseBody
    Todo create(@RequestBody Todo newTodo) {
        return repository.save(newTodo);
    }

    @RequestMapping(method = GET)
    public @ResponseBody
    Collection<Todo> getAll() {
        return repository.findAll();
    }

    @RequestMapping(method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        repository.deleteAll();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public @ResponseBody
    Todo getOne(@PathVariable("id") Long id) throws ResponseStatusException {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
    }

    @RequestMapping(method = PATCH, value = "/{id}")
    public @ResponseBody
    Todo update(@PathVariable("id") Long id,
                @RequestBody Todo updatedTodo) throws ResponseStatusException {
        return repository.findById(id)
                .map(todo -> todo.merge(updatedTodo))
                .map(todo -> repository.save(todo))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }

    @RequestMapping(value = "/{todo-id}/tags/", method = GET)
    public @ResponseBody
    Collection<Tag> getAllTags(@PathVariable("todo-id") Long id) throws ResponseStatusException {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
        return todo.getTags();
    }

    @RequestMapping(value = "/{todo-id}/tags/", method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTags(@PathVariable("todo-id") Long id) throws ResponseStatusException {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
        todo.getTags().clear();
        repository.save(todo);
    }

    @RequestMapping(value = "/{todo-id}/tags/", method = POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    Tag addTag(@PathVariable("todo-id") Long id,
               @RequestBody Tag tag) throws ResponseStatusException {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
        todo.getTags().add(tag);
        repository.save(todo);
        return tag;
    }

    @RequestMapping(value = "/{todo-id}/tags/{tag-id}", method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeTag(@PathVariable("todo-id") Long todoId, @PathVariable("tag-id") Long tagId) throws ResponseStatusException {
        Todo todo = repository.findById(todoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
        if (!todo.getTags().removeIf(tag -> tag.getId().equals(tagId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag does not exist!");
        }
        repository.save(todo);
    }

}
