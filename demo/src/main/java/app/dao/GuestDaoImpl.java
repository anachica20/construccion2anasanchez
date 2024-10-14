
package app.dao;
import app.dao.interfaces.GuestDao;
import app.dao.repository.GuestRepository;
import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Guest;
import app.model.Partner;
import app.model.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service

public class GuestDaoImpl implements GuestDao{
    @Autowired
    GuestRepository guestRepository;
        
    @Override
    public boolean existById(GuestDto guestDto) throws Exception {
        return guestRepository.existsById(guestDto.getId());
    }

    @Override
    public void createPerson(GuestDto guestDto) throws Exception {
        System.out.println(guestDto.getId());
        Guest guest = Helper.parse(guestDto);
        guestRepository.save(guest);  
    }

    @Override
    public void deletePerson(GuestDto guestDto) throws Exception {
        Guest guest = Helper.parse(guestDto);
        guestRepository.delete(guest);     
    }

    @Override
    public GuestDto findById(GuestDto guestDto) throws Exception {
        Guest guest = guestRepository.findById(guestDto.getId());
        return Helper.parse(guest);      
    }

    @Override
    public GuestDto findByUserId(UserDto userDto) {
        User user = Helper.parse(userDto);
        Guest guest = guestRepository.findByUserId(user);
        
        if (guest != null) {
        return Helper.parse(guest);
        } 
        return null;
    }
    
    public ArrayList<GuestDto> findByPartnerId(PartnerDto partnerId, String status) throws Exception {
        Partner partner = Helper.parse(partnerId);
        List <Guest> guests = guestRepository.findByPartnerId(partner);
        List<GuestDto> guestDto = new ArrayList();
        for (Guest guest : guests) {
            if (status.equalsIgnoreCase(guest.getStatus())) {
                guestDto.add(Helper.parse(guest));
            }
        }
        return (ArrayList<GuestDto>) guestDto;
    }
}