
package app.dto;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

public class InvoiceDto{
    private long invoiceId;
    private long personId;
    private long partnerId;
    private Date dateOfGeneration;
    private double totalValue;
    private String status;
}

