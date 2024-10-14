package app;

import app.controller.LoginController;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Setter
@SpringBootApplication
public class ClubApplication {

    public static void main(String[] args) {
        // Iniciar el contexto de Spring
        ApplicationContext context = SpringApplication.run(ClubApplication.class, args);
        
        // Obtener el bean LoginController del contexto de Spring
        LoginController loginController = context.getBean(LoginController.class);
        
        try {
            // Llamar al método de sesión o el que desees
            loginController.session(); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
