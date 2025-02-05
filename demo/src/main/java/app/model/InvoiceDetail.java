
package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="INVOICEDETAIL")
@Getter
@Setter
@NoArgsConstructor

public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;  
    @JoinColumn(name = "invoiceid")
    @ManyToOne 
    private Invoice invoiceid;
    @Column(name="item")
    private int item;
    @Column(name="description")
    private String description;
    @Column(name="amount")
    private double amount;
    
}
