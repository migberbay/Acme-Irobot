
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	
}
