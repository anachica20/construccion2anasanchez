
package app.dao.repository;


import app.model.Invoice;
import app.model.InvoiceDetail;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceDetailRepository extends JpaRepository <InvoiceDetail,Long> {
   public ArrayList<InvoiceDetail> findByInvoiceid(Invoice invoice);
                                        
}
