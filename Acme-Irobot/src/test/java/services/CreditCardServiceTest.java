package services;

import java.util.Date;
import java.util.Random;

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
import domain.Scientist;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private ScientistService scientistService;
	

	//Comprobamos que todos puede hacerlo.
	@Test
	public void driverCreateCreditCard(){
		
		final Object testingData[][] = {{"scientist1", null},
										{"customer1", null},
										{"admin", null},
										{null, null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateCreditCard((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateCreditCard(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			CreditCard c = this.creditCardService.create();
			c.setCVV(345);
			c.setExpirationDate(new Date(System.currentTimeMillis()+24000000000L));
			c.setHolder("YO");
			c.setMake("VISA");
			Random r = new Random();
			Integer a = r.nextInt(99999999);
			Integer b = r.nextInt(99999999);
			String aux = a.toString();
			String aux2 = b.toString();
			for (int i = 0; i < 8 - aux.length(); i++) {
				aux = "0" + aux;
			}
			for (int i = 0; i < 8 - aux2.length(); i++) {
				aux2 = "0" + aux2;
			}
			
			String s = aux+aux2;
			System.out.println(s);
			c.setNumber(s);
			creditCardService.save(c);
		} catch (Throwable oops){
			System.out.println(username + " " + oops);
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	
	
	//	Se comprueba que no se puede guardar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("scientist1");

		CreditCard creditCard = creditCardService.create();
		
		creditCard.setCVV(0);
		creditCard.setExpirationDate(new Date(System.currentTimeMillis()-1000));
		creditCard.setHolder("");
		creditCard.setMake("soemthign");
		creditCard.setNumber("asdasd");
		
		CreditCard result = creditCardService.save(creditCard);
		
		Assert.isTrue(creditCardService.findAll().contains(result));
		
		unauthenticate();
	}
	

	
	//	Se comprueba que los scientists pueden actualizar los creditCards
	@Test
	public void testUpdate(){
		
		authenticate("scientist1");
		
		CreditCard creditCard = scientistService.getPrincipal().getCreditCard();
		
		creditCard.setCVV(999);
		creditCard.setHolder("new holder madafaka");
		
		CreditCard result = creditCardService.save(creditCard);
		
		Assert.isTrue(creditCardService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que no se puede actualizar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testUpdateIncorrectData(){
		
		authenticate("scientist1");
		

		CreditCard creditCard = scientistService.getPrincipal().getCreditCard();
		
		creditCard.setCVV(0);
		creditCard.setHolder("");
		
		CreditCard result = creditCardService.save(creditCard);
		
		Assert.isTrue(creditCardService.findAll().contains(result));
		
		unauthenticate();
	}	

}