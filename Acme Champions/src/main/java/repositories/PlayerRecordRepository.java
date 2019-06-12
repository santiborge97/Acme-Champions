
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.PlayerRecord;

@Repository
public interface PlayerRecordRepository extends JpaRepository<PlayerRecord, Integer> {

}
