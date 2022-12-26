package ayaz.bro.library.controllers;

import ayaz.bro.library.models.Client;
import ayaz.bro.library.models.ClientValidator;
import ayaz.bro.library.security.ClientDetails;
import ayaz.bro.library.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final ClientValidator clientValidator;

    @Autowired
    public ClientController(ClientService clientService, ClientValidator clientValidator) {
        this.clientService = clientService;
        this.clientValidator = clientValidator;
    }

    @GetMapping("/{id}")
    public String showClient(@PathVariable("id") Integer id, Model model){
        Client client = clientService.findById(id);
        model.addAttribute("client", client);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails=(ClientDetails) authentication.getPrincipal();
        model.addAttribute("realClient",clientDetails);
        return "client/client";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public String showClients(Model model) {
        model.addAttribute("clients",clientService.all());
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails=(ClientDetails) authentication.getPrincipal();
        model.addAttribute("realClient",clientDetails);
        return "client/client-list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") int id) {
        clientService.deleteById(id);
        return "redirect:/client/all";
    }


    @GetMapping("/update/{id}")
    public String updateAuthor(@PathVariable("id") int id, Model model) {
        Client client=clientService.findById(id);
        model.addAttribute("client",client);
        return "client/client-update";
    }

    @PostMapping("/updateClient/{id}")
    public String saveAuthor(@PathVariable("id") int id, @Valid Client client, BindingResult bindingResult) {
        clientValidator.validate(client,bindingResult);
        if(bindingResult.hasErrors())
            return "client/client-update";
        Client client1 = clientService.findById(id);
        client1.setImageUrl(client.getImageUrl());
        client1.setName(client.getName());
        client1.setPhone(client.getPhone());
        client1.setPassword(client.getPassword());
        client1.setRole("ROLE_USER");
        clientService.save(client1);
        String ref="redirect:/client/"+id;
        return ref;
    }

    @GetMapping("/new")
    public String createNewClient(Model model) {
        Client client=new Client();
        model.addAttribute("client",client);
        return "client/client-new";
    }

    @PostMapping("/newClient")
    public String createClient(@Valid Client client,BindingResult bindingResult) {
        clientValidator.validate(client,bindingResult);
        if(bindingResult.hasErrors())
            return "client/client-new";
        client.setRole("ROLE_USER");
        clientService.save(client);
        return "redirect:/";
    }
}