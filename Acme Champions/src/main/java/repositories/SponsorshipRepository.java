
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select s from Sponsorship s where s.sponsor.id = ?1")
	Collection<Sponsorship> findAllBySponsorId(int actorId);

	@Query("select s from Sponsorship s where s.game != null and s.game.id = ?1")
	Collection<Sponsorship> findSponsorshipsByGameId(int gameId);

	@Query("select s from Sponsorship s where s.player != null and s.player.id = ?1")
	Collection<Sponsorship> findSponsorshipsByPlayerId(int playerId);

	@Query("select s from Sponsorship s where s.team != null and s.team.id = ?1")
	Collection<Sponsorship> findSponsorshipsByTeamId(int teamId);

	@Query("select s.id from Sponsorship s where s.team.id = ?1 and s.sponsor.id = ?2")
	Integer findSponsorshipByTeamAndSponsorId(final int teamId, final int sponsorId);

	@Query("select s.id from Sponsorship s where s.game.id = ?1 and s.sponsor.id = ?2")
	Integer findSponsorshipByGameAndSponsorId(final int gameId, final int sponsorId);

	@Query("select s.id from Sponsorship s where s.player.id = ?1 and s.sponsor.id = ?2")
	Integer findSponsorshipByPlayerAndSponsorId(final int playerId, final int sponsorId);

}
