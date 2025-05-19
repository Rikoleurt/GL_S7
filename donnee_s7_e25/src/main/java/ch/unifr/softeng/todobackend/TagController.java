package ch.unifr.softeng.todobackend;

/*
 * Quentin Nater - Backend - GL - 2025
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping(value = "/tags")
public class TagController {
    private final TagRepository repository;

    public TagController(TagRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns a list of all tags.
     *
     * @return a collection of all tags.
     */
    @GetMapping
    public Collection<Tag> getAllTag() {
        return repository.findAll();
    }

    /**
     * Creates and returns a new tag.
     *
     * @param newTag the tag to create.
     * @return the created tag.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(@RequestBody Tag newTag) {
        return repository.save(newTag);
    }

    /**
     * Deletes all tags.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllTag() {
        repository.deleteAll();
    }

    /**
     * Returns a tag by its ID.
     *
     * @param id the ID of the tag.
     * @return the tag with the given ID.
     * @throws ResponseStatusException if the tag does not exist (404 Not Found).
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Tag getTag(@PathVariable("id") Long id) throws ResponseStatusException {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag does not exist!"));
    }

    /**
     * Deletes a tag by its ID.
     *
     * @param id the ID of the tag to delete.
     * @throws ResponseStatusException if the tag does not exist (404 Not Found).
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable("id") Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag does not exist!");
        }
        repository.deleteById(id);
    }

    /**
     * Returns all todos associated with a given tag ID.
     *
     * @param id the ID of the tag.
     * @return a collection of todos linked to the tag.
     * @throws ResponseStatusException if the tag does not exist (404 Not Found).
     */
    @RequestMapping(value = "/{tag-id}/todos/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Collection<Todo> getTodos(@PathVariable("tag-id") Long id) throws ResponseStatusException {
        Tag tag = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag does not exist!"));
        return tag.getTodos();
    }

    /**
     * Updates a tag's attributes by its ID.
     *
     * @param id the ID of the tag to update.
     * @param updatedTag the tag object containing updated fields.
     * @return the updated tag.
     * @throws ResponseStatusException if the tag does not exist (404 Not Found).
     */
    @PatchMapping("/{id}")
    public Tag updateTag(@PathVariable("id") Long id, @RequestBody Tag updatedTag) {
        return repository.findById(id)
                .map(tag -> {
                    tag.merge(updatedTag);
                    return repository.save(tag);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag does not exist!"));
    }
}
