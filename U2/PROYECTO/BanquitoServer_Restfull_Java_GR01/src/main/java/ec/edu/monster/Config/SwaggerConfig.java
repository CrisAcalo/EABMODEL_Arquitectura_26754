package ec.edu.monster.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de Swagger/OpenAPI para documentación de la API
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI banquitoOpenAPI() {
        Server server = new Server();
        server.setUrl("/BanquitoServer_Restfull_Java_GR01");
        server.setDescription("Servidor Payara - Desarrollo");

        Contact contact = new Contact();
        contact.setName("Equipo de Desarrollo GR01");
        contact.setEmail("desarrollo@monster.edu.ec");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("BanQuito REST API - Sistema de Créditos")
                .version("1.0.0")
                .description("API RESTful para gestión de créditos de electrodomésticos.\n\n" +
                        "**Servicios disponibles:**\n" +
                        "- WS1: Validar si un cliente es sujeto de crédito\n" +
                        "- WS2: Obtener monto máximo de crédito\n" +
                        "- WS3: Otorgar crédito\n" +
                        "- WS4: Obtener tabla de amortización\n\n" +
                        "Migración del servicio SOAP .NET original a REST Java.")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
