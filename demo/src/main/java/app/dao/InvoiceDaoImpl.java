package app.dao;

import app.dao.interfaces.InvoiceDao;
import app.dao.repository.InvoiceRepository;
import app.dto.InvoiceDto;
import app.helpers.Helper;
import app.model.Invoice;
import app.model.Partner;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service

public class InvoiceDaoImpl implements InvoiceDao {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public InvoiceDto findById(InvoiceDto invoiceDto) throws Exception {
        Invoice invoice = invoiceRepository.findById(invoiceDto.getInvoiceId());
        return Helper.parse(invoice);
    }

    @Override
    public boolean findByPersonId(InvoiceDto invoiceDto) throws Exception {
    long personId = invoiceDto.getPersonId();
    return invoiceRepository.findByPersonId((int)personId) != null; 
    }


    @Override
    public void createInvoice(InvoiceDto invoiceDto) throws Exception {
        Invoice invoice = Helper.parse(invoiceDto);
        invoiceRepository.save(invoice);
        invoiceDto.setInvoiceId(invoice.getInvoiceId());
    }

    @Override
    public ArrayList<InvoiceDto> findByPartnerId(long partnerId, String status) {
        List<Invoice> invoices = invoiceRepository.findByPartnerId(partnerId);
        List<InvoiceDto> invoicesDto = new ArrayList();
        for (Invoice invoice : invoices) {
             if(status.equalsIgnoreCase("todo")){
                invoicesDto.add(Helper.parse(invoice));
             }
             else if (status.equalsIgnoreCase(invoice.getStatus())) {
                invoicesDto.add(Helper.parse(invoice));
            }
        }
        return (ArrayList<InvoiceDto>) invoicesDto;
    }

    @Override
    public void deleteInvoice(InvoiceDto invoiceDto) throws Exception {
        Invoice invoice = Helper.parse(invoiceDto);
        invoiceRepository.delete(invoice);   
    }
    
    @Override
    public ArrayList<InvoiceDto> findByPersonId(int personId, String status) throws Exception {
        List<Invoice> invoices = invoiceRepository.findByPersonId(personId);
        List<InvoiceDto> invoicesDto = new ArrayList();
        
        for (Invoice invoice : invoices) {
            if(status.equalsIgnoreCase("todo")){
                 invoicesDto.add(Helper.parse(invoice));
            }else if (status.equalsIgnoreCase(invoice.getStatus())) {
                invoicesDto.add(Helper.parse(invoice));
            }
        }
        return (ArrayList<InvoiceDto>) invoicesDto;
    }
}
