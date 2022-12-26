package ayaz.bro.library.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="clients")
public class Client  {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotEmpty(message="name should not be empty")
    @Size(min=2,max=30,message="name size must be between 2 and 30")
    private String name;

    @Column
    @NotEmpty(message="password should not be empty")
    @Size(min=2,max=30,message="password size must be between 2 and 30")
    private String password;
    @Column
    private String role;

    @Column
    @Pattern(regexp = "((8|\\+7)-?)?\\(?\\d{3}\\)?-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}",
            message="please, enter your phone in real format")
    private String phone;

    @Column(name="image_url")
    @URL(message="please, enter true url")
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name="shopping_basket",
            joinColumns = @JoinColumn(name="client_id"),
            inverseJoinColumns = @JoinColumn(name="book_id"))
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
