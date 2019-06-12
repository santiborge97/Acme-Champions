
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Player;
import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select distinct p from Report r join r.player p where (p.team.id = ?1 and r.player.id = p.id )")
	Collection<Player> findPlayerWithReportPerTeamId(int teamId);

	@Query("select r from Report r join r.player p where p.team.id = ?1")
	Collection<Report> findByTeamId(int temaId);

	@Query("select r from Report r where r.player.id = ?1")
	Collection<Report> findByPlayerId(int playerId);
}
