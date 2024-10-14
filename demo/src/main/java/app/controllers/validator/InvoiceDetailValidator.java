
package app.controllers.validator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component

public class InvoiceDetailValidator extends CommonsValidator {
	public void validItems(String items)throws Exception {
		super.isValidString("El id del detalle de la factura es", items);
	}
	
	public double validAmount(String amount) throws Exception{
		return super.isValidDouble("el monto de la factura ", amount);
	}
	
}
