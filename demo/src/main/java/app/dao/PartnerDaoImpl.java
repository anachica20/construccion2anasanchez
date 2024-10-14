package app.dao;

import app.dao.interfaces.PartnerDao;
import app.dao.repository.PartnerRepository;
import app.dto.PartnerDto;
import app.dto.UserDto;
import app.helpers.Helper;
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

public class PartnerDaoImpl implements PartnerDao {

    @Autowired
    PartnerRepository partnerRepository;

    @Override
    public boolean existsByUserId(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        partnerRepository.existsByUserId(user);
        return true;
    }

    @Override
    public void createPartner(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        partnerRepository.save(partner);
    }

    @Override
    public void deletePartner(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        partnerRepository.delete(partner);
    }

    @Override
    public PartnerDto findByUserId(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        Partner partner = partnerRepository.findByUserId(user);
        if (partner != null ) { 
        return Helper.parse(partner);
        }
        return null;
    }

    @Override
    public ArrayList<PartnerDto> findByTypeSubscription(String type) throws Exception {
        List<Partner> partnerPending = partnerRepository.findByTypeSubscription(type);
        ArrayList<PartnerDto> partnersDto = new ArrayList<PartnerDto>();
        for (Partner partner : partnerPending) {
            partnersDto.add(Helper.parse(partner));
        }
        return partnersDto;
    }

}
