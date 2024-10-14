package app.dao.interfaces;

import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.UserDto;
import java.util.ArrayList;

public interface GuestDao {
    	public boolean existById(GuestDto guestDto) throws Exception;
	public void createPerson(GuestDto guestDto) throws Exception;
	public void deletePerson(GuestDto guestDto) throws Exception;
	public GuestDto findById(GuestDto guestDto) throws Exception;
        public GuestDto findByUserId(UserDto userDto);
        public ArrayList<GuestDto> findByPartnerId(PartnerDto partnerId, String status)throws Exception;
}

