
package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="invoice")
@Getter
@Setter
@NoArgsConstructor

public class Invoice{   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long invoiceId;
    @Column(name="personid")
    private long personId;
    @Column(name="partnerid")
    private long partnerId;
    @Column(name="creationdate")
    private Date dateOfGeneration;
    @Column(name="amount")
    private double totalValue;
    @Column(name="status")
    private String status;  
}

