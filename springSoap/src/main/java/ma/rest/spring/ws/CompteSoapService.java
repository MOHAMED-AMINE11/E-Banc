package ma.rest.spring.ws;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import ma.rest.spring.entities.Compte;
import ma.rest.spring.entities.TypeCompte;
import ma.rest.spring.repositories.CompteRepository;

@Service // Indique que cette classe est un service Spring
@WebService(
    serviceName = "BanqueWS",                 // Nom du service SOAP
    targetNamespace = "http://ws.spring.rest.ma/" // Namespace du service
)
public class CompteSoapService {

    @Autowired
    private CompteRepository compteRepository;

    @WebMethod(operationName = "getComptes") // Définit le nom de l'opération SOAP
    public List<Compte> getComptes() {
        return compteRepository.findAll();
    }

    @WebMethod(operationName = "getCompteById")
    public Compte getCompteById(@WebParam(name = "id") Long id) {
        return compteRepository.findById(id).orElse(null);
    }

    @WebMethod(operationName = "createCompte")
    public Compte createCompte(
        @WebParam(name = "solde") double solde,
        @WebParam(name = "type") TypeCompte type
    ) {
        Compte compte = new Compte(solde, new Date(), type);
        return compteRepository.save(compte);
    }

    @WebMethod(operationName = "deleteCompte")
    public boolean deleteCompte(@WebParam(name = "id") Long id) {
        if (compteRepository.existsById(id)) {
            compteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
