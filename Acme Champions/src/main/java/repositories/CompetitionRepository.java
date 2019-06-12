
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

	@Query("select c from Competition c join c.games g where g.id = ?1")
	Competition findCompetitionByGameId(int gameId);

	@Query("select c from Competition c where c.federation.id = ?1")
	Collection<Competition> findByFederationId(int id);
	
	@Query("select c from Competition c where c.format.id = ?1")
	Collection<Competition> findByFormatId(int id);

}
