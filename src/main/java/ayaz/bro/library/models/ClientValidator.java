package ayaz.bro.library.models;

import ayaz.bro.library.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ClientValidator implements Validator {
    private final ClientService clientService;

    @Autowired
    public ClientValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client=(Client) target;
        List<Client> list=clientService.all();
        boolean t=true;
        for(int i=0;i<list.size() && t;i++) {
            if(list.get(i).getName().equals(client.getName()))
                t=false;
        }
        if(!t)
            errors.rejectValue("name","", "This name is already taken");
    }
}
