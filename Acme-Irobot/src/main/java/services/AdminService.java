package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdminRepository;
import security.UserAccount;
import domain.Admin;


@Service
@Transactional
public class AdminService {

	//Managed Repository -----
	
	@Autowired
	private AdminRepository adminRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Admin create(UserAccount ua){
		Admin res = new Admin();
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Admin> findAll(){
		return adminRepository.findAll();
	}
	
	public Admin findOne(int Id){
		return adminRepository.findOne(Id);
	}
	
	public Admin save(Admin a){
		
		Admin saved = adminRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Admin a){
		adminRepository.delete(a);
	}
	
	//Other business methods -----

}