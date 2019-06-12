
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccountId(int userAccountId);

	@Query("select m from Manager m where (m.name like ?1 or m.surnames like ?1)")
	Collection<Manager> findManagersByFinder(String keyword);

	@Query("select m from Manager m where m.team.id = ?1")
	Manager findManagerByTeamId(int teamId);
}
