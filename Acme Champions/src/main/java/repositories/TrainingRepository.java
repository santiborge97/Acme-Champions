
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {

	@Query("select t from Training t where t.manager.id = ?1")
	Collection<Training> findTrainingsByManagerId(int managerId);
	
	@Query("select t from Training t where t.manager.id = ?1 and t.startDate > ?2")
	Collection<Training> findFutureTrainingsByManagerId(int managerId, Date now);

	@Query("select t from Training t join t.players play where play.id = ?1")
	Collection<Training> findTrainingsByPlayerId(int playerId);

}
