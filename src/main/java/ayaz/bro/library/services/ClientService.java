package ayaz.bro.library.services;

import ayaz.bro.library.models.Client;
import ayaz.bro.library.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> all() {
        return clientRepository.findAll();
    }
    public Client findById(int id) {
        return clientRepository.findById(id).get();
    }
    public void deleteById(int id) {
        clientRepository.deleteById(id);
    }
    public void save(Client client) {clientRepository.save(client);}
}