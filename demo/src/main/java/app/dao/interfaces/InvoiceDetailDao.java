package app.dao.interfaces;

import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import java.util.ArrayList;

public interface InvoiceDetailDao {
public void createInvoice(InvoiceDetailDto invoiceDetail) throws Exception; 
public ArrayList<InvoiceDetailDto> findByInvoiceId(InvoiceDto invoiceDto) throws Exception;

}
