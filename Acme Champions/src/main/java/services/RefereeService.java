
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

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Referee;
import forms.RegisterRefereeForm;

@Service
@Transactional
public class RefereeService {

	// Managed Repository ------------------------
	@Autowired
	private RefereeRepository		refereeRepository;

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

	public Referee create() {

		Referee result;
		result = new Referee();

		final UserAccount userAccount = this.userAccountService.createReferee();
		result.setUserAccount(userAccount);

		return result;

	}

	public Collection<Referee> findAll() {

		Collection<Referee> result;
		result = this.refereeRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Referee findOne(final int refereeId) {

		Assert.notNull(refereeId);
		Referee result;
		result = this.refereeRepository.findOne(refereeId);
		return result;
	}

	public Referee save(final Referee referee) {
		Assert.notNull(referee);
		Referee result;

		if (referee.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == referee.getId() || actor.getUserAccount().getAuthorities().contains(admin));

			this.actorService.checkEmail(referee.getEmail(), false);
			this.actorService.checkPhone(referee.getPhone());

			final String newUrl = this.configurationService.checkURL(referee.getPhoto());
			referee.setPhoto(newUrl);

			final String phone = this.actorService.checkPhone(referee.getPhone());
			referee.setPhone(phone);

			result = this.refereeRepository.save(referee);

		} else {

			String pass = referee.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = referee.getUserAccount();
			userAccount.setPassword(pass);

			referee.setUserAccount(userAccount);

			this.actorService.checkEmail(referee.getEmail(), false);
			this.actorService.checkPhone(referee.getPhone());

			final String newUrl = this.configurationService.checkURL(referee.getPhoto());
			referee.setPhoto(newUrl);

			final String phone = this.actorService.checkPhone(referee.getPhone());
			referee.setPhone(phone);

			result = this.refereeRepository.save(referee);

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

	public Referee findByPrincipal() {
		Referee referee;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		referee = this.findByUserAccount(userAccount);
		Assert.notNull(referee);

		return referee;
	}

	public Referee findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Referee result;

		result = this.refereeRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Referee reconstruct(final RegisterRefereeForm form, final BindingResult binding) {

		this.validator.validate(form, binding);

		final Referee referee = this.create();

		referee.setName(form.getName());
		referee.setSurnames(form.getSurnames());
		referee.setPhoto(form.getPhoto());
		referee.setEmail(form.getEmail());
		referee.setPhone(form.getPhone());
		referee.setAddress(form.getAddress());
		referee.getUserAccount().setUsername(form.getUsername());
		referee.getUserAccount().setPassword(form.getPassword());

		return referee;

	}

	public Referee reconstruct(final Referee referee, final BindingResult binding) {

		final Referee result;

		final Referee refereeBBDD = this.findOne(referee.getId());

		if (refereeBBDD != null) {

			referee.setUserAccount(refereeBBDD.getUserAccount());

			this.validator.validate(referee, binding);

		}
		result = referee;
		return result;

	}

	public void flush() {
		this.refereeRepository.flush();
	}
}
