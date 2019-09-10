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

import domain.Irobot;
import domain.Scientist;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class IrobotServiceTest extends AbstractTest {

	
	
	@Autowired
	private IrobotService irobotService;
	
	@Autowired
	private ScientistService scientistService;
	

	
	
	
	//	Se comprueba que solo los scientist pueden crear los irobots.
	@Test
	public void driverCreateIrobot(){
		
		final Object testingData[][] = {{"scientist1", null},
										{"scientist2", null},
										{"customer1", NullPointerException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateIrobot((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateIrobot(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.irobotService.create(scientistService.getPrincipal());
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	//	Se comprueba que los scientists pueden guardar las betpools.
	@Test
	public void testSave(){
		
		authenticate("scientist1");
		
		Irobot irobot = irobotService.create(scientistService.getPrincipal());
		
		irobot.setTitle("Title irobot");
		irobot.setDescription("Description irobot");
		irobot.setIsDecomissioned(false);
		irobot.setPrice(678);
		
		Irobot result = irobotService.save(irobot);
		
		Assert.isTrue(irobotService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que no se puede guardar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("scientist1");

		Irobot irobot = irobotService.create(scientistService.getPrincipal());
		
		irobot.setTitle("");
		irobot.setDescription("");	
		irobot.setIsDecomissioned(null);
		irobot.setPrice(null);
		
		Irobot result = irobotService.save(irobot);
		
		Assert.isTrue(irobotService.findAll().contains(result));
		
		unauthenticate();
	}
	

	
	//	Se comprueba que los scientists pueden actualizar los irobots
	@Test
	public void testUpdate(){
		
		authenticate("scientist1");
		
		Irobot irobot = (Irobot) irobotService.findAll().toArray()[0];
		
		irobot.setTitle("Updated title irobot");
		irobot.setDescription("Updated description irobot");
		
		Irobot result = irobotService.save(irobot);
		
		Assert.isTrue(irobotService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que no se puede actualizar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testUpdateIncorrectData(){
		
		authenticate("scientist1");
		
		Irobot irobot = (Irobot) irobotService.findAll().toArray()[0];
		
		irobot.setTitle("");
		irobot.setDescription("");
		
		Irobot result = irobotService.save(irobot);
		
		Assert.isTrue(irobotService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que no se puede eliminar sin estar logeado como scientist.
	@Test(expected = DataIntegrityViolationException.class)
	public void testDeleteNotAuthenticated(){
		
		authenticate(null);
		
		Irobot irobot = (Irobot) irobotService.findAll().toArray()[0];
		
	    irobotService.delete(irobot);
		
		Assert.isTrue(!irobotService.findAll().contains(irobot));
		
		unauthenticate();
	}
	

}