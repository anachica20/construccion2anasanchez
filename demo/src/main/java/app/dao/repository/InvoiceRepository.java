
package app.dao.repository;

import app.dto.InvoiceDto;
import app.model.Invoice;
import app.model.Partner;
import app.model.Person;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceRepository extends JpaRepository <Invoice,Long> {
   public Invoice findById(long invoiceId);  
   public Person findByPersonId(long personId);
   public ArrayList<Invoice> findByPartnerId(long partnerId);
   public ArrayList<Invoice> findByPersonId(int personId);
}
