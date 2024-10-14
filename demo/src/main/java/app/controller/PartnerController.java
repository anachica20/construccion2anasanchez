package app.controller;

import app.controllers.validator.InvoiceValidator;
import app.controllers.validator.PersonValidator;
import app.controllers.validator.UserValidator;
import app.dto.GuestDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.services.interfaces.PartnerService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Setter
@Getter
@NoArgsConstructor

public class PartnerController implements ControllerInterface {
    
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private InvoiceValidator invoiceValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private PartnerService service;

    
    private static final String MENU = "ingrese la opcion que desea ejecutar: \n"
            + "\n 1. para cambiar tipo de suscripcion. "
            + "\n 2. para incrementar fondos. "
            + "\n 3. para registrar un invitado. "
            + "\n 4. para activar un invitado. "
            + "\n 5. para desactivar un invitado."
            + "\n 6. para pagar facturas."
            + "\n 7. para consumo de servicios."
            + "\n 8. para ver historial de facturas."
            + "\n 9. para cerrar sesion. \n";

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
    //1. ver facturas 2.ver lista de invitados (validar si son necesarios)
    private boolean options(int option) throws Exception {
        switch (option) {
            case 1: {
                this.changeTypeSubscription();
                return true;
            }
            case 2: {
                this.addFunds();
                return true;
            }
            case 3: {
                this.createGuest();
                return true;
            }
            case 4: {
                this.activateGuests();
                return true;
            }
            case 5: {
                this.inactivateGuests();
                return true;
            }
            case 6: {
                this.pageInvoices();
                return true;
            }
            case 7: {
                this.consumption();
                return true;
            }
            case 8: {
                this.viewDetailInvoices();
                return true;
            }
            case 9: {
                System.out.println("Se ha cerrado sesion");
                return false;
            }
            default: {
                System.out.println("ingrese una opcion valida");
                return true;
            }
        }
    }
    
    private void changeTypeSubscription() throws Exception {
        this.service.changeTypeSubscription();
    }

    private void addFunds() throws Exception {
        System.out.println("¿Cuanto dinero desea aumentar?");
        String funds = Utils.getReader().next();
        double fund = invoiceValidator.validAmount(funds);
        this.service.addFunds(fund);
    }

    private void createGuest() throws Exception {

        if (this.service.getQuotasPartner() == true) {

            System.out.println("ingrese el nombre del invitado: ");
            String name = Utils.getReader().next();
            personValidator.validName(name);

            System.out.println("ingrese número documento: ");
            int document = personValidator.validDocument(Utils.getReader().next());

            System.out.println("ingrese el usuario del invitado: ");
            String userName = Utils.getReader().next();
            userValidator.validUserName(userName);

            System.out.println("ingrese la contraseña del invitado: ");
            String password = Utils.getReader().next();
            userValidator.validPassword(password);

            System.out.println("ingrese el numero de celular del invitado: ");
            int celphone = personValidator.validAge(Utils.getReader().next());

            PersonDto personDto = new PersonDto();
            personDto.setName(name);
            personDto.setDocument(document);
            personDto.setCelphoneNumber(celphone);

            UserDto userDto = new UserDto();
            userDto.setPersonId(personDto);
            userDto.setUserName(userName);
            userDto.setPassword(password);
            userDto.setRole("Invitado");

            GuestDto guestDto = new GuestDto();
            guestDto.setUserId(userDto);
            guestDto.setStatus("inactive");

            this.service.createGuest(guestDto);
            System.out.println("se ha creado el invitado exitosamente");
        } else {
            System.out.println("No tiene cupos disponibles para crear nuevos invitados");
        }

    }
    
    private void activateGuests() throws Exception {
        this.service.activateGuests();
    }
    
    private void inactivateGuests() throws Exception {
        this.service.inactivateGuests();     
    }
    
    private void pageInvoices() throws Exception {    
        System.out.println("\n Esta a punto de pagar sus facturas pendientes...");
        this.service.pageInvoices();
    }
    
    private void viewDetailInvoices() throws Exception {
        this.service.viewDetailInvoices();
    }
    
    private void consumption() throws Exception {       
    System.out.println("Cuál es el valor consumido:");
    double valor = Double.parseDouble(Utils.getReader().next());

    System.out.println("Seleccione su metodo de pago:  \n 1. Para pago inmediato  \n 2. Para pago pendiente");
    int tipoPago = Utils.getReader().nextInt();

    this.service.consumption(valor, tipoPago);
    }
}
