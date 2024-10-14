
package app.controller;
import app.controllers.validator.GuestValidator;
import app.services.interfaces.GuestService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Setter
@Getter
@NoArgsConstructor

public class GuestController implements ControllerInterface {

    @Autowired
    public GuestValidator guestValidator;
    @Autowired
    public GuestService service;
            
    private static final String MENU = "ingrese la opcion que desea ejecutar: "
        + "\n 1. Para cambiarse a rol Socio. "
        + "\n 2. Para hacer un consumo. "
        + "\n 3. Para cerrar sesion. ";
    
    @Override
    public void session() throws Exception {
        boolean session = true;
            while (session) {
                session = menu();
            }
    }
    
    private boolean menu() {
        try {
            System.out.println(MENU);
            int option = Utils.getReader().nextInt();
            return options(option);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            for (StackTraceElement el : e.getStackTrace()){
                System.out.println(el.toString());
            }
            return true;
            }
        }
    
        private boolean options(int option) throws Exception {
        switch (option) {
            case 1: {
                this.becomeaPartner();
                return true;
            }
            case 2: {
                this.consumption();
                return true;
            }
            case 3: {
                System.out.println("Se ha cerrado sesion");
                return false;
            }
            default: {
                System.out.println("ingrese una opcion valida");
                return true;
            }
        }
    }
    
    private void becomeaPartner() throws Exception {
        this.service.becomeaPartner();
    } 
    
    private void consumption() throws Exception {
        
        System.out.println("Cuál es el valor consumido:");
        double valor = Double.parseDouble(Utils.getReader().next());

        System.out.println("Su factura será pagada por el Socio que lo invitó");

        this.service.consumption(valor, 2);
    }
    
    public void viewDetailInvoices() throws Exception {
        this.service.viewDetailInvoices();
    }
    
}
