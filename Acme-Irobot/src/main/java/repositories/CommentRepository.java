
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;
import domain.Irobot;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	@Query("select c from Comment c where c.irobot = ?1") 
	Collection<Comment> findByIrobot(Irobot irobot);
}
