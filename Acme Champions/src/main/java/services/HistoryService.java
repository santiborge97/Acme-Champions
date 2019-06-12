
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import security.Authority;
import domain.Actor;
import domain.History;

@Service
@Transactional
public class HistoryService {

	// Managed Repository ------------------------
	@Autowired
	private HistoryRepository	historyRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PlayerService		playerService;

	@Autowired
	private PlayerRecordService	playerRecordService;

	@Autowired
	private SportRecordService	sportRecordService;

	@Autowired
	private PersonalDataService	personalDataService;


	// Simple CRUD methods -----------------------

	public History create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PLAYER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		History result;
		result = new History();

		return result;
	}

	public History findOne(final int historyId) {

		Assert.notNull(historyId);
		History result;
		result = this.historyRepository.findOne(historyId);
		return result;
	}

	public History save(final History history) {

		Assert.notNull(history);

		History result;

		result = this.historyRepository.save(history);

		return result;

	}

	public History historyPerSportRecordId(final int recordId) {

		final History result = this.historyRepository.historyPerSportRecordId(recordId);

		return result;
	}

	public History historyPerPlayerRecordId(final int recordId) {

		final History result = this.historyRepository.historyPerPlayerRecordId(recordId);

		return result;

	}

	public History findByPlayerId(final int playerId) {

		final History result = this.historyRepository.findByPlayerId(playerId);

		return result;
	}

	public void delete(final History history) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PLAYER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Assert.notNull(history);

		this.historyRepository.delete(history);

	}

	public void flush() {
		this.historyRepository.flush();
	}
}
