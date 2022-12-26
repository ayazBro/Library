package ayaz.bro.library.models;

import javax.persistence.*;

@Entity
@Table(name="shopping_basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="client_id")
    private int clientId;
    @Column(name="book_id")
    private int bookId;

    public int getClientId() {
        return clientId;
    }
    public Basket() {
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
