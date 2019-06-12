
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SigningRepository;
import security.Authority;
import domain.Actor;
import domain.Signing;
import domain.Team;
import forms.SigningForm;

@Service
@Transactional
public class SigningService {

	// Managed Repository ------------------------
	@Autowired
	private SigningRepository	signingRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PlayerService		playerService;

	@Autowired
	private Validator			validator;

	@Autowired
	private PresidentService	presidentService;

	@Autowired
	private TeamService			teamService;


	public SigningForm create(final Integer playerId) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PRESIDENT);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		SigningForm result;
		result = new SigningForm();

		result.setPlayerId(playerId);

		return result;

	}

	public Collection<Signing> findAll() {

		Collection<Signing> result;
		result = this.signingRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Signing findOne(final int hiringId) {

		Assert.notNull(hiringId);
		Signing result;
		result = this.signingRepository.findOne(hiringId);
		return result;
	}

	public Signing save(final Signing signing) {

		Assert.notNull(signing);
		Signing result;

		if (signing.getId() == 0) {

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);
			final Authority authority = new Authority();
			authority.setAuthority(Authority.PRESIDENT);
			Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

			signing.setStatus("PENDING");

			result = this.signingRepository.save(signing);

		} else {

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);
			final Authority authority = new Authority();
			authority.setAuthority(Authority.PLAYER);
			final Authority authority3 = new Authority();
			authority3.setAuthority(Authority.PRESIDENT);

			Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)) || (actor.getUserAccount().getAuthorities().contains(authority3)));

			result = this.signingRepository.save(signing);

		}
		return result;
	}
	public Signing reconstruct(final SigningForm form, final BindingResult binding) {

		final Signing signing = new Signing();

		if (form.getId() == 0) {

			signing.setPlayer(this.playerService.findOne(form.getPlayerId()));
			signing.setMandatoryComment(form.getMandatoryComment());
			signing.setPresident(this.presidentService.findByPrincipal());
			signing.setPrice(form.getPrice());
			signing.setOfferedClause(form.getOfferedClause());
			signing.setStatus("PENDING");

		} else {

			final Signing oldOne = this.signingRepository.findOne(form.getId());

			signing.setId(oldOne.getId());
			signing.setPlayer(oldOne.getPlayer());
			signing.setMandatoryComment(form.getMandatoryComment());
			signing.setPresident(oldOne.getPresident());
			signing.setPrice(oldOne.getPrice());
			signing.setOfferedClause(oldOne.getOfferedClause());
			signing.setVersion(oldOne.getVersion());
			signing.setStatus(oldOne.getStatus());

		}

		this.validator.validate(signing, binding);

		return signing;

	}

	public Collection<Signing> findByPresident(final int id) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PRESIDENT);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		final Team team = this.teamService.findByPresidentId(id);

		final Collection<Signing> res = this.signingRepository.findByTeamId(team.getId());

		return res;
	}

	public Boolean exist(final int signingId) {

		Boolean res = false;

		final Signing signing = this.signingRepository.findOne(signingId);

		if (signing != null)
			res = true;

		return res;
	}

	public SigningForm editForm(final Signing signing) {

		final SigningForm res = new SigningForm();

		res.setId(signing.getId());
		res.setPlayerId(signing.getPlayer().getId());
		res.setMandatoryComment(signing.getMandatoryComment());
		res.setPrice(signing.getPrice());
		res.setVersion(signing.getVersion());

		return res;
	}

	public Collection<Signing> findByPlayer(final int id) {

		final Collection<Signing> res = this.signingRepository.findByPlayerId(id);

		return res;
	}

	public Collection<Signing> findAllByPlayer(final int id) {

		final Collection<Signing> res = this.signingRepository.findAllByPlayerId(id);

		return res;
	}

	public void delete(final Signing signing) {

		Assert.notNull(signing);
		Assert.isTrue(signing.getId() != 0);

		this.signingRepository.delete(signing);

	}

	public Signing findSigningOfPresidentAndPlayer(final int presidentId, final int playerId) {
		return this.signingRepository.findSigningOfPresidentAndPlayer(presidentId, playerId);
	}

	public Collection<Signing> findAllByPresident(final int id) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PRESIDENT);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		final Collection<Signing> res = this.signingRepository.findAllByTeamId(id);

		return res;
	}
}
