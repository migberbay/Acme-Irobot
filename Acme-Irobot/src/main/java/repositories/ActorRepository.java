
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Actor;
import domain.Comment;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	@Query("select a from Actor a where a.userAccount = ?1")
	Actor getByUserAccount(UserAccount userAccount);
	
//	@Query("select a from Actor a where ?1 in a.comments")
//	Actor getByCommentId(Integer id);
	
}
