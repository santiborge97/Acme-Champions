
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Finder;
import domain.Manager;
import domain.Team;
import forms.RegisterManagerForm;

@Service
@Transactional
public class ManagerService {

	// Managed Repository ------------------------
	@Autowired
	private ManagerRepository		managerRepository;

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
	private TeamService				teamService;

	@Autowired
	private ConfigurationService	configurationService;


	// Methods -----------------------------------

	public Manager create() {

		Manager result;
		result = new Manager();

		final UserAccount userAccount = this.userAccountService.createManager();
		result.setUserAccount(userAccount);

		return result;

	}

	public Collection<Manager> findAll() {

		Collection<Manager> result;
		result = this.managerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Manager findOne(final int managerId) {

		Assert.notNull(managerId);
		Manager result;
		result = this.managerRepository.findOne(managerId);
		return result;
	}

	public void editTeam(final Manager manager, final Team team) {

		final Team old = manager.getTeam();

		manager.setTeam(team);

		this.managerRepository.save(manager);

		this.teamService.functional(old);

	}

	public Manager save(final Manager manager) {
		Assert.notNull(manager);
		Manager result;

		if (manager.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Authority president = new Authority();
			president.setAuthority(Authority.PRESIDENT);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == manager.getId() || actor.getUserAccount().getAuthorities().contains(admin) || actor.getUserAccount().getAuthorities().contains(president));

			this.actorService.checkEmail(manager.getEmail(), false);
			this.actorService.checkPhone(manager.getPhone());
			final String newUrl = this.configurationService.checkURL(manager.getPhoto());
			manager.setPhoto(newUrl);

			final String phone = this.actorService.checkPhone(manager.getPhone());
			manager.setPhone(phone);

			result = this.managerRepository.save(manager);

		} else {

			String pass = manager.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = manager.getUserAccount();
			userAccount.setPassword(pass);

			manager.setUserAccount(userAccount);

			this.actorService.checkEmail(manager.getEmail(), false);
			this.actorService.checkPhone(manager.getPhone());

			final String newUrl = this.configurationService.checkURL(manager.getPhoto());
			manager.setPhoto(newUrl);

			final String phone = this.actorService.checkPhone(manager.getPhone());
			manager.setPhone(phone);

			result = this.managerRepository.save(manager);

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

	public Manager findByPrincipal() {
		Manager manager;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		manager = this.findByUserAccount(userAccount);
		Assert.notNull(manager);

		return manager;
	}

	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Manager result;

		result = this.managerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Manager reconstruct(final RegisterManagerForm form, final BindingResult binding) {

		this.validator.validate(form, binding);

		final Manager manager = this.create();

		manager.setName(form.getName());
		manager.setSurnames(form.getSurnames());
		manager.setPhoto(form.getPhoto());
		manager.setEmail(form.getEmail());
		manager.setPhone(form.getPhone());
		manager.setAddress(form.getAddress());
		manager.getUserAccount().setUsername(form.getUsername());
		manager.getUserAccount().setPassword(form.getPassword());

		return manager;

	}

	public Manager reconstruct(final Manager manager, final BindingResult binding) {

		final Manager result;

		final Manager managerBBDD = this.findOne(manager.getId());

		if (managerBBDD != null) {

			manager.setUserAccount(managerBBDD.getUserAccount());
			manager.setTeam(managerBBDD.getTeam());

			this.validator.validate(manager, binding);

		}
		result = manager;
		return result;

	}

	public Collection<Manager> findManagersByFinder(final Finder finder) {

		String keyword = finder.getKeyWord();
		Collection<Manager> managers = new HashSet<Manager>();

		if (keyword == null)
			keyword = "";

		final String keywordFormat = "%" + keyword + "%";

		managers = this.managerRepository.findManagersByFinder(keywordFormat);

		return managers;

	}

	public Boolean exist(final int managerId) {

		Boolean res = false;

		final Manager manager = this.managerRepository.findOne(managerId);

		if (manager != null)
			res = true;

		return res;
	}

	public Manager findManagerByTeamId(final int teamId) {

		final Manager res = this.managerRepository.findManagerByTeamId(teamId);

		return res;
	}

	public void flush() {
		this.managerRepository.flush();
	}

}
