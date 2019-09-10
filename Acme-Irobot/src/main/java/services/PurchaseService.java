package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import repositories.PurchaseRepository;
import domain.Purchase;
import domain.Irobot;


@Service
@Transactional
public class PurchaseService {

	//Managed Repository -----
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	//Supporting Services -----
	
	@Autowired
	private Validator validator;

	//Simple CRUD methods -----
	public Purchase create(){
		Purchase res = new Purchase();
		res.setMoment(new Date(System.currentTimeMillis()-1000));
		return res;
	}
	
	public Collection<Purchase> findAll(){
		return purchaseRepository.findAll();
	}
	
	public Purchase findOne(int Id){
		return purchaseRepository.findOne(Id);
	}
	
	public Purchase save(Purchase a){
		return purchaseRepository.saveAndFlush(a);
	}
	
	public void delete(Purchase a){
		purchaseRepository.delete(a);
	}
	
	//Other business methods -----
	

	
}