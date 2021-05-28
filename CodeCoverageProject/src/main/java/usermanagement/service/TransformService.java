package usermanagement.service;

import org.springframework.stereotype.Component;

import usermanagement.entity.Person;

@Component
public class TransformService {

	public User toUserDomain(final Person person) {
		User user = new User();
		user.setCompanyName(person.getCompanyName());
		user.setFirstName(person.getfName());
		user.setLastName(person.getlName());
		user.setUserId(person.getPersonId());
		return user;
	}

	public Person toUserEntity(final User user) {
		Person person = new Person();
		person.setCompanyName(user.getCompanyName());
		person.setfName(user.getFirstName());
		person.setlName(user.getLastName());
		if (user.getUserId() != null) {
			person.setPersonId(user.getUserId());
		}
		return person;
	}
}
