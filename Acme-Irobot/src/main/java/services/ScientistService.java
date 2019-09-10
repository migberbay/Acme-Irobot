package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ScientistRepository;
import security.LoginService;
import security.UserAccount;
import domain.Irobot;
import domain.Scientist;


@Service
@Transactional
public class ScientistService {

	//Managed Repository -----
	
	@Autowired
	private ScientistRepository scientistRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Scientist create(UserAccount ua){
		Scientist res = new Scientist();
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Scientist> findAll(){
		return scientistRepository.findAll();
	}
	
	public Scientist findOne(int Id){
		return scientistRepository.findOne(Id);
	}
	
	public Scientist save(Scientist a){
		
		Scientist saved = scientistRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Scientist a){
		scientistRepository.delete(a);
	}
	
	//Other business methods -----
	public Scientist getPrincipal(){
		Scientist res = this.scientistRepository.findByPrincipal(LoginService.getPrincipal());
		return res;
	}
	
	public List<Entry<Scientist, Integer>> Top10BestSelling(){
		List<Scientist> aux = this.scientistRepository.findAll();
		Map<Scientist,Integer> aux2 = new HashMap<>();
		Integer cont;
		
		for (Scientist s : aux) {
			cont = 0;
			for (Irobot i : s.getIrobots()) {
				cont += i.getPurchases().size();
			}
			aux2.put(s, cont);
		}
		
		Comparator<Entry<Scientist,Integer>> comparator = new Comparator<Entry<Scientist,Integer>>(){
	        @Override
			public int compare(Entry<Scientist,Integer> e1, Entry<Scientist,Integer> e2) {
	            return e1.getValue() - e2.getValue();
	        }
		};
		
//		negative, if this object is less than the supplied object.
//		zero, if this object is equal to the supplied object.
//		positive, if this object is greater than the supplied object.
		
		List<Entry<Scientist,Integer>> res = new ArrayList<>(aux2.entrySet());
		
		Collections.sort(res, comparator);
		Collections.reverse(res);
		if(res.size() > 10){
			res = res.subList(0, 10);
		}
		return res;
	}
	
	
	

}