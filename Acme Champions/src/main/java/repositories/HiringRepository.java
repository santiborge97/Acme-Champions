
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hiring;

@Repository
public interface HiringRepository extends JpaRepository<Hiring, Integer> {

	@Query("select h from Hiring h where h.president.id=?1 and h.manager.id=?2 and h.status = 'ACCEPTED'")
	Hiring findHiringOfPresidentAndManager(int presidentId, int managerId);

	@Query("Select h from Hiring h where h.manager.id = ?1 and h.status = 'PENDING'")
	Collection<Hiring> findByManagerIdPresident(int id);

	@Query("Select h from Hiring h where h.manager.id = ?1 and h.status = 'PENDING' and h.manager.team = null")
	Collection<Hiring> findByManagerId(int id);

	@Query("Select h from Hiring h where h.manager.id = ?1")
	Collection<Hiring> findAllByManager(int id);

}
