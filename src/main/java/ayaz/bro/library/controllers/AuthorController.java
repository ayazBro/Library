package ayaz.bro.library.controllers;

import ayaz.bro.library.models.Author;
import ayaz.bro.library.security.ClientDetails;
import ayaz.bro.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping("/all")
    public String showAuthors(Model model) {
        model.addAttribute("authors",authorService.all());
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails=(ClientDetails) authentication.getPrincipal();
        model.addAttribute("client",clientDetails);
        return "author/author-list";
    }

    @GetMapping("/all/{id}")
    public String showAuthorsPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("id",id);
        model.addAttribute("kol",authorService.all().size());
        model.addAttribute("authors",authorService.allByPage(id));
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails=(ClientDetails) authentication.getPrincipal();
        model.addAttribute("client",clientDetails);
        return "author/author-pagination";
    }

    @GetMapping("/{id}")
    public String showAuthor(@PathVariable("id") int id, Model model) {
        Author author = authorService.findById(id);
        model.addAttribute("author", author);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails=(ClientDetails) authentication.getPrincipal();
        model.addAttribute("client",clientDetails);
        return "author/author";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") int id) {
        authorService.deleteById(id);
        return "redirect:/author/all/0";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String createNewAuthor(Model model) {
        Author author=new Author();
        model.addAttribute("author",author);
        return "author/author-new";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/newAuthor")
    public String createAuthor(@Valid Author author, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "author/author-new";
        authorService.save(author);
        return "redirect:/author/all/0";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update/{id}")
    public String updateAuthor(@PathVariable("id") int id, Model model) {
        Author author=authorService.findById(id);
        model.addAttribute("author",author);
        return "author/author-update";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/updateAuthor/{id}")
    public String saveAuthor(@PathVariable("id") int id,  @Valid Author author,BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "author/author-update";
        Author author1 = authorService.findById(id);
        author1.setBooks(author.getBooks());
        author1.setImageUrl(author.getImageUrl());
        author1.setName(author.getName());
        author1.setSurname(author.getSurname());
        authorService.save(author1);
        return "redirect:/author/all/0";
    }
}