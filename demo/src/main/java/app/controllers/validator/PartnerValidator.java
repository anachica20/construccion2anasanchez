
package app.controllers.validator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component

public class PartnerValidator extends CommonsValidator {
    
	public void validName(String name) throws Exception{
		super.isValidString("el nombre del socio ", name);
	}
	
	public long validDocument(String document) throws Exception{
		return super.isValidLong("la cedula del socio ", document);
	}
	
	public int validCelphone(String age) throws Exception{
		return super.isValidInteger("la edad del socio ", age);
	}
	
}
