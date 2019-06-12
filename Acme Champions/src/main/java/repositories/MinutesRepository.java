
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Minutes;

@Repository
public interface MinutesRepository extends JpaRepository<Minutes, Integer> {

	@Query("select m from Minutes m where m.game.id = ?1")
	Minutes findMinuteByGameId(int gameId);

	@Query("select count(m) from Minutes m where m.game.id = ?1")
	Integer CountMinutesByGameId(int gameId);

}
