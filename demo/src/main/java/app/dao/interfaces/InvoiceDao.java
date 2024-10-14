package app.dao.interfaces;

import app.dto.InvoiceDto;
import app.model.Invoice;
import app.model.Partner;
import java.util.ArrayList;

public interface InvoiceDao {
    public InvoiceDto findById(InvoiceDto invoiceDto) throws Exception;
    public boolean findByPersonId(InvoiceDto invoiceDto) throws Exception;
    public void createInvoice(InvoiceDto invoiceDto) throws Exception; 
    public ArrayList<InvoiceDto> findByPartnerId(long partnerId,  String status); 
    public void deleteInvoice(InvoiceDto invoiceDto) throws Exception;
    public ArrayList<InvoiceDto> findByPersonId(int personId, String status) throws Exception;
}
