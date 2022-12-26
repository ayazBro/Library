package ayaz.bro.library.services;

import ayaz.bro.library.models.Basket;
import ayaz.bro.library.repositories.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    private final BasketRepository basketRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public void save(Basket basket) {
        basketRepository.save(basket);
    }

    public void back(int clientId, int bookId) {
        Basket basket=basketRepository.findFirstByClientIdAndBookId(clientId,bookId).get();
        basketRepository.delete(basket);
    }
}