package ayaz.bro.library.repositories;

import ayaz.bro.library.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
    Optional<Client> findByName(String name);
}
