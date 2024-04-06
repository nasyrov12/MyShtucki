package ru.nasyrov.spring.MyProject2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nasyrov.spring.MyProject2.models.Person;
import ru.nasyrov.spring.MyProject2.services.BooksService;
import ru.nasyrov.spring.MyProject2.services.PeopleService;
import ru.nasyrov.spring.MyProject2.models.Book;

import java.util.List;
import java.util.Optional;


@Controller
    @RequestMapping("/books")
    public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }
        @GetMapping
        public String index(Model model,@RequestParam(value = "page",required = false,defaultValue = "0")Integer page,
                            @RequestParam(value = "booksperpage",required = false)Integer books_per_page,
                            @RequestParam(value = "sort_by_year",required = false,defaultValue = "false")boolean sortByYear){
        if (books_per_page==null){
            books_per_page = booksService.findAll(sortByYear).size();
            model.addAttribute("books",booksService.findAll(sortByYear));
            return "books/index";
        }else {
            model.addAttribute("books",booksService.findAll(page,books_per_page,sortByYear));
        }
            return "books/index";
        }


        @GetMapping("{id}")
        public String show(@PathVariable("id")int id,Model model,@ModelAttribute("person") Person person){
            model.addAttribute("book",booksService.findOne(id));
            Optional<Person> bookOwner = booksService.getBookOwner(id);
            if (bookOwner.isPresent()){
                model.addAttribute("owner",bookOwner.get());
            }else model.addAttribute("people",peopleService.findAll());
            return "books/show";
        }
        @GetMapping("/new")
        public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
        }
        @PostMapping()
        public String create(@ModelAttribute("book")Book book){
            booksService.save(book);
            return "redirect:/books";
        }

        @GetMapping("{id}/edit")
        public String edit(Model model,@PathVariable("id")int id){
        model.addAttribute("book",booksService.findOne(id));
        return "books/edit";
        }

        @PatchMapping("/{id}")
        public String update(@ModelAttribute("book")Book book,@PathVariable("id")int id){
        booksService.update(id,book);
        return "redirect:/books";
        }
        @DeleteMapping("/{id}")
        public String delete(@PathVariable("id")int id){
            booksService.delete(id);
            return "redirect:/books";
        }
        @PatchMapping("{id}/assign")
        public String assign(@PathVariable("id")int id, @ModelAttribute("person")Person selectedPerson){
            booksService.assign(id,selectedPerson);
            return "redirect:/books/" + id;
        }
        @PatchMapping("{id}/release")
        public String release(@PathVariable("id")int id){
        booksService.release(id);
        return "redirect:/books/"+ id;
        }

        @GetMapping("/search")
    public String search(@RequestParam(value = "first_words",required = false)String first_words,Model model){
                    if (first_words!=null){
                List<Book> books = booksService.searchByFirstWord(first_words);
                if (books.isEmpty()){
                    model.addAttribute("noResults",true);
                }else
                    model.addAttribute("books",books);
        }return "books/search";
    }


}
