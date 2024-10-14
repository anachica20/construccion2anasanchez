package app.dao;

import app.dao.interfaces.PersonDao;
import app.dao.repository.PersonRepository;
import app.dao.repository.UserRepository;
import java.sql.ResultSet;
import app.dto.PersonDto;
import app.helpers.Helper;
import app.model.Person;
import java.sql.PreparedStatement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service

public class PersonDaoImpl implements PersonDao {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean existsByDocument(PersonDto personDto) throws Exception {
        return personRepository.existsByDocument(personDto.getDocument());
    }

    @Override
    public void createPerson(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        personRepository.save(person);   
        personDto.setId(person.getId());
    }

    @Override
    public void deletePerson(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        personRepository.delete(person);
    }

    @Override
    public PersonDto findByDocument(PersonDto personDto) throws Exception {
        Person person = personRepository.findByDocument(personDto.getDocument());
        return Helper.parse(person);
    }

    @Override
    public PersonDto findbyUserId(long personId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

