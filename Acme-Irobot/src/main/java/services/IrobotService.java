package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.IrobotRepository;
import domain.Irobot;
import domain.Purchase;
import domain.Scientist;


@Service
@Transactional
public class IrobotService {

	//Managed Repository -----
	
	@Autowired
	private IrobotRepository irobotRepository;
	
	//Supporting Services -----
	
	ScientistService scientistService;
	
	//Simple CRUD methods -----
	
	public Irobot create(Scientist s){
		Irobot res = new Irobot();
		res.setScientist(s);
		System.out.println(generateTicker(s));
		res.setTicker(generateTicker(s));
		res.setPurchases(new ArrayList<Purchase>());
		
		return res;
	}
	
	public Collection<Irobot> findAll(){
		return irobotRepository.findAll();
	}
	
	public Irobot findOne(int Id){
		return irobotRepository.findOne(Id);
	}
	
	public Irobot save(Irobot a){
		Irobot saved = irobotRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Irobot a){
		irobotRepository.delete(a);
	}
	
	//Other business methods -----
	String generateTicker(Scientist s){
		String res = "";
		Scientist scientist = s;
		String surnames = scientist.getSurnames();
		if(surnames.length() >4){
			res = res + surnames.substring(0, 4).toUpperCase();
		}else{
			int numberOfX = 4-surnames.length();
			res += surnames.toUpperCase();
			for (int i = 0; i < numberOfX; i++) {
				res += "X";
			}
		}
		res += "-";
		Random r = new Random();
		Integer rndNumber = r.nextInt(9999);
		res += rndNumber.toString();
		
		return res;
	}

	public Collection<Irobot> findByKeyword(String keyword) {
		return this.irobotRepository.findByKeyword(keyword);
	}

	public Collection<Irobot> findByScientist(Scientist scientist) {
		return this.irobotRepository.findByScientist(scientist);
	}
	
	public Integer getMinimumIrobotPerScientist(){
		return this.irobotRepository.getMinIrobotsPerScientist();
	}
	
	public Integer getMaximumIrobotPerScientist(){
		return this.irobotRepository.getMaxIrobotsPerScientist();
	}
	
	public Double getAverageIrobotPerScientist(){
		return this.irobotRepository.getAvgIrobotsPerScientist();
	}
	
	public Double getStdevIrobotPerScientist(){
		return this.irobotRepository.getStdevIrobotsPerScientist();
	}
	
	public Collection<Irobot> top10BestSelling(){
		List<Irobot> res = new ArrayList<>(this.irobotRepository.Top10SellingIrobots());
		Collections.reverse(res);
		if(res.size() > 10){
			res = res.subList(0, 10);
		}
		return res;
	}

}