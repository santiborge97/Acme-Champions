
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PlayerRecordRepository;
import security.Authority;
import domain.Actor;
import domain.History;
import domain.Player;
import domain.PlayerRecord;

@Service
@Transactional
public class PlayerRecordService {

	// Managed Repository ------------------------
	@Autowired
	private PlayerRecordRepository	playerRecordRepository;

	// Suporting services ------------------------

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods -----------------------

	public PlayerRecord create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PLAYER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		PlayerRecord result;

		result = new PlayerRecord();

		return result;

	}

	public Collection<PlayerRecord> findAll() {

		Collection<PlayerRecord> result;

		result = this.playerRecordRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public PlayerRecord findOne(final int recordId) {

		PlayerRecord result;

		result = this.playerRecordRepository.findOne(recordId);

		return result;
	}

	public PlayerRecord save(final PlayerRecord record) {

		final Player player = this.playerService.findByPrincipal();
		Assert.notNull(player);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PLAYER);
		Assert.isTrue(player.getUserAccount().getAuthorities().contains(authority));

		Assert.notNull(record);

		if (record.getStartDate() != null && record.getEndDate() != null)
			Assert.isTrue(!record.getStartDate().after(record.getEndDate()));

		PlayerRecord result;

		result = this.playerRecordRepository.save(record);

		return result;
	}
	public void delete(final PlayerRecord record) {

		Assert.notNull(record);
		Assert.isTrue(record.getId() != 0);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Authority br = new Authority();
		br.setAuthority(Authority.PLAYER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(br));

		this.playerRecordRepository.delete(record);
	}

	public Boolean exist(final int playerRecordId) {

		Boolean result = false;

		final PlayerRecord playerRecord = this.playerRecordRepository.findOne(playerRecordId);

		if (playerRecord != null)
			result = true;

		return result;
	}

	public Boolean security(final int playerRecordId) {

		Boolean result = false;

		final Player player = this.playerService.findByPrincipal();

		final PlayerRecord playerRecord = this.playerRecordRepository.findOne(playerRecordId);

		final History history = this.historyService.findByPlayerId(player.getId());

		if (playerRecord != null && history != null && history.getPlayerRecords().contains(playerRecord))
			result = true;

		return result;
	}

	public void flush() {
		this.playerRecordRepository.flush();
	}
}
