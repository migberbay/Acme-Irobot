package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Comment;
import domain.Irobot;


@Service
@Transactional
public class CommentService {

	//Managed Repository -----
	@Autowired
	private CommentRepository commentRepository;
	
	//Supporting Services -----
	
	@Autowired
	private Validator validator;

	//Simple CRUD methods -----
	public Comment create(){
		Comment res = new Comment();
		return res;
	}
	
	public Collection<Comment> findAll(){
		return commentRepository.findAll();
	}
	
	public Comment findOne(int Id){
		return commentRepository.findOne(Id);
	}
	
	public Comment save(Comment a){
		return commentRepository.saveAndFlush(a);
	}
	
	public void delete(Comment a){
		commentRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Collection<Comment> findByIrobot(Irobot irobot){
		return this.commentRepository.findByIrobot(irobot);
	}

	
}