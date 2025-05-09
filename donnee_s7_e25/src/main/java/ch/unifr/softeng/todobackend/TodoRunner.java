package ch.unifr.softeng.todobackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
 * Quentin Nater - Backend - GL - 2025
 */
@Component
public class TodoRunner implements CommandLineRunner {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void run(String... args) throws Exception {
        Tag t1 = new Tag("Work");
        tagRepository.save(t1);
        Tag t2 = new Tag("Social");
        tagRepository.save(t2);
        Tag t3 = new Tag("Miscellaneous");
        tagRepository.save(t3);
    }
}