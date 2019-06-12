
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;
import domain.Player;
import domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

	@Query("select p from Player p where p.team.id=?1")
	Collection<Player> findPlayersByTeamId(int teamId);

	@Query("select m from Manager m where m.team.id=?1")
	Manager findManagerByTeamId(int teamId);

	@Query("select t from Team t where t.president.id=?1")
	Team findByPresidentId(int id);

	@Query("select t from Team t where t.president.id =?1")
	Team findTeamByPresidentId(int presidentId);

	@Query("select t from Team t where t.functional=true")
	Collection<Team> findFunctionalTeams();

}
