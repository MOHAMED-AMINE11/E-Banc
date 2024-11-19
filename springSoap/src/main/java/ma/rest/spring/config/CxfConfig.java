package ma.rest.spring.config;

import ma.rest.spring.ws.CompteSoapService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfig {

    @Autowired
    private CompteSoapService compteSoapService;

    @Autowired
    private Bus bus;

    @Bean
    public EndpointImpl endpoint() {
        // Vérification des dépendances avant de créer l'endpoint
        if (compteSoapService == null || bus == null) {
            throw new IllegalStateException("Le CompteSoapService ou le Bus est null. Vérifiez l'injection des dépendances.");
        }
        // Création et publication de l'endpoint SOAP
        EndpointImpl endpoint = new EndpointImpl(bus, compteSoapService);
        endpoint.publish("/ws");  // L'endpoint sera disponible à l'URL suivante : /ws
        return endpoint;
    }
}
