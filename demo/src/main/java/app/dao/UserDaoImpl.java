package app.dao;

import app.dao.interfaces.UserDao;
import app.dao.repository.UserRepository;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Person;
import app.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service

public class UserDaoImpl implements UserDao {
    
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean existsByUserName(UserDto userDto) throws Exception {
    return userRepository.existsByUserName(userDto.getUserName());
}


    @Override
    public void createUser(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        userRepository.save(user);
        userDto.setId(user.getId());
    }

    @Override
    public void deleteUser(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        userRepository.delete(user);
    }

    @Override
    public UserDto findByUserName(UserDto userDto) throws Exception {
        User user = userRepository.findByUserName(userDto.getUserName());
        if (user == null) {
        return null;
        }
        return Helper.parse(user);
    }

    @Override
    public UserDto findByPersonId(PersonDto personDto) {
        Person person = Helper.parse(personDto);
        User user = userRepository.findByPersonId(person);
        return Helper.parse(user);
    }

}

