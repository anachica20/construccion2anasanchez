
package app.dto;

import java.sql.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PartnerDto {
    private long id;
    private UserDto userId;
    private double availableFunds;
    private String typeSubscription;
    private Date creationDate;
    private List<InvoiceDto> invoices;
}
