package app.dao.repository;

import app.dto.PartnerDto;
import app.model.Partner;
import app.model.User;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository <Partner, Long> {

    public void existsByUserId(User userId);

    public Partner findByUserId(User userId);
    
    public ArrayList<Partner> findByTypeSubscription (String type)throws Exception;
}
