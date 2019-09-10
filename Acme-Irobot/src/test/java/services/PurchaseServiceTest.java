package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.Irobot;
import domain.Purchase;
import domain.Scientist;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class PurchaseServiceTest extends AbstractTest {

	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private ScientistService scientistService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private IrobotService irobotService;

	
	//	Se comprueba que solo los scientist pueden crear los purchases.
	@Test
	public void driverCreatePurchase(){
		
		final Object testingData[][] = {{"customer1", null},
										{"customer2", null},
										{"scientist1", NullPointerException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreatePurchase((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreatePurchase(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			Purchase p = this.purchaseService.create();
			CreditCard c = customerService.getPrincipal().getCreditCard();
			p.setCustomer(customerService.getPrincipal());
			p.setCVV(c.getCVV());
			p.setExpirationDate(c.getExpirationDate());
			p.setHolder(c.getHolder());
			p.setIrobot((Irobot) irobotService.findAll().toArray()[0]);
			p.setMake(c.getMake());
			p.setMoment(new Date(System.currentTimeMillis()-1000));
			p.setNumber(c.getNumber());
			purchaseService.save(p);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}	
}