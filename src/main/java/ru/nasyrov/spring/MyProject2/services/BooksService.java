package ru.nasyrov.spring.MyProject2.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nasyrov.spring.MyProject2.models.Book;
import ru.nasyrov.spring.MyProject2.models.Person;
import ru.nasyrov.spring.MyProject2.repositories.BooksRepository;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(int page, int booksPerPage, boolean sortByYear) {
        if (sortByYear == true)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear == true)
            return booksRepository.findAll(Sort.by("year"));
        else return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBooks = booksRepository.findById(id);
        return foundBooks.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book book = booksRepository.findById(id).stream().findAny().orElse(null);
        book.setId(id);
        book.setDateByTakenBook(new Date());
        book.setOwner(selectedPerson);
        booksRepository.save(book);
    }

    @Transactional
    public void release(int id) {
        Book book = booksRepository.findById(id).stream().findAny().orElse(null);
        book.setId(id);
        book.setOwner(null);
        book.setDateByTakenBook(null);
        booksRepository.save(book);

    }

    public Optional<Person> getBookOwner(int id) {
        Book book = booksRepository.findById(id).stream().findAny().orElse(null);
        return Optional.ofNullable(book.getOwner());
    }

    public List<Book> searchByFirstWord(String firstWords) {
        return booksRepository.findByBookNameStartingWith(firstWords);
    }
    public boolean expirationBook(Book book) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(book.getDateByTakenBook());
        calendar.add(Calendar.DAY_OF_MONTH,10);
        Date expirationDate = calendar.getTime();
        Date currentDate = new Date();
        if (currentDate.after(expirationDate)){
            book.setExpiration(true);
            return true;
        }else
            book.setExpiration(false);
            return false;
    }



}
///////////////Логика проверки книги на просрочку не выкидывать!!

//           Calendar calendar = Calendar.getInstance();
//           calendar.setTime(book.getDateByTakenBook());
//           calendar.add(Calendar.DAY_OF_MONTH,10);
//           Date date = calendar.getTime();
//           if (book.getDateByTakenBook().before(date)==true){
//               book.setExpiration(true);
//               return book.isExpiration();
//           }
//           else return false;

