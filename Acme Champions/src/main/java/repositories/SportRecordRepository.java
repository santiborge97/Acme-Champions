
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SportRecord;

@Repository
public interface SportRecordRepository extends JpaRepository<SportRecord, Integer> {

}
