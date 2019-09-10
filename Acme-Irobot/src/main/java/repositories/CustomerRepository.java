
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	
	@Query("select c from Customer c where c.userAccount = ?1") 
	Customer findByPrincipal(UserAccount principal);
	
}
