package ayaz.bro.library.services;

import ayaz.bro.library.models.Book;
import ayaz.bro.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> all() {
        return bookRepository.findAll();
    }
    public Book findById(int id) {
        return bookRepository.findById(id).orElse(new Book());
    }
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }
    public List<Book> allByPage(int num) {
        return bookRepository.findAll(PageRequest.of(num,5)).getContent();
    }
    @Transactional
    public void takeBook(int id) throws Exception {
       Book book= bookRepository.findById(id).get();
       if(book.getAmount()==0)
           throw new Exception();
       book.setAmount(book.getAmount()-1);
       bookRepository.save(book);
    }
    @Transactional
    public void returnBook(int id) {
        Book book= bookRepository.findById(id).get();
        book.setAmount(book.getAmount()+1);
        bookRepository.save(book);
    }
}