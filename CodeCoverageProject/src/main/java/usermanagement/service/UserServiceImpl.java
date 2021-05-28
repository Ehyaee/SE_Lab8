package usermanagement.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import usermanagement.entity.Person;
import usermanagement.exception.UserNotFoundException;
import usermanagement.repository.PersonRepository;

@Component
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private PersonRepository personDao;

	@Autowired
	private TransformService transformer;

	@Override
	public void deleteById(Integer personId) {
		personDao.delete(personId);
	}

	//@Override
	public User findById_old(Integer personId) {
		Person found = personDao.findOne(personId);

		if (found == null) {
			throw new UserNotFoundException("not found user", personId);
		}
		return transformer.toUserDomain(found);
	}
	
 
	@Override
	public User findById(Integer personId) {
		Person found = personDao.findOne(personId);

		if (found == null) {
			Person defaultPerson = new Person();
			defaultPerson.setfName("default");
			defaultPerson.setlName("System");
			defaultPerson.setCompanyName("Default");
			found = defaultPerson;
		}
		return transformer.toUserDomain(found);

	}

	@Override
	public User save(User user) {
		Person saved = personDao.save(transformer.toUserEntity(user));
		return transformer.toUserDomain(saved);
	}

	@Override
	public List<User> searchByCompanyName(String companyName) {
		List<Person> persons = personDao.findByCompany(companyName);
		List<User> users = new ArrayList<>();
		for (Person person : persons) {
			users.add(transformer.toUserDomain(person));
		}
		return users;
	}
}
