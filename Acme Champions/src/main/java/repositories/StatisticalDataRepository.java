
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.StatisticalData;

@Repository
public interface StatisticalDataRepository extends JpaRepository<StatisticalData, Integer> {

	@Query("select s from StatisticalData s where s.player.id = ?1")
	StatisticalData findStatisticalDataByPlayerId(int playerId);

}
