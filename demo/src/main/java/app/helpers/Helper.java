package app.helpers;

import app.dto.GuestDto;
import app.dto.InvoiceDetailDto;
import app.dto.PersonDto;
import app.model.Person;
import app.dto.UserDto;
import app.model.User;
import app.dto.InvoiceDto;
import app.model.Invoice;
import app.dto.PartnerDto;
import app.model.Guest;
import app.model.InvoiceDetail;
import app.model.Partner;

public abstract class Helper {

    public static PersonDto parse(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setDocument(person.getDocument());
        personDto.setCelphoneNumber(person.getCellphone());
        personDto.setName(person.getName());
        return personDto;
    }

    public static Person parse(PersonDto personDto) {
        Person person = new Person();
        person.setId(personDto.getId());
        person.setDocument(personDto.getDocument());
        person.setCellphone(personDto.getCelphoneNumber());
        person.setName(personDto.getName());
        return person;
    }

    public static UserDto parse(User user) {
        UserDto userDto = new UserDto();
        userDto.setPersonId(parse(user.getPersonId()));
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setUserName(user.getUserName());
        return userDto;
    }

    public static User parse(UserDto userDto) {        
    User user = new User();
    if (userDto != null) {
        if (userDto.getPersonId() != null) {
            user.setPersonId(parse(userDto.getPersonId()));
        }
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setUserName(userDto.getUserName());
    } else {
        throw new IllegalArgumentException("El objeto userDto no puede ser nulo");
    }
    return user;
    }

    public static InvoiceDto parse(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setInvoiceId(invoice.getInvoiceId());
        invoiceDto.setPersonId(invoice.getPersonId());
        invoiceDto.setPartnerId(invoice.getPartnerId());
        invoiceDto.setDateOfGeneration(invoice.getDateOfGeneration());
        invoiceDto.setTotalValue(invoice.getTotalValue());
        invoiceDto.setStatus(invoice.getStatus());
        return invoiceDto;
    }

    public static Invoice parse(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceDto.getInvoiceId());
        invoice.setPersonId(invoiceDto.getPersonId());
        invoice.setPartnerId(invoiceDto.getPartnerId());
        invoice.setDateOfGeneration(invoiceDto.getDateOfGeneration());
        invoice.setTotalValue(invoiceDto.getTotalValue());
        invoice.setStatus(invoiceDto.getStatus());
        return invoice;
    }

    public static PartnerDto parse(Partner partner) {
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setId(partner.getId());
        partnerDto.setUserId(parse(partner.getUserId()));
        partnerDto.setAvailableFunds(partner.getAvailableFunds());
        partnerDto.setTypeSubscription(partner.getTypeSubscription());
        partnerDto.setCreationDate(partner.getCreationDate());
        return partnerDto;
    }

    public static Partner parse(PartnerDto partnerDto) {
        Partner partner = new Partner();
        if (partnerDto.getUserId() != null){
        partner.setUserId(parse(partnerDto.getUserId()));
        }
        partner.setId(partnerDto.getId());
        partner.setAvailableFunds(partnerDto.getAvailableFunds());
        partner.setTypeSubscription(partnerDto.getTypeSubscription());
        partner.setCreationDate(partnerDto.getCreationDate());
        return partner;
    }
    
        public static Guest parse(GuestDto guestDto) {
        Guest guest = new Guest();
        if (guestDto.getPartnerId() != null) {
            guest.setPartnerId(parse(guestDto.getPartnerId()));
        }
        if (guestDto.getUserId() != null){
            guest.setUserId(parse(guestDto.getUserId()));
        }
        guest.setStatus(guestDto.getStatus());
        guest.setId(guestDto.getId());
        return guest;
    }

    public static GuestDto parse(Guest guest) {
        GuestDto guestDto = new GuestDto();
        guestDto.setPartnerId(parse(guest.getPartnerId()));
        guestDto.setUserId(parse(guest.getUserId()));
        guestDto.setStatus(guest.getStatus());
        guestDto.setId(guest.getId());
        return guestDto;
    }
    
    public static InvoiceDetail parse(InvoiceDetailDto invoiceDetailDto) {
        InvoiceDetail invoice = new InvoiceDetail();
        invoice.setAmount(invoiceDetailDto.getAmount());
        invoice.setDescription(invoiceDetailDto.getDescription());
        invoice.setId(invoiceDetailDto.getId());
        invoice.setInvoiceid(parse(invoiceDetailDto.getInvoiceid()));
        invoice.setItem(invoiceDetailDto.getItem());
        return invoice;
    }
        
    public static InvoiceDetailDto parse(InvoiceDetail invoiceDetail) {
        InvoiceDetailDto invoiceDto = new InvoiceDetailDto();
        invoiceDto.setAmount(invoiceDetail.getAmount());
        invoiceDto.setDescription(invoiceDetail.getDescription());
        invoiceDto.setId(invoiceDetail.getId());
        invoiceDto.setInvoiceid(parse(invoiceDetail.getInvoiceid()));
        invoiceDto.setItem(invoiceDetail.getItem());
        return invoiceDto;
    }
}
