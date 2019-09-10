package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;


@Service
@Transactional
public class CustomerService {

	//Managed Repository -----
	
	@Autowired
	private CustomerRepository customerRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Customer create(UserAccount ua){
		Customer res = new Customer();
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Customer> findAll(){
		return customerRepository.findAll();
	}
	
	public Customer findOne(int Id){
		return customerRepository.findOne(Id);
	}
	
	public Customer save(Customer a){
		
		Customer saved = customerRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Customer a){
		customerRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Customer getPrincipal(){
		return this.customerRepository.findByPrincipal(LoginService.getPrincipal());
	}

}