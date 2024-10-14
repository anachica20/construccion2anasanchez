package app.services;

import app.controller.Utils;
import app.dao.interfaces.GuestDao;
import app.dao.interfaces.InvoiceDao;
import app.dao.interfaces.PartnerDao;
import app.dao.interfaces.PersonDao;
import app.dao.interfaces.UserDao;
import app.dto.GuestDto;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.services.interfaces.AdminService;
import app.services.interfaces.GuestService;
import app.services.interfaces.LoginService;
import app.services.interfaces.PartnerService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.dao.interfaces.InvoiceDetailDao;

@Getter
@Setter
@Service
@NoArgsConstructor
public class ClubService implements AdminService, GuestService, LoginService, PartnerService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private PartnerDao partnerDao;
    @Autowired
    private GuestDao guestDao;
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private InvoiceDetailDao invoiceDetailDao;
    
    public static UserDto user;

    private final int TOTAL_QUOTAS = 3;

    public void createUser(UserDto userDto) throws Exception {
        if (userDto == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        boolean existingUser = userDao.existsByUserName(userDto);
        if (existingUser) {
            throw new Exception("El usuario" + userDto.getUserName() + "ya existe");
        }
        this.createPerson(userDto.getPersonId());
        this.userDao.createUser(userDto);
    }

    @Override
    public void createPartner(PartnerDto partnerDto) throws Exception {
        UserDto userDto = partnerDto.getUserId();
        if (userDto == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo en PartnerDto");
        }
        this.createUser(userDto);
        this.partnerDao.createPartner(partnerDto);
    }

    @Override
    public void createGuest(GuestDto guestDto) throws Exception {
        UserDto userDto = guestDto.getUserId();
        this.createUser(userDto);
        PartnerDto partnerDto = partnerDao.findByUserId(user);
        guestDto.setPartnerId(partnerDto);
        this.guestDao.createPerson(guestDto);
    }

    @Override
    public boolean getQuotasPartner() throws Exception {
        PartnerDto partnerDto = partnerDao.findByUserId(user);

        List<GuestDto> quotasGuest = new ArrayList<GuestDto>();

        if (partnerDto.getTypeSubscription().equalsIgnoreCase("regular")) {
            quotasGuest = guestDao.findByPartnerId(partnerDto, "active");
            if (quotasGuest.size() < 3) {
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean existsByDocument(PersonDto personDto) throws Exception {
        return true;
    }

    @Override
    public void createPerson(PersonDto personDto) throws Exception {
        if (personDto == null) {
            throw new IllegalArgumentException("El objeto personDto no puede ser nulo");
        }

        if (this.personDao.existsByDocument(personDto)) {
            throw new Exception("Ya existe una persona con ese documento");
        }

        this.personDao.createPerson(personDto);
    }

    @Override
    public void deletePerson(PersonDto personDto) throws Exception {

    }

    @Override
    public PersonDto findByDocument(PersonDto personDto) throws Exception {
        throw new Exception("ya existe un usuario con ese user name");
    }

    @Override
    public void login(UserDto userDto) throws Exception {
        UserDto validateDto = userDao.findByUserName(userDto);
        if (validateDto == null) {
            throw new Exception("no existe usuario registrado");
        }
        if (!userDto.getPassword().equals(validateDto.getPassword())) {
            throw new Exception("usuario o contraseña incorrecto");
        }
        userDto.setRole(validateDto.getRole());
        user = validateDto;
    }

    @Override
    public void logout() {

    }

    @Override
    public boolean existsByUserId(UserDto userDto) throws Exception {
        throw new Exception("ya existe un usuario con ese user name");
    }

    @Override
    public void deletePartner(PartnerDto partnerDto) throws Exception {

    }

    @Override
    public PartnerDto findByUserId(UserDto userDto) throws Exception {
        throw new Exception("ya existe un usuario con ese user name");
    }

    @Override
    public boolean userValidation(String userName, String password) throws Exception {
        return true;
    }

    @Override
    public void viewInvoiceHistory(UserDto userDto) throws Exception {

    }

    @Override
    public void addFunds(double fund) throws Exception {
        PartnerDto partner = partnerDao.findByUserId(user);
        double total = fund + partner.getAvailableFunds();
        if (partner.getTypeSubscription().equals("regular") && total > 1000000) {
            throw new Exception("ha excedido el limite de fondos");
        }

        if (partner.getTypeSubscription().equals("vip") && total > 5000000) {
            throw new Exception("ha excedido el limite de fondos");
        }
        partner.setAvailableFunds(total);
        partnerDao.createPartner(partner);
        System.out.println("Se han incrementado los fondos");
        this.automaticPayment(partner);
    }

    private void automaticPayment(PartnerDto partner) throws Exception {
        List<InvoiceDto> invoices = invoiceDao.findByPartnerId(partner.getId(), "sin pagar");
        invoices.sort((invoice1, invoice2) -> invoice1.getDateOfGeneration().compareTo(invoice2.getDateOfGeneration()));

        for (InvoiceDto invoice : invoices) {
            if (invoice.getTotalValue() <= partner.getAvailableFunds()) {
                partner.setAvailableFunds(partner.getAvailableFunds() - invoice.getTotalValue());
                partnerDao.createPartner(partner);
                invoice.setStatus("pagada");
                invoiceDao.createInvoice(invoice);
                System.out.println("La factura con ID " + invoice.getInvoiceId() + " ha sido pagada de forma automatica por el incremento ");
            }
        }
    }

    @Override
    public void consumption(double valor, int tipoPago) throws Exception {
        
        System.out.println("Ingrese el Item de su compra: \n"
                + "1. Para bienestar \n"
                + "2. Para Alimentación \n"
                + "3. Otros \n");
        
        int item = Utils.getReader().nextInt();
        System.out.println("Ingrese la descripción de su consumo");
        Utils.getReader().nextLine(); 
        String descripcion = Utils.getReader().nextLine();

        LocalDate today = LocalDate.now();
        Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        PartnerDto partner = partnerDao.findByUserId(user);
        GuestDto guest = guestDao.findByUserId(user);

        int partnerId = 0;

        if (partner != null) {
            partnerId = (int) partner.getId();
        } else {
            partnerId = (int) guest.getPartnerId().getId();
        }

        InvoiceDto invoice = new InvoiceDto();
        InvoiceDetailDto invoiceDetail = new InvoiceDetailDto();
        invoice.setPersonId((int) user.getPersonId().getId());
        invoice.setPartnerId(partnerId);
        invoice.setDateOfGeneration(date);
        invoice.setTotalValue(valor);
        
        invoiceDetail.setItem(item);
        invoiceDetail.setDescription(descripcion);
        invoiceDetail.setAmount(valor);

        if (tipoPago == 2) {
            invoice.setStatus("sin pagar");
            invoiceDao.createInvoice(invoice);
            invoiceDetail.setInvoiceid(invoice);
            invoiceDetailDao.createInvoice(invoiceDetail);
            
            System.out.println("Se ha creado su factura como pendiente");
        } else if (tipoPago == 1) {
            if (partner.getAvailableFunds() < valor) {
                System.out.println("Saldo insufiencie, registre su pago como pendiente o incremente fondos");
            } else {
                partner.setAvailableFunds(partner.getAvailableFunds() - valor);
                partnerDao.createPartner(partner);
                invoice.setStatus("pagada");
                invoiceDao.createInvoice(invoice);
       
                invoiceDetail.setInvoiceid(invoice);
                
                invoiceDetailDao.createInvoice(invoiceDetail);
                System.out.println("El consumo ha sido liquido por el partner de usuario " + user.getUserName());
            }

        }
    }

    @Override
    public void pageInvoices() throws Exception {
        PartnerDto partner = partnerDao.findByUserId(user);
        List<InvoiceDto> invoices = invoiceDao.findByPartnerId(partner.getId(), "sin pagar");
        invoices.sort((invoice1, invoice2) -> invoice1.getDateOfGeneration().compareTo(invoice2.getDateOfGeneration()));
        int opcion;
        do {
            if (invoices.isEmpty()) {
                System.out.println(" No tiene facturas pendientes para pagar \n");
                break;
            }
            System.out.println("1. Para pagar \n2. Para salir ");
            opcion = Utils.getReader().nextInt();

            if (opcion == 1) {
                System.out.println("Listado de facturas pendientes: ");

                for (InvoiceDto invoice : invoices) {
                    System.out.println(invoice.getInvoiceId() + " " + invoice.getTotalValue() + " " + invoice.getStatus());
                }
                System.out.println("Ingrese el ID de la factura a pagar");
                int invoiceId = Utils.getReader().nextInt();
                for (InvoiceDto invoice : invoices) {

                    if (invoiceId == (int) invoice.getInvoiceId()) {
                        if (partner.getAvailableFunds() >= invoice.getTotalValue()) {
                            partner.setAvailableFunds(partner.getAvailableFunds() - invoice.getTotalValue());
                            invoice.setStatus("pagada");
                            invoiceDao.createInvoice(invoice);
                            partnerDao.createPartner(partner);
                            System.out.println("Factura con ID " + invoiceId + " , pagada con exito");
                            invoices.remove(invoice);
                            break;
                        } else {
                            System.out.println("Fondos insuficientes, sus fondos disponibles son: " + partner.getAvailableFunds());
                        }

                    }

                }
            }
        } while (opcion != 2);

    }

    @Override
    public void inactivateGuests() throws Exception {

        PartnerDto partnerDto = partnerDao.findByUserId(user);
        List<GuestDto> quotasGuest = guestDao.findByPartnerId(partnerDto, "active");
        if (quotasGuest.size() > 0) {
            System.out.println("Estos son sus invitados activos: ");
            for (GuestDto quota : quotasGuest) {
                System.out.println("ID: " + quota.getId() + " Nombre: " + quota.getUserId().getPersonId().getName());
            }

            System.out.println("Por favor ingrese el id de la persona que va a desactivar");
            int id = Utils.getReader().nextInt();
            for (GuestDto quota : quotasGuest) {
                if (quota.getId() == (long) id) {
                    quota.setStatus("inactive");
                    guestDao.createPerson(quota);
                    break;
                }
            }
            System.out.println("Su invitado ha sido inactivado");
        } else {
            System.out.println("No tienes invitados para desactivar");
        }

    }

    @Override
    public void activateGuests() throws Exception {
        if (this.getQuotasPartner() == true) {
            PartnerDto partnerDto = partnerDao.findByUserId(user);
            List<GuestDto> quotasGuest = guestDao.findByPartnerId(partnerDto, "inactive");
            System.out.println("Estos son sus invitados pendientes por aprobacion: ");
            for (GuestDto quota : quotasGuest) {
                System.out.println("ID: " + quota.getId() + " Nombre: " + quota.getUserId().getPersonId().getName());
            }

            System.out.println("Por favor ingrese el id de la persona que va a aprobar");
            int id = Utils.getReader().nextInt();
            for (GuestDto quota : quotasGuest) {
                if (quota.getId() == (long) id) {
                    quota.setStatus("active");
                    guestDao.createPerson(quota);
                    break;
                }
            }
            System.out.println("Su invitado ha sido aprobado");
        } else {
            System.out.println("No tiene mas cupos para nuevos invitados");
        }

    }

    @Override
    public void changeTypeSubscription() throws Exception {
        PartnerDto partner = partnerDao.findByUserId(user);
        List<PartnerDto> partners = partnerDao.findByTypeSubscription("vip");
        if (partners.size() >= 3) {
            System.out.println("No hay capacidad para socios VIP");
        } else {
            partner.setTypeSubscription("pendiente");
            partnerDao.createPartner(partner);
            System.out.println("La solicitud ha sido enviada, a espera de conformacion del Administrador...");
        }
    }

    @Override
    public void vipPromotion() throws Exception {
        List<PartnerDto> partnersPending = partnerDao.findByTypeSubscription("pendiente");
        
        if (partnersPending.size() <=0) {
            System.out.println("No hay socios en estado pendiente o no quedan más cupos VIP.\n");
            return; 
        }
        
        List<PartnerDto> partnersSelec = this.getInvoicesPartner(partnersPending);
        for (PartnerDto partner : partnersSelec) {
            System.out.println("El de socio " + partner.getUserId().getPersonId().getName() + " con cedula: " + partner.getUserId().getPersonId().getDocument() + " quiere ser un socio VIP: \n ");
            System.out.println(" 1. Para aprobar \n 2. Para rechazar \n 3. Para salir");
            int option = Utils.getReader().nextInt();
            switch (option) {
                case 1: {
                    partner.setTypeSubscription("vip");
                    partnerDao.createPartner(partner);
                    System.out.println("Se ha actualizado el tipo de suscripcion a VIP");
                    break;
                }
                case 2: {
                    partner.setTypeSubscription("regular");
                    partnerDao.createPartner(partner);
                    System.out.println("No se aprobo su solicitud y su estado es regular");
                    break;

                }
                case 3: {
                    System.out.println("Se ha cerrado sesion");
                    break;

                }
                default: {
                    System.out.println("Opcion incorrecta");
                    break;
                }
            }
        }
        
        
        List<PartnerDto> rechazados = partnerDao.findByTypeSubscription("pendiente");
        for (PartnerDto p : rechazados) {
            p.setTypeSubscription("regular");
            partnerDao.createPartner(p);
        }
    }

    private List<PartnerDto> getInvoicesPartner(List<PartnerDto> partners) throws Exception {
        List<PartnerDto> partnerSelect = new ArrayList<PartnerDto>();
        int quotas = TOTAL_QUOTAS - availableQuotas();
        System.out.println(quotas);
        if (quotas > 0) {

            for (PartnerDto partner : partners) {
                partner.setInvoices(invoiceDao.findByPartnerId(partner.getId(), "sin pagar"));
            }
            
            partners.stream().sorted(Comparator.comparingDouble((PartnerDto partner)
                    -> partner.getInvoices().stream()
                            .mapToDouble(InvoiceDto::getTotalValue)
                            .sum()
            ).thenComparing(PartnerDto::getCreationDate))
                    .toList();
            try{
            for (int i = 0; i < quotas; i++) {
                if(i<partners.size()){
                    partnerSelect.add(partners.get(i));
                }
            }
            }catch(Exception e){ System.out.println(e.toString());}

        }
        return partnerSelect;
    }

    private int availableQuotas() throws Exception {
        List<PartnerDto> partners = partnerDao.findByTypeSubscription("vip");
        return partners.size();

    }

    @Override
    public void becomeaPartner() throws Exception {
        LocalDate today = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(today);

        boolean disapproved = this.getPendingGuestInvoices(user.getPersonId());
        GuestDto guestDto = guestDao.findByUserId(user);
        PartnerDto partner = partnerDao.findByUserId(user);
        if(partner!=null){
            System.out.println("Ya existe un socio para este id de usuario");
            return;
        }
        if (!disapproved) {

            guestDto.setStatus("inactivo permanentemente");
            guestDao.createPerson(guestDto);

            user.setRole("Socio");
            userDao.createUser(user);

            PartnerDto partnerDto = new PartnerDto();
            partnerDto.setUserId(user);
            partnerDto.setAvailableFunds(50000);
            partnerDto.setTypeSubscription("regular");
            partnerDto.setCreationDate(date);
            partnerDao.createPartner(partnerDto);

            System.out.println("Se ha actualizado su rol, ahora es Socio.");
        } else {
            String partherName = guestDto.getPartnerId().getUserId().getPersonId().getName();
            System.out.println("Tienes facturas sin pagar, aún no puedes pasarte a Socio. Comunicate con " + partherName + " para que se ponga al dia \n");
        }
    }

    private boolean getPendingGuestInvoices(PersonDto personDto) throws Exception {
        List<InvoiceDto> invoices = invoiceDao.findByPersonId((int) personDto.getId(), "sin pagar");
        return invoices.size() > 0;
    }

    @Override
    public void viewDetailInvoices() throws Exception {
        PartnerDto partner = partnerDao.findByUserId(user);
        List<InvoiceDto> invoices = invoiceDao.findByPartnerId((int) partner.getId(), "todo");
        List<InvoiceDetailDto> invoiceDetail = null;
        int index = 1;
        for (InvoiceDto inv : invoices) {
            System.out.println("Factura " + index + "\n Fecha de creacion " + inv.getDateOfGeneration() + "\n Status: " + inv.getStatus());
            invoiceDetail = invoiceDetailDao.findByInvoiceId(inv);
            if (invoiceDetail.size() > 0) {
                System.out.println("Detalle de su factura: ");
                for (InvoiceDetailDto invDetail : invoiceDetail) {
                    System.out.println(" Item: "+ invDetail.getItem()+ "\n Descripcion: " + invDetail.getDescription()+ "\n Monto : " + invDetail.getAmount()+"\n");
                }
            } else {
                System.out.println("Su factura no tiene detalle.. ");

            }
            System.out.println("===========================");

            index++;
        }
        if (invoices.size() <= 0) {
            System.out.println(" No tienes facturas generadas");
        }
        
    }
}
