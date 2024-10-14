
package app.dao.repository;

import app.model.Guest;
import app.model.Partner;
import app.model.User;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository <Guest,Long> {

    public Guest findById(long id);

    public boolean existsById(User idUser);

    public Guest findByUserId(User user);
    
    public ArrayList<Guest> findByPartnerId(Partner partnerId);
    
}
