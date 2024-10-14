
package app.controller;

import app.controllers.validator.UserValidator;
import app.dto.UserDto;
import app.services.interfaces.LoginService;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
@Controller
@Getter
@Setter

public class LoginController implements ControllerInterface{
        
        @Autowired
        private UserValidator userValidator;
        @Autowired
	private LoginService service;
        
	private static final String MENU = "ingrese la opcion que desea: \n 1. para iniciar sesion. \n 2. para detener la ejecucion.";    
        private Map<String,ControllerInterface> roles;
    
	public LoginController(AdminController adminController, PartnerController partnerController,GuestController guestController ) {
        this.roles = new HashMap<String, ControllerInterface>();
        roles.put("Socio", partnerController);
        roles.put("Invitado", guestController);
        roles.put("Admin", adminController);
    }
        
    @Override
       public void session() throws Exception {
       boolean session = true;
       while (session) {
       session = menu();
       }
    }
    
    private boolean menu() throws Exception {
    try {
        System.out.println(MENU);
        int option = Utils.getReader().nextInt();
        return options(option);
    } catch (Exception e) {
        System.out.println("Ingrese una opcion correcta");
         Utils.getReader().nextLine(); 
        return true;
    }
}
    
  private boolean options(int option) throws Exception {
        switch (option) {
            case 1: {
                this.login();
                return true;
            }
            case 2: {
                System.out.println("se detiene el programa");
                return false;
            }
            default: {
                System.out.println("ingrese una opcion valida");
                return true;
            }
        }
    }  
    
    private void login() throws Exception {
    try {
        System.out.println("Ingrese el usuario");
        String userName = Utils.getReader().next();
        userValidator.validUserName(userName);  

        System.out.println("Ingrese la contraseña");
        String password = Utils.getReader().next();
        userValidator.validPassword(password);  

        UserDto userDto = new UserDto();
        userDto.setPassword(password);
        userDto.setUserName(userName);

        this.service.login(userDto); 

        if (roles.get(userDto.getRole()) == null) {
            throw new Exception("Rol inválido para el usuario " + userDto.getUserName());
        }

        roles.get(userDto.getRole()).session();

    } catch (Exception e) {
       
        System.out.println("Error en el login: " + e.getMessage());
    }
}

}
