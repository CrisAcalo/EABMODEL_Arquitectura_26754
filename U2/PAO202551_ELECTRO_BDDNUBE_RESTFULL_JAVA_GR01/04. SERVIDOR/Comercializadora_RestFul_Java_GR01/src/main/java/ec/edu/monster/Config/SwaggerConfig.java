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
 * Configuración de Swagger/OpenAPI
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI comercializadoraOpenAPI() {
        Server server = new Server();
        server.setUrl("/Comercializadora_RestFul_Java_GR01");
        server.setDescription("Servidor Payara - Desarrollo");

        Contact contact = new Contact();
        contact.setName("Equipo de Desarrollo GR01");
        contact.setEmail("desarrollo@monster.edu.ec");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Comercializadora REST API")
                .version("1.0.0")
                .description("API RESTful para gestión de productos y facturación de electrodomésticos.\n\n" +
                        "**Servicios disponibles:**\n" +
                        "- **Productos**: CRUD completo + filtros por categoría y precio\n" +
                        "- **Facturación**: Cálculo, generación y consulta de facturas\n\n" +
                        "**Reglas de negocio:**\n" +
                        "- Pago EFECTIVO: 33% de descuento\n" +
                        "- Pago CRÉDITO: Sin descuento (requiere NumeroCredito de BanQuito)\n\n" +
                        "Migración del servicio SOAP .NET original a REST Java.")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
