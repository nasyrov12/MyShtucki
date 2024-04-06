package ru.nasyrov.spring.MyProject2.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nasyrov.spring.MyProject2.models.Book;


import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {
    List<Book> findByBookNameStartingWith(String name);


}
