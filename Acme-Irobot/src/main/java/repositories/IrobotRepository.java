
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Irobot;
import domain.Scientist;

@Repository
public interface IrobotRepository extends JpaRepository<Irobot, Integer> {

	@Query("select i from Irobot i where i.title like %?1% or " +
			"i.ticker like %?1% or i.description like %?1%") 
	Collection<Irobot> findByKeyword(String keyword);

	@Query("select s.irobots from Scientist s where s = ?1") 
	Collection<Irobot> findByScientist(Scientist scientist);
	
	@Query("select avg(s.irobots.size) from Scientist s")
	Double getAvgIrobotsPerScientist();

	@Query("select min(s.irobots.size) from Scientist s")
	Integer getMinIrobotsPerScientist();
	
	@Query("select max(s.irobots.size) from Scientist s")
	Integer getMaxIrobotsPerScientist();
	
	@Query("select stddev(s.irobots.size) from Scientist s")
	Double getStdevIrobotsPerScientist();
	
	@Query("select i from Irobot i order by size(i.purchases)")
	Collection<Irobot> Top10SellingIrobots();
	
	
	
}
