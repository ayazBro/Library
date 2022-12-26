package ayaz.bro.library.repositories;

import ayaz.bro.library.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Integer> {
    Optional<Basket> findFirstByClientIdAndBookId(int clientId,int bookId);
}
