package ayaz.bro.library.services;

import ayaz.bro.library.models.Author;
import ayaz.bro.library.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    public List<Author> all() {
        return authorRepository.findAll();
    }
    public Author findById(int id) {
        return authorRepository.findById(id).get();
    }
    public void deleteById(int id) {
        authorRepository.deleteById(id);
    }
    public void save(Author author) {
         authorRepository.save(author);
    }
    public List<Author> allByPage(int num) {
        return authorRepository.findAll(PageRequest.of(num,5)).getContent();
    }

}