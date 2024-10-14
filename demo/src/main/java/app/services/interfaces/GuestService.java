package app.services.interfaces;

import app.dto.PersonDto;


public interface GuestService {
    
    public boolean existsByDocument(PersonDto personDto) throws Exception;

    public void createPerson(PersonDto personDto) throws Exception;

    public void deletePerson(PersonDto personDto) throws Exception;

    public PersonDto findByDocument(PersonDto personDto) throws Exception;

    public void becomeaPartner()throws Exception;
    public void consumption(double valor, int tipoPago) throws Exception;
    
    public void viewDetailInvoices() throws Exception;
}
