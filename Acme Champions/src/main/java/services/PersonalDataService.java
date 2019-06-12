
package services;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PersonalDataRepository;
import security.Authority;
import domain.Actor;
import domain.History;
import domain.PersonalData;
import domain.Player;
import forms.CreateHistoryForm;
import forms.PersonalDataForm;

@Service
@Transactional
public class PersonalDataService {

	// Managed Repository ------------------------
	@Autowired
	private PersonalDataRepository	personalDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ConfigurationService	configurationService;


	// Simple CRUD methods -----------------------

	public PersonalData create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PLAYER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		PersonalData result;
		result = new PersonalData();

		return result;
	}

	public Collection<PersonalData> findAll() {

		Collection<PersonalData> result;

		result = this.personalDataRepository.findAll();

		return result;
	}

	public PersonalData findOne(final int personalId) {

		Assert.notNull(personalId);
		PersonalData result;
		result = this.personalDataRepository.findOne(personalId);
		return result;
	}

	public PersonalData save(final PersonalData personal) {

		Assert.notNull(personal);

		final Collection<String> newAttachments = this.checkPictures(personal.getPhotos());
		personal.setPhotos(newAttachments);

		final String newUrl = this.configurationService.checkURL(personal.getSocialNetworkProfilelink());
		personal.setSocialNetworkProfilelink(newUrl);

		PersonalData result;

		result = this.personalDataRepository.save(personal);

		return result;

	}
	public PersonalDataForm createForm(final int personalDataId) {

		final PersonalData personal = this.findOne(personalDataId);

		final PersonalDataForm result = new PersonalDataForm();

		result.setId(personal.getId());
		result.setVersion(personal.getVersion());
		result.setPhotos(personal.getPhotos());
		result.setSocialNetworkProfilelink(personal.getSocialNetworkProfilelink());

		return result;
	}

	public Boolean exist(final int personalDataId) {
		Boolean res = false;

		final PersonalData find = this.personalDataRepository.findOne(personalDataId);

		if (find != null)
			res = true;

		return res;
	}

	public PersonalData reconstruct(final CreateHistoryForm form, final BindingResult binding) {

		final PersonalData result = this.create();

		this.validator.validate(form, binding);

		result.setPhotos(form.getPhotos());
		result.setSocialNetworkProfilelink(form.getSocialNetworkProfilelink());

		return result;

	}

	public PersonalData reconstruct(final PersonalDataForm form, final BindingResult binding) {

		final PersonalData result = this.create();

		this.validator.validate(form, binding);

		result.setId(form.getId());
		result.setVersion(form.getVersion());
		result.setPhotos(form.getPhotos());
		result.setSocialNetworkProfilelink(form.getSocialNetworkProfilelink());

		return result;

	}
	public Boolean security(final int personalDataId) {

		Boolean result = false;

		final Player player = this.playerService.findByPrincipal();

		final History history = this.historyService.findByPlayerId(player.getId());

		final PersonalData personal = this.findOne(personalDataId);

		if (history.getPersonalData().equals(personal))
			result = true;

		return result;
	}

	public void flush() {
		this.personalDataRepository.flush();
	}

	public Collection<String> checkPictures(final Collection<String> links) {

		final Collection<String> newAttachments = new HashSet<String>();
		for (final String url : links)
			try {
				new URL(url);
				final String newUrl = this.configurationService.checkURL(url);
				newAttachments.add(newUrl);
			} catch (final Exception e) {
				throw new DataIntegrityViolationException("Invalid URL");
			}

		return newAttachments;
	}
}
