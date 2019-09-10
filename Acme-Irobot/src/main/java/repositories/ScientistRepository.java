
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Irobot;
import domain.Scientist;

@Repository
public interface ScientistRepository extends JpaRepository<Scientist, Integer> {
	
	@Query("select s from Scientist s where s.userAccount = ?1") 
	Scientist findByPrincipal(UserAccount principal);
	
//	@Query("select s from Scientist s order by sum(size(s.irobots.purchases))")
//	Collection<Scientist> Top10SellingScientist();
	
//	@Query("select i.scientist from Irobot i order by max(i.purchases.size) group by i.scientist")
//	Collection<Scientist> Top10SellingScientist();

}
