package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CreditCardRepository;
import domain.CreditCard;


@Service
@Transactional
public class CreditCardService {

	//Managed Repository -----
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public CreditCard create(){
		CreditCard res = new CreditCard();
		
		return res;
	}
	
	public Collection<CreditCard> findAll(){
		return creditCardRepository.findAll();
	}
	
	public CreditCard findOne(int Id){
		return creditCardRepository.findOne(Id);
	}
	
	public CreditCard save(CreditCard a){
		
		CreditCard saved = creditCardRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(CreditCard a){
		creditCardRepository.delete(a);
	}
	
	//Other business methods -----

}