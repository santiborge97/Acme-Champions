
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

	@Query("select h from History h where h.player.id = ?1")
	History findByPlayerId(int playerId);

	@Query("select h from History h join h.sportRecords r where r.id = ?1")
	History historyPerSportRecordId(int recordId);

	@Query("select h from History h join h.playerRecords r where r.id = ?1")
	History historyPerPlayerRecordId(int recordId);
}
