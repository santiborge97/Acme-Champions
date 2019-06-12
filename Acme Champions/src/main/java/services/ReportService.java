
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportRepository;
import security.Authority;
import domain.Actor;
import domain.Manager;
import domain.Player;
import domain.Report;
import forms.ReportForm;

@Service
@Transactional
public class ReportService {

	// Managed Repository ------------------------
	@Autowired
	private ReportRepository	reportRepository;

	// Suporting services ------------------------

	@Autowired
	private PlayerService		playerService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods -----------------------

	public Report create() {

		Report result;
		result = new Report();

		return result;
	}

	public Report findOne(final int reportId) {

		Assert.notNull(reportId);
		Report result;
		result = this.reportRepository.findOne(reportId);
		return result;
	}

	public Report save(final Report report) {

		Assert.notNull(report);

		Report result;

		result = this.reportRepository.save(report);

		return result;

	}

	public void delete(final Report report) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.MANAGER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);

		this.reportRepository.delete(report);
	}

	public Report reconstruct(final ReportForm form, final BindingResult binding) {

		final Report result = this.create();

		this.validator.validate(form, binding);

		final Player player = this.playerService.findOne(form.getPlayerId());

		final Date now = new Date(System.currentTimeMillis() - 1000);

		result.setDescription(form.getDescription());
		result.setPlayer(player);
		result.setMoment(now);

		return result;

	}

	public Collection<Player> findPlayerWithReportPerTeamId(final int teamId) {

		final Collection<Player> result = this.reportRepository.findPlayerWithReportPerTeamId(teamId);

		return result;
	}

	public Collection<Report> findByTeamId(final int temaId) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.MANAGER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		final Collection<Report> result = this.reportRepository.findByTeamId(temaId);

		return result;
	}

	public Collection<Report> findByPlayerId(final int playerId) {

		final Collection<Report> result = this.reportRepository.findByPlayerId(playerId);

		return result;
	}

	public Boolean securityPlayer(final int playerId) {

		Boolean result = false;

		final Manager manager = this.managerService.findByPrincipal();

		final Player player = this.playerService.findOne(playerId);

		if (manager.getTeam() != null && player.getTeam() != null && manager.getTeam().equals(player.getTeam()))
			result = true;

		return result;
	}

	public Boolean exist(final int reportId) {

		Boolean result = false;

		final Report report = this.reportRepository.findOne(reportId);

		if (report != null)
			result = true;

		return result;
	}

	public void flush() {
		this.reportRepository.flush();
	}
}
