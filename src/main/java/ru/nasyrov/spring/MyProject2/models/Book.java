package ru.nasyrov.spring.MyProject2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 300, message = "Название книги должно состоять из 1-300 символов")
    @Column(name = "book_name")
    private String bookName;
    @Column(name = "author")
    private String author;
    @Column(name = "year")
    private int year;
    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person owner;
    @Column(name = "date_by_book_was_taken")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateByTakenBook;
    @Transient
    private boolean expiration;
    public Book(String bookName, String author, int year) {
        this.bookName = bookName;
        this.author = author;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String name) {
        this.bookName = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getDateByTakenBook() {
        return dateByTakenBook;
    }

    public void setDateByTakenBook(Date dateByTakenBook) {
        this.dateByTakenBook = dateByTakenBook;
    }

    public boolean isExpiration() {
        return expiration;
    }

    public void setExpiration(boolean expiration) {
        this.expiration = expiration;
    }
}
