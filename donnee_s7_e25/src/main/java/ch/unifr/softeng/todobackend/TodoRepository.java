package ch.unifr.softeng.todobackend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Quentin Nater - Backend - GL - 2025
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Todo findByTitle(String title);
}
