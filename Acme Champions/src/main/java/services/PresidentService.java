
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PresidentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Finder;
import domain.President;
import forms.RegisterPresidentForm;

@Service
@Transactional
public class PresidentService {

	// Managed Repository ------------------------
	@Autowired
	private PresidentRepository		presidentRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private BoxService				boxService;


	public President create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		President result;
		result = new President();

		final UserAccount userAccount = this.userAccountService.createPresident();
		result.setUserAccount(userAccount);

		return result;

	}

	public Collection<President> findAll() {

		Collection<President> result;
		result = this.presidentRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public President findOne(final int presidentId) {

		Assert.notNull(presidentId);
		President result;
		result = this.presidentRepository.findOne(presidentId);
		return result;
	}

	public President save(final President president) {

		Assert.notNull(president);
		President result;

		if (president.getId() != 0) {

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == president.getId());

			this.actorService.checkEmail(president.getEmail(), true);
			this.actorService.checkPhone(president.getPhone());

			final String phone = this.actorService.checkPhone(president.getPhone());
			president.setPhone(phone);

			final String newUrl = this.configurationService.checkURL(president.getPhoto());
			president.setPhoto(newUrl);

			result = this.presidentRepository.save(president);

		} else {

			this.actorService.checkEmail(president.getEmail(), true);
			this.actorService.checkPhone(president.getPhone());

			String pass = president.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = president.getUserAccount();
			userAccount.setPassword(pass);

			president.setUserAccount(userAccount);

			final String phone = this.actorService.checkPhone(president.getPhone());
			president.setPhone(phone);

			final String newUrl = this.configurationService.checkURL(president.getPhoto());
			president.setPhoto(newUrl);

			result = this.presidentRepository.save(president);

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

			final Finder finder = this.finderService.create();
			finder.setPresident(result);
			this.finderService.save(finder);

		}
		return result;
	}

	public President reconstruct(final RegisterPresidentForm form, final BindingResult binding) {

		final President president = this.create();

		this.validator.validate(form, binding);

		president.setName(form.getName());
		president.setSurnames(form.getSurnames());
		president.setPhoto(form.getPhoto());
		president.setEmail(form.getEmail());
		president.setPhone(form.getPhone());
		president.setAddress(form.getAddress());
		president.getUserAccount().setUsername(form.getUsername());
		president.getUserAccount().setPassword(form.getPassword());

		return president;

	}

	public President reconstruct(final President president, final BindingResult binding) {

		final President result;

		final President presidentBBDD = this.findOne(president.getId());

		if (presidentBBDD != null) {

			president.setUserAccount(presidentBBDD.getUserAccount());

			this.validator.validate(president, binding);

		}
		result = president;

		return result;

	}

	public void flush() {
		this.presidentRepository.flush();
	}

	// Other business methods -----------------------

	public President findByPrincipal() {
		President president;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		president = this.findByUserAccount(userAccount);
		Assert.notNull(president);

		return president;
	}

	public President findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		President result;

		result = this.presidentRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
}
