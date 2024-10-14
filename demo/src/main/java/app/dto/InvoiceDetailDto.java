
package app.dto;

import app.model.Invoice;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class InvoiceDetailDto{
    private long id;  
    private InvoiceDto invoiceid;
    private int item;
    private String description;
    private double amount;
}
