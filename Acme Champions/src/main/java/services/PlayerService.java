
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PlayerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Finder;
import domain.Player;
import domain.StatisticalData;
import domain.Team;
import forms.RegisterPlayerForm;

@Service
@Transactional
public class PlayerService {

	// Managed Repository ------------------------
	@Autowired
	private PlayerRepository		playerRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private StatisticalDataService	statisticalDataService;

	@Autowired
	private Validator				validator;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private TeamService				teamService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ConfigurationService	configurationService;


	// Methods -----------------------------------

	public Player create() {

		Player result;
		result = new Player();

		final UserAccount userAccount = this.userAccountService.createPlayer();
		result.setUserAccount(userAccount);

		return result;

	}

	public Collection<Player> findAll() {

		Collection<Player> result;
		result = this.playerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Player findOne(final int playerId) {

		Assert.notNull(playerId);
		Player result;
		result = this.playerRepository.findOne(playerId);
		return result;
	}

	public void editTeam(final Player player, final Team team, final Double offeredClause) {

		final Team old = player.getTeam();

		player.setTeam(team);
		player.setBuyoutClause(offeredClause);

		this.playerRepository.save(player);

		this.teamService.functional(old);

	}

	public Player save(final Player player) {
		Assert.notNull(player);
		Player result;

		if (player.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Authority president = new Authority();
			president.setAuthority(Authority.PRESIDENT);

			//si referee escribe un minute y se sanciona a jugador por roja o amarilla
			final Authority referee = new Authority();
			referee.setAuthority(Authority.REFEREE);

			//si es manager y lo marca como lesionado
			final Authority manager = new Authority();
			manager.setAuthority(Authority.MANAGER);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == player.getId() || actor.getUserAccount().getAuthorities().contains(admin) || actor.getUserAccount().getAuthorities().contains(president) || actor.getUserAccount().getAuthorities().contains(referee)
				|| actor.getUserAccount().getAuthorities().contains(manager));

			this.actorService.checkEmail(player.getEmail(), false);
			this.actorService.checkPhone(player.getPhone());

			final String newUrl = this.configurationService.checkURL(player.getPhoto());
			player.setPhoto(newUrl);

			final String phone = this.actorService.checkPhone(player.getPhone());
			player.setPhone(phone);

			result = this.playerRepository.save(player);

		} else {

			String pass = player.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = player.getUserAccount();
			userAccount.setPassword(pass);

			player.setUserAccount(userAccount);

			this.actorService.checkEmail(player.getEmail(), false);
			this.actorService.checkPhone(player.getPhone());

			final String newUrl = this.configurationService.checkURL(player.getPhoto());
			player.setPhoto(newUrl);

			final String phone = this.actorService.checkPhone(player.getPhone());
			player.setPhone(phone);

			result = this.playerRepository.save(player);

			final StatisticalData data = this.statisticalDataService.create();
			data.setPlayer(result);
			this.statisticalDataService.save(data);

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

	public Player savePresident(final Player player) {

		final Player result = this.playerRepository.save(player);

		return result;
	}

	public Player findByPrincipal() {
		Player player;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		player = this.findByUserAccount(userAccount);
		Assert.notNull(player);

		return player;
	}

	public Player findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Player result;

		result = this.playerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Boolean exist(final int playerId) {

		Boolean result = false;

		final Player player = this.playerRepository.findOne(playerId);

		if (player != null)
			result = true;

		return result;
	}

	public Player reconstruct(final RegisterPlayerForm form, final BindingResult binding) {

		final Player player = this.create();

		final Locale locale = LocaleContextHolder.getLocale();
		final String language = locale.getLanguage();

		if (language.equals("es")) {
			player.setPositionSpanish(form.getPosition());

			if (form.getPosition().equals("PORTERO"))
				player.setPositionEnglish("GOALKEEPER");
			else if (form.getPosition().equals("DEFENSA"))
				player.setPositionEnglish("DEFENDER");
			else if (form.getPosition().equals("CENTROCAMPISTA"))
				player.setPositionEnglish("MIDFIELDER");
			else if (form.getPosition().equals("DELANTERO"))
				player.setPositionEnglish("STRIKER");
			else
				form.setPosition("error");

		} else if (language.equals("en")) {

			player.setPositionEnglish(form.getPosition());

			if (form.getPosition().equals("GOALKEEPER"))
				player.setPositionSpanish("PORTERO");
			else if (form.getPosition().equals("DEFENDER"))
				player.setPositionSpanish("DEFENSA");
			else if (form.getPosition().equals("MIDFIELDER"))
				player.setPositionSpanish("CENTROCAMPISTA");
			else if (form.getPosition().equals("STRIKER"))
				player.setPositionSpanish("DELANTERO");
			else
				form.setPosition("error");
		}

		this.validator.validate(form, binding);

		player.setName(form.getName());
		player.setSurnames(form.getSurnames());
		player.setPhoto(form.getPhoto());
		player.setEmail(form.getEmail());
		player.setPhone(form.getPhone());
		player.setAddress(form.getAddress());

		player.setBuyoutClause(0.0);
		player.setInjured(false);
		player.setPunished(false);
		player.setSquadName(form.getSquadName());
		player.setSquadNumber(form.getSquadNumber());

		player.getUserAccount().setUsername(form.getUsername());
		player.getUserAccount().setPassword(form.getPassword());

		return player;

	}
	public Player reconstruct(final Player player, final BindingResult binding) {

		final Player result;

		final Player playerBBDD = this.findOne(player.getId());

		if (playerBBDD != null) {

			player.setUserAccount(playerBBDD.getUserAccount());
			player.setBuyoutClause(playerBBDD.getBuyoutClause());
			player.setInjured(playerBBDD.getInjured());
			player.setPunished(playerBBDD.getPunished());
			player.setPositionEnglish(playerBBDD.getPositionEnglish());
			player.setPositionSpanish(playerBBDD.getPositionSpanish());
			player.setTeam(playerBBDD.getTeam());

			this.validator.validate(player, binding);

		}
		result = player;
		return result;

	}

	public Collection<Player> findPlayersByFinder(final Finder finder) {

		String keyword = finder.getKeyWord();
		String position = finder.getPosition();
		Collection<Player> players = new HashSet<Player>();

		if (keyword == null)
			keyword = "";
		if (position == null)
			position = "";

		final String keywordFormat = "%" + keyword + "%";
		final String positionFormat = "%" + position.toUpperCase() + "%";

		players = this.playerRepository.findPlayersByFinder(keywordFormat, positionFormat);

		return players;
	}

	public Collection<Player> findPlayersOfTeam(final int teamId) {
		final Collection<Player> players = this.playerRepository.findPlayersOfTeam(teamId);

		return players;
	}

	public Integer countHomeGoalsByMinutesId(final int minutesId) {
		final Integer res = this.playerRepository.countHomeGoalsByMinutesId(minutesId);

		return res;
	}

	public Integer countHomeYellowsByMinutesId(final int minutesId) {
		final Integer res = this.playerRepository.countHomeYellowsByMinutesId(minutesId);

		return res;
	}

	public Integer countHomeRedsByMinutesId(final int minutesId) {
		final Integer res = this.playerRepository.countHomeRedsByMinutesId(minutesId);
		return res;
	}

	public Integer countVisitorGoalsByMinutesId(final int minutesId) {
		final Integer res = this.playerRepository.countVisitorGoalsByMinutesId(minutesId);
		return res;
	}

	public Integer countVisitorYellowsByMinutesId(final int minutesId) {
		final Integer res = this.playerRepository.countVisitorYellowsByMinutesId(minutesId);

		return res;
	}

	public Integer countVisitorRedsByMinutesId(final int minutesId) {
		final Integer res = this.playerRepository.countVisitorRedsByMinutesId(minutesId);

		return res;
	}

	public Integer countGoalsOfPlayerByMinutePlayerId(final int minutesId, final int playerId) {
		final Integer res = this.playerRepository.countGoalsOfPlayerByMinutePlayerId(minutesId, playerId);

		return res;
	}

	public Integer countYellowsOfPlayerByMinutePlayerId(final int minutesId, final int playerId) {
		final Integer res = this.playerRepository.countYellowsOfPlayerByMinutePlayerId(minutesId, playerId);

		return res;
	}

	Integer countRedOfPlayerByMinutePlayerId(final int minutesId, final int playerId) {
		final Integer res = this.playerRepository.countRedOfPlayerByMinutePlayerId(minutesId, playerId);

		return res;
	}

	public void flush() {
		this.playerRepository.flush();
	}
}
