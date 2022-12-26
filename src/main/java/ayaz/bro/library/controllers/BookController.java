package ayaz.bro.library.controllers;

import ayaz.bro.library.models.Basket;
import ayaz.bro.library.models.Book;
import ayaz.bro.library.security.ClientDetails;
import ayaz.bro.library.services.AuthorService;
import ayaz.bro.library.services.BasketService;
import ayaz.bro.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final BasketService basketService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService,BasketService basketService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.basketService=basketService;
    }

    @GetMapping("/all")
    public String showBooks( Model model) {
        model.addAttribute("books",bookService.all());
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails=(ClientDetails) authentication.getPrincipal();
        model.addAttribute("client",clientDetails);
        return "book/book-list";
    }

    @GetMapping("/all/{id}")
    public String showBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("id",id);
        model.addAttribute("kol",bookService.all().size());
        model.addAttribute("books",bookService.allByPage(id));
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails=(ClientDetails) authentication.getPrincipal();
        model.addAttribute("client",clientDetails);
        return "book/book-pagination";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") Integer id, Model model){
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails=(ClientDetails) authentication.getPrincipal();
        model.addAttribute("client",clientDetails);
        return "book/book";
    }

    @GetMapping("/buy/{id}")
    public String buyBook(@PathVariable("id") int id,Model model) {
        model.addAttribute("book",bookService.findById(id));
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails=(ClientDetails) authentication.getPrincipal();
        model.addAttribute("client",clientDetails);
        Basket basket=new Basket();
        basket.setClientId(clientDetails.getClient().getId());
        basket.setBookId(id);
        try {
            bookService.takeBook(id);
            basketService.save(basket);
        }
        catch (Exception e) {
            return "book/error";
        }
        return "book/buy";
    }

    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable("id") int id,Model model) {
        model.addAttribute("book",bookService.findById(id));
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails=(ClientDetails) authentication.getPrincipal();
        model.addAttribute("client",clientDetails);
        basketService.back(clientDetails.getClient().getId(),id);
        bookService.returnBook(id);
        return "redirect:/book/all/0";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.deleteById(id);
        return "redirect:/book/all/0";
    }

    @GetMapping("/new")
    public String createNewBook(Model model) {
        Book book=new Book();
        model.addAttribute("book",book);
        model.addAttribute("authors",authorService.all());
        return "book/book-new";
    }

    @PostMapping("/newBook")
    public String createBook(@Valid Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "redirect:/book/new";
        bookService.save(book);
        return "redirect:/book/all/0";
    }

    @GetMapping("/update/{id}")
    public String updateBook(@PathVariable("id") int id, Model model){
        Book book=bookService.findById(id);
        model.addAttribute("book",book);
        model.addAttribute("authors",authorService.all());
        return "book/book-update";
    }

    @PostMapping("/updateBook/{id}")
    public String saveAuthor(@PathVariable("id") int id,  @Valid Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "redirect:/book/update/"+id;
        Book book1 = bookService.findById(id);
        book1.setAuthor(book.getAuthor());
        book1.setImageUrl(book.getImageUrl());
        book1.setName(book.getName());
        book1.setYear(book.getYear());
        bookService.save(book1);
        return "redirect:/book/all/0";
    }
}