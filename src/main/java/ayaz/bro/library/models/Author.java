package ayaz.bro.library.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @NotEmpty(message="name should not be empty")
    @Size(min=2,max=30,message="name size must be between 2 and 30")
    private String name;
    @Column
    @NotEmpty(message="surname should not be empty")
    @Size(min=2,max=30,message="surname size must be between 2 and 30")
    String surname;
    public Author(){};
    @OneToMany(mappedBy="author")
    List<Book> books;

    @Column(name="image_url")
    @NotEmpty(message="write umage url, please")
    @URL(message="write umage url, please")
    String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
