
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.President;

@Repository
public interface PresidentRepository extends JpaRepository<President, Integer> {

	@Query("select p from President p where p.userAccount.id = ?1")
	President findByUserAccountId(int userAccountId);
}
