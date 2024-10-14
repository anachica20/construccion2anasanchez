
package app.dao.interfaces;

import app.dto.PartnerDto;
import app.dto.UserDto;
import java.util.ArrayList;

public interface PartnerDao {
    	public boolean existsByUserId(UserDto userDto) throws Exception;
	public void createPartner(PartnerDto partnerDto) throws Exception;
	public void deletePartner(PartnerDto partnerDto) throws Exception;
	public PartnerDto findByUserId(UserDto userDto) throws Exception;
        public ArrayList<PartnerDto> findByTypeSubscription (String type)throws Exception;
}   

