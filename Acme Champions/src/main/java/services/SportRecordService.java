
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SportRecordRepository;
import security.Authority;
import domain.Actor;
import domain.History;
import domain.Player;
import domain.SportRecord;

@Service
@Transactional
public class SportRecordService {

	// Managed Repository ------------------------
	@Autowired
	private SportRecordRepository	sportRecordRepository;

	// Suporting services ------------------------

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods -----------------------

	public SportRecord create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PLAYER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		SportRecord result;

		result = new SportRecord();

		return result;

	}

	public Collection<SportRecord> findAll() {

		Collection<SportRecord> result;

		result = this.sportRecordRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public SportRecord findOne(final int recordId) {

		SportRecord result;

		result = this.sportRecordRepository.findOne(recordId);

		return result;
	}

	public SportRecord save(final SportRecord record) {

		final Player player = this.playerService.findByPrincipal();
		Assert.notNull(player);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PLAYER);
		Assert.isTrue(player.getUserAccount().getAuthorities().contains(authority));

		Assert.notNull(record);

		if (record.getStartDate() != null && record.getEndDate() != null)
			Assert.isTrue(!record.getStartDate().after(record.getEndDate()));

		SportRecord result;

		result = this.sportRecordRepository.save(record);

		return result;
	}

	public void delete(final SportRecord record) {

		Assert.notNull(record);
		Assert.isTrue(record.getId() != 0);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Authority br = new Authority();
		br.setAuthority(Authority.PLAYER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(br));

		this.sportRecordRepository.delete(record);
	}

	public Boolean exist(final int sportRecordId) {

		Boolean result = false;

		final SportRecord sportRecord = this.sportRecordRepository.findOne(sportRecordId);

		if (sportRecord != null)
			result = true;

		return result;
	}

	public Boolean security(final int sportRecordId) {

		Boolean result = false;

		final Player player = this.playerService.findByPrincipal();

		final SportRecord sportRecord = this.sportRecordRepository.findOne(sportRecordId);

		final History history = this.historyService.findByPlayerId(player.getId());

		if (sportRecord != null && history != null && history.getSportRecords().contains(sportRecord))
			result = true;

		return result;
	}

	public void flush() {
		this.sportRecordRepository.flush();
	}
}
