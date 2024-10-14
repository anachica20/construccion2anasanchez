package app.dao;

import app.dao.repository.InvoiceDetailRepository;
import app.dto.InvoiceDetailDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.InvoiceDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.dao.interfaces.InvoiceDetailDao;
import app.dto.InvoiceDto;
import app.model.Invoice;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Service

public class InvoiceDetailDaoImpl implements InvoiceDetailDao {
    
    @Autowired
    InvoiceDetailRepository invoiceDetailRepository;

    @Override
    public void createInvoice(InvoiceDetailDto invoiceDetail) throws Exception {
        InvoiceDetail invoice = Helper.parse(invoiceDetail);
        invoiceDetailRepository.save(invoice);
    }

    @Override
    public ArrayList<InvoiceDetailDto> findByInvoiceId(InvoiceDto invoiceDto) throws Exception {
        Invoice invoice = Helper.parse(invoiceDto);
        List<InvoiceDetail> invoices  = invoiceDetailRepository.findByInvoiceid(invoice);
             
        List<InvoiceDetailDto> invoicesDto = new ArrayList<InvoiceDetailDto>();
        
        for (InvoiceDetail invoiceD : invoices) {
            invoicesDto.add(Helper.parse(invoiceD));
        }
        return (ArrayList<InvoiceDetailDto>) invoicesDto;
    }
}

    

