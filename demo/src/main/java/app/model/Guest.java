
package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="guest")
@Getter
@Setter
@NoArgsConstructor

public class Guest {        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;
    @JoinColumn(name="partnerid")
    @ManyToOne
    private Partner partnerId;
    @JoinColumn(name="userid")
    @OneToOne
    private User userId;
    @Column(name="status")
    private String status;
}
