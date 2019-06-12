
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

	@Query("select p from Player p where p.userAccount.id = ?1")
	Player findByUserAccountId(int userAccountId);

	@Query("select p from Player p where (p.name like ?1 or p.surnames like ?1) and (p.positionEnglish like ?2 or p.positionSpanish like ?2)")
	Collection<Player> findPlayersByFinder(String keyword, String position);

	@Query("select p from Player p where p.team.id = ?1")
	Collection<Player> findPlayersOfTeam(int teamId);

	@Query("select count(m) from Minutes m join m.playersScore p where m.id = ?1 and m.game.homeTeam.id=p.team.id")
	Integer countHomeGoalsByMinutesId(int minutesId);

	@Query("select count(m) from Minutes m join m.playersYellow p where m.id = ?1 and m.game.homeTeam.id=p.team.id")
	Integer countHomeYellowsByMinutesId(int minutesId);

	@Query("select count(m) from Minutes m join m.playersRed p where m.id = ?1 and m.game.homeTeam.id=p.team.id")
	Integer countHomeRedsByMinutesId(int minutesId);

	@Query("select count(m) from Minutes m join m.playersScore p where m.id = ?1 and m.game.visitorTeam.id=p.team.id")
	Integer countVisitorGoalsByMinutesId(int minutesId);

	@Query("select count(m) from Minutes m join m.playersYellow p where m.id = ?1 and m.game.visitorTeam.id=p.team.id")
	Integer countVisitorYellowsByMinutesId(int minutesId);

	@Query("select count(m) from Minutes m join m.playersRed p where m.id = ?1 and m.game.visitorTeam.id=p.team.id")
	Integer countVisitorRedsByMinutesId(int minutesId);

	@Query("select count(m) from Minutes m join m.playersScore p where m.id = ?1 and p.id = ?2")
	Integer countGoalsOfPlayerByMinutePlayerId(int minutesId, int playerId);

	@Query("select count(m) from Minutes m join m.playersYellow p where m.id = ?1 and p.id = ?2")
	Integer countYellowsOfPlayerByMinutePlayerId(int minutesId, int playerId);

	@Query("select count(m) from Minutes m join m.playersRed p where m.id = ?1 and p.id = ?2")
	Integer countRedOfPlayerByMinutePlayerId(int minutesId, int playerId);

}
