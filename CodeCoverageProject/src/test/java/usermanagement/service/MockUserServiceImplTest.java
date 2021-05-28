package usermanagement.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import usermanagement.entity.Person;
import usermanagement.exception.UserNotFoundException;
import usermanagement.repository.PersonRepository;

@RunWith(MockitoJUnitRunner.class)
public class MockUserServiceImplTest {

	private static final String ALI = "Ali";
	private static final String TEST_COMPANY = "Test";
	private Person person = new Person();
	@Mock
	private PersonRepository personDao;

	@InjectMocks
	private UserServiceImpl testClass;

	@Mock
	private TransformService transformer;

	private User user = new User();

	private User user2 = new User();
	private Person person2 = new Person();

	@Test
	public void findById_found() {
		doReturn(person).when(personDao).findOne(Integer.valueOf(1));
		doReturn(user).when(transformer).toUserDomain(person);

		User user = testClass.findById(Integer.valueOf(1));
		assertEquals(ALI, user.getFirstName());
	}

	@Test 
	public void findById_not_found_default_user() {
		doReturn(null).when(personDao).findOne( Matchers.any(Integer.class));
		 
		doReturn(user).when(transformer).toUserDomain(Matchers.any(Person.class));
		
		User default_user = testClass.findById(Integer.valueOf(1));
		assertNotNull(default_user);
		 
	}


	@Test
	public void searchByCompanyName_found() {
		List<Person> persons = new ArrayList<>();
		persons.add(person);
		doReturn(persons).when(personDao).findByCompany(TEST_COMPANY);
		doReturn(user).when(transformer).toUserDomain(person);

		List<User> users = testClass.searchByCompanyName(TEST_COMPANY);
		assertEquals(1, users.size());
		assertEquals(ALI, users.get(0).getFirstName());
	}

	@Test
	public void searchByCompanyName_not_found() {
		List<Person> persons = new ArrayList<>();
		doReturn(persons).when(personDao).findByCompany(TEST_COMPANY);
		doReturn(user).when(transformer).toUserDomain(person);

		List<User> users = testClass.searchByCompanyName(TEST_COMPANY);
		assertTrue(users.isEmpty());
	}

	@Test
	public void deleteById_is_done_by_dao_delete() {
		doNothing().when(personDao).delete(Matchers.any(Integer.class));

		testClass.deleteById(Integer.valueOf(1));

		verify(personDao, times(1)).delete(Integer.valueOf(1));
		;
	}

	@Test
	public void same_person(){
		User user2 = new User();
		assertTrue(user.equals(user2));

		user2.setUserId(110);
		assertFalse(user.equals(user2));

	}

	@Before
	public void setup() {
		person.setfName(ALI);
		user.setFirstName(ALI);

		person2.setfName("Mohammad");
		person2.setlName("Jaberi");
		person2.setPersonId(3);

		user2.setFirstName("Mohammad");
		user2.setLastName("Jaberi");
		user2.setUserId(3);
	}


	@Test
	public void save_user() {
		testClass.save(user2);
		verify(personDao, times(1)).save(transformer.toUserEntity(user2));
	}

	@Test
	public void transformtoUserDomain(){
		User other= new TransformService().toUserDomain(person2);
		assertEquals(user2, other);
		assertNotEquals(user2, user);
	}

	@Test
	public void UserNotFoundException() {
		doReturn(null).when(personDao).findOne( Matchers.any(Integer.class));
		doReturn(user2).when(transformer).toUserDomain(Matchers.any(Person.class));
		try {
			User default_user = testClass.findById_old(Integer.valueOf(3));
		} catch (RuntimeException expected) {
			assertTrue(expected instanceof UserNotFoundException);
			assertEquals(((UserNotFoundException) expected).getUserId(),Integer.valueOf(3));

		}


	}


}
