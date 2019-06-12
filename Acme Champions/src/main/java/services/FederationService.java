
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FederationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Federation;
import forms.RegisterFederationForm;

@Service
@Transactional
public class FederationService {

	// Managed Repository ------------------------
	@Autowired
	private FederationRepository	federationRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private Validator				validator;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;


	// Methods -----------------------------------

	public Federation create() {

		Federation result;
		result = new Federation();

		final UserAccount userAccount = this.userAccountService.createFederation();
		result.setUserAccount(userAccount);

		return result;

	}

	public Collection<Federation> findAll() {

		Collection<Federation> result;
		result = this.federationRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Federation findOne(final int federationId) {

		Assert.notNull(federationId);
		Federation result;
		result = this.federationRepository.findOne(federationId);
		return result;
	}

	public Federation save(final Federation federation) {
		Assert.notNull(federation);
		Federation result;

		if (federation.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == federation.getId() || actor.getUserAccount().getAuthorities().contains(admin));

			this.actorService.checkEmail(federation.getEmail(), false);
			this.actorService.checkPhone(federation.getPhone());

			final String phone = this.actorService.checkPhone(federation.getPhone());
			federation.setPhone(phone);

			final String newUrl = this.configurationService.checkURL(federation.getPhoto());
			federation.setPhoto(newUrl);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(!now.before(federation.getEstablishmentDate()));

			result = this.federationRepository.save(federation);

		} else {

			String pass = federation.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = federation.getUserAccount();
			userAccount.setPassword(pass);

			federation.setUserAccount(userAccount);

			this.actorService.checkEmail(federation.getEmail(), false);
			this.actorService.checkPhone(federation.getPhone());

			final String phone = this.actorService.checkPhone(federation.getPhone());
			federation.setPhone(phone);

			final String newUrl = this.configurationService.checkURL(federation.getPhoto());
			federation.setPhoto(newUrl);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(!now.before(federation.getEstablishmentDate()));

			result = this.federationRepository.save(federation);

			Box inBox, outBox, trashBox, spamBox;
			inBox = this.boxService.create();
			outBox = this.boxService.create();
			trashBox = this.boxService.create();
			spamBox = this.boxService.create();

			inBox.setName("in box");
			outBox.setName("out box");
			trashBox.setName("trash box");
			spamBox.setName("spam box");

			inBox.setActor(result);
			outBox.setActor(result);
			trashBox.setActor(result);
			spamBox.setActor(result);

			final Collection<Box> boxes = new ArrayList<>();
			boxes.add(spamBox);
			boxes.add(trashBox);
			boxes.add(inBox);
			boxes.add(outBox);

			inBox = this.boxService.saveNewActor(inBox);
			outBox = this.boxService.saveNewActor(outBox);
			trashBox = this.boxService.saveNewActor(trashBox);
			spamBox = this.boxService.saveNewActor(spamBox);

		}
		return result;

	}

	public Federation findByPrincipal() {
		Federation federation;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		federation = this.findByUserAccount(userAccount);
		Assert.notNull(federation);

		return federation;
	}

	public Federation findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Federation result;

		result = this.federationRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Federation reconstruct(final RegisterFederationForm form, final BindingResult binding) {

		this.validator.validate(form, binding);

		final Federation federation = this.create();

		federation.setEstablishmentDate(form.getEstablishmentDate());
		federation.setName(form.getName());
		federation.setSurnames(form.getSurnames());
		federation.setPhoto(form.getPhoto());
		federation.setEmail(form.getEmail());
		federation.setPhone(form.getPhone());
		federation.setAddress(form.getAddress());
		federation.getUserAccount().setUsername(form.getUsername());
		federation.getUserAccount().setPassword(form.getPassword());

		return federation;

	}

	public Federation reconstruct(final Federation federation, final BindingResult binding) {

		final Federation result;

		final Federation federationBBDD = this.findOne(federation.getId());

		if (federationBBDD != null) {

			federation.setUserAccount(federationBBDD.getUserAccount());

			this.validator.validate(federation, binding);

		}
		result = federation;
		return result;

	}

	public void flush() {
		this.federationRepository.flush();
	}
}
