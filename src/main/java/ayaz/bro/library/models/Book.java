package ayaz.bro.library.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @NotEmpty(message="name should not be empty")
    @Size(min=2,max=30,message="name size must be between 2 and 30")
    private String name;
    @Column
    @Min(value = 0,message = "please, enter correct year")
    @Max(value = 2023,message = "please, enter correct year")
    private int year;
    @ManyToOne
    @JoinColumn(name="author_id",referencedColumnName = "id")
    private Author author;

    @Column(name="image_url")
    @NotEmpty(message="url should not be empty")
    @URL(message="enter real URL")
    private String imageUrl;

    @Column
    private int amount;
    @ManyToMany(mappedBy = "books")
    private List<Client> clients;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Author getAuthor() {return author;}

    public void setAuthor(Author author) {
        this.author = author;
    }
}
