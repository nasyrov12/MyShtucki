package ru.nasyrov.spring.MyProject2.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * @author Neil Alishev
 */
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "age")
    private int age;
//    @Column(name = "taken_date")
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
//    @Transient
//    private Date dateByTakenBook;
    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        if (name!=null){
            return name;
        }
        else {
            return "";
        }
    }



    public Person() {

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    public Date getDateByTakenBook() {
//        return dateByTakenBook;
//    }
//
//    public void setDateByTakenBook(Date dateByTakenBook) {
//        this.dateByTakenBook = dateByTakenBook;
//    }
}
