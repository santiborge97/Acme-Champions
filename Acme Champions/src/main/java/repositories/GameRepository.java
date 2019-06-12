
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

	@Query("select g from Game g where (g.homeTeam.id=?1 or g.visitorTeam=?1) and g.gameDate > current_date")
	Collection<Game> findNextGamesOfTeam(int teamId);

	@Query("select g from Game g order by g.gameDate desc")
	Collection<Game> findAllGamesOrdered();

	@Query("select g from Game g where g.homeTeam.id=?1 and g.gameDate < current_date")
	Collection<Game> localGamesByTeamId(int teamId);

	@Query("select g from Game g where g.visitorTeam.id=?1 and g.gameDate < current_date")
	Collection<Game> visitorGamesByTeamId(int teamId);

	@Query("select g from Game g where (((select count(m) from Minutes m where m.game.id=g.id and m.closed=false)=1) or ((select count(m) from Minutes m where m.game.id=g.id and m.closed=true)=0)) and (g.gameDate<CURRENT_TIMESTAMP) and (g.referee.id=?1)")
	Collection<Game> findAllEndedGamesWithoutMinutes(int refereeId);

	@Query("select g from Game g where ((select count(m) from Minutes m where m.game.id=g.id and m.closed=true)=1) and (g.gameDate<CURRENT_TIMESTAMP) and (g.referee.id=?1)")
	Collection<Game> findAllEndedGamesWithMinutes(int refereeId);

	@Query("select g from Competition c join c.games g where c.id=?1")
	Collection<Game> findByCompetitionId(int id);

	@Query("select g from Game g where g.referee.id = ?1")
	Collection<Game> findGameByRefereeId(int refereeId);

	@Query("select g from Game g where g.referee.id = ?1 and g.gameDate>CURRENT_TIMESTAMP")
	Collection<Game> findFutureGamesByRefereeId(int refereeId);

	@Query("select g from Game g where g.referee.id = ?1 and g.gameDate>CURRENT_TIMESTAMP and g.friendly=true")
	Collection<Game> findFutureFriendlyGamesByRefereeId(int refereeId);
}
