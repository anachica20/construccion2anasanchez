
package app.services.interfaces;

import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;

public interface PartnerService {
    public boolean existsByUserId(UserDto userDto) throws Exception;
	public void createPartner(PartnerDto partnerDto) throws Exception;
	public void deletePartner(PartnerDto partnerDto) throws Exception;
	public PartnerDto findByUserId(UserDto userDto) throws Exception;
        public boolean userValidation(String userName, String password) throws Exception;
        public void createGuest(GuestDto guestDto) throws Exception;
        public void addFunds(double fund)throws Exception;
        public void consumption(double valor, int tipoPago) throws Exception;
        public void pageInvoices() throws Exception ;
        public void inactivateGuests() throws Exception;

    public void activateGuests() throws Exception;

    public void changeTypeSubscription() throws Exception;
    public boolean getQuotasPartner() throws Exception;
     public void viewDetailInvoices() throws Exception;

}
