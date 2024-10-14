
package app.controllers.validator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component

public class PersonValidator extends CommonsValidator {
    
	public void validName(String name) throws Exception{
		super.isValidString("el nombre de la persona ", name);
	}
	
	public int validDocument(String document) throws Exception{
		return super.isValidInteger("la cedula de la persona ", document);
	}
	
	public int validAge(String age) throws Exception{
		return super.isValidInteger("la edad de la persona ", age);
	}	
}
