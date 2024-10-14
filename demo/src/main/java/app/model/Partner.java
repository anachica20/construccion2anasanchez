
package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="partner")
@Getter
@Setter
@NoArgsConstructor

public class Partner {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    
    @JoinColumn(name="userid")
    @OneToOne
    private User userId;
    
    @Column(name="amount")
    private double availableFunds;
    
    @Column(name="type")
    private String typeSubscription;
    
    @Column(name="creationdate")
    private Date creationDate;
}
