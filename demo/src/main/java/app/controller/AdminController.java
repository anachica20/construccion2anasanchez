package app.controller;

import app.controllers.validator.InvoiceValidator;
import app.controllers.validator.PersonValidator;
import app.controllers.validator.UserValidator;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.services.interfaces.AdminService;
import java.sql.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Setter
@Getter
@NoArgsConstructor
public class AdminController implements ControllerInterface {
    
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private InvoiceValidator invoiceValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private AdminService service;
    
    private static final String MENU = "ingrese la opcion que desea ejecturar: \n "
            + "1. para crear socio \n "
            + "2. para aprobar promoción a VIP \n "
            + "3. Salir \n";
    
    @Override
    public void session() throws Exception {
        boolean session = true;
        while (session){
        session = menu();
        }
    }
    
    private boolean menu() {
        try {
            System.out.print(MENU);
            int option = Utils.getReader().nextInt();
            return options(option);

        } catch (Exception e) {
            Utils.getReader().nextLine();
            return true;
        }
    }
  
    private boolean options(int option) throws Exception {
        switch (option) {
            case 1: {
                this.createPartner();
                return true;
            } 
            case 2: {
                this.vipPromotion();
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

    private void createPartner() throws Exception {
        System.out.println("ingrese número documento: ");
        int document = personValidator.validDocument(Utils.getReader().next());
        
        System.out.println("ingrese el nombre del Socio: ");
        String name = Utils.getReader().next();
        personValidator.validName(name);
        
        System.out.println("ingrese el numero de celular: ");
        int celphone = personValidator.validAge(Utils.getReader().next());
        
        System.out.println("ingrese el usuario del socio: ");
        String userName = Utils.getReader().next();
        userValidator.validUserName(userName);
        
        System.out.println("ingrese la contraseña del socio: ");
        String password = Utils.getReader().next();
        userValidator.validPassword(password);
        
        PersonDto personDto = new PersonDto();
        personDto.setName(name);
        personDto.setDocument(document);
        personDto.setCelphoneNumber(celphone);

        UserDto userDto = new UserDto();
        userDto.setPersonId(personDto);
        userDto.setUserName(userName);
        userDto.setPassword(password);
        userDto.setRole("socio");
        
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setUserId(userDto);
        partnerDto.setAvailableFunds(50000);  // Parsear el tipo de dato a double
        partnerDto.setTypeSubscription("regular");  
        partnerDto.setCreationDate(new Date(System.currentTimeMillis()));
        
        this.service.createPartner(partnerDto);
        System.out.println("se ha creado el usuario exitosamente");       
    }
    
    private void viewInvoiceHistory() throws Exception {}
    
    private void vipPromotion()throws Exception {
        try{
         this.service.vipPromotion();
        }catch(Exception e){
        }

    }        
}
