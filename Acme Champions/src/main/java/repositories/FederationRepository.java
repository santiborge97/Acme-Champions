
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Federation;

@Repository
public interface FederationRepository extends JpaRepository<Federation, Integer> {

	@Query("select f from Federation f where f.userAccount.id = ?1")
	Federation findByUserAccountId(int userAccountId);
}
