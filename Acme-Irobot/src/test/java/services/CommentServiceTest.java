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

import domain.Comment;
import domain.Irobot;
import domain.Scientist;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class CommentServiceTest extends AbstractTest {

	
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private IrobotService irobotService;
	

	
	//	Se comprueba que cualquier actor puede crear los comments y guardar los comments
	@Test
	public void driverCreateComment(){
		
		final Object testingData[][] = {{"admin", null},
										{"scientist1", null},
										{"customer1", null},
										{null , null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateComment((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateComment(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			Comment c = this.commentService.create();
			c.setBody("comment body");
			c.setMoment(new Date(System.currentTimeMillis()-1000));
			c.setIrobot((Irobot) irobotService.findAll().toArray()[0]);
			c = commentService.save(c);
			Assert.isTrue(commentService.findAll().contains(c));
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	
	//	Se comprueba que no se puede guardar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("customer2");

		Comment comment = commentService.create();
		
		comment.setBody("");
		comment.setIrobot(null);
		comment.setMoment(new Date(System.currentTimeMillis()+100000));
		
		Comment result = commentService.save(comment);
		
		Assert.isTrue(commentService.findAll().contains(result));
		
		unauthenticate();
	}
	

	

}