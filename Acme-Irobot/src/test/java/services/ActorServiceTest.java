package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Admin;
import domain.Customer;
import domain.Scientist;
import forms.RegisterForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ActorServiceTest extends AbstractTest {

	
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ScientistService scientistService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private ActorService actorService;
	
	
	@Test
	public void testRegisterAdmin(){
		
		authenticate("admin");
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("admin10");
		userAccount.setPassword("admin10");		
		Admin admin = adminService.create(userAccount);
				
		admin.setName("adminName");
		admin.setSurnames("adminSurname1 adminSurname2");
		admin.setEmail("admin@email.com");
		admin.setPhoto("http://www.photo.com");
		admin.setPhone("+346542");
		
		Admin result = actorService.registerAdmin(admin);
		
		Assert.isTrue(adminService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testRegisterCustomer(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("customer10");
		userAccount.setPassword("customer10");		
		
		Customer customer = customerService.create(userAccount);
		
		customer.setName("customerName");
		customer.setSurnames("customerSurname1 customerSurname2");
		customer.setEmail("customer@email.com");
		customer.setPhoto("http://www.photo.com");
		customer.setPhone("+34612123456");
		
		RegisterForm form = new RegisterForm();
		form.setVATNumber("ESN12345678");
		
		Customer result = actorService.registerCustomer(customer, form);
		
		Assert.isTrue(customerService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectRegisterCustomer(){
		
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("customer10");
		userAccount.setPassword("customer10");		
		Customer customer = customerService.create(userAccount);
		
		customer.setName("customerName");
		customer.setSurnames("customerSurname1 customerSurname2");
		customer.setEmail("customer@email.com");
		customer.setPhoto("not a valid url");
		customer.setPhone("+34612123456");
		
		RegisterForm form = new RegisterForm();
		form.setVATNumber("ESN12345678");
		
		Customer result = actorService.registerCustomer(customer, form);
		
		Assert.isTrue(customerService.findAll().contains(result));
		
	}
	
	
	@Test
	public void testRegisterScientist(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.SCIENTIST);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("scientist10");
		userAccount.setPassword("scientist10");		
		Scientist scientist = scientistService.create(userAccount);
		
		scientist.setName("scientistName");
		scientist.setSurnames("scientistSurname1 scientistSurname2");
		scientist.setEmail("scientist@email.com");
		scientist.setPhoto("http://www.photo.com");
		scientist.setPhone("+34612123456");
		
		RegisterForm form = new RegisterForm();
		form.setVATNumber("ESN12345678");
		
		Scientist result = actorService.registerScientist(scientist, form);
		
		Assert.isTrue(scientistService.findAll().contains(result));
		
	}
	
	@Test
	public void testEditAdmin(){
		
		authenticate("admin");
		
		Admin admin = (Admin) actorService.getPrincipal();
		
		admin.setName("adminUpdatedName");
		admin.setSurnames("adminUpdatedSurname1 adminUpdatedSurname2");
		admin.setEmail("adminUpdated@email.com");
		admin.setPhoto("http://www.photoUpdated.com");
		
		Admin result = adminService.save(admin);
		
		Assert.isTrue(adminService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditAdmin(){
		
		authenticate("admin");
		
		Admin admin = (Admin) actorService.getPrincipal();
		
		admin.setName("adminUpdatedName");
		admin.setSurnames("adminUpdatedSurname");
		admin.setEmail("not a valid email");
		admin.setPhoto("not a valid photo");
		
		Admin result = adminService.save(admin);
		
		Assert.isTrue(adminService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testEditScientist(){
		
		authenticate("scientist1");
		Scientist scientist = scientistService.getPrincipal();
		
		scientist.setName("scientistUpdatedName");
		scientist.setSurnames("scientistUpdatedSurname1 scientistUpdatedSurname2");
		scientist.setEmail("scientistUpdated@email.com");
		scientist.setPhoto("http://www.photoUpdated.com");
		
		Scientist result = scientistService.save(scientist);
		
		Assert.isTrue(scientistService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditScientist(){
		
		authenticate("scientist1");
		
		Scientist scientist = scientistService.getPrincipal();
		
		scientist.setName("");
		scientist.setSurnames("");
		
		Scientist result = scientistService.save(scientist);
		
		Assert.isTrue(scientistService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testEditCustomer(){
		
		authenticate("customer1");
		
		Customer customer = customerService.getPrincipal();
		
		customer.setName("customerUpdatedName");
		customer.setSurnames("customerUpdatedSurname1 customerUpdatedSurname2");
		customer.setEmail("customerUpdated@email.com");
		customer.setPhoto("http://www.photoUpdated.com");
		
		Customer result = customerService.save(customer);
		
		Assert.isTrue(customerService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditCustomer(){
		
		authenticate("customer1");
		
		Customer customer = customerService.getPrincipal();
		
		customer.setName("");
		customer.setSurnames("");
		
		Customer result = customerService.save(customer);
		
		Assert.isTrue(customerService.findAll().contains(result));
		
		unauthenticate();
	}
}
