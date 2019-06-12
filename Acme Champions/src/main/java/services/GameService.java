
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.GameRepository;
import security.Authority;
import domain.Actor;
import domain.Game;
import domain.Referee;
import domain.Sponsorship;
import domain.Team;

@Service
@Transactional
public class GameService {

	// Managed repository

	@Autowired
	private GameRepository		gameRepository;

	// Supporting Services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private CompetitionService	competitionService;

	@Autowired
	private Validator			validator;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private TeamService			teamService;


	//Simple CRUD methods

	public Game create() {

		final Game result = new Game();

		result.setFriendly(true);

		return result;

	}
	public Collection<Game> findAll() {

		final Collection<Game> result = this.gameRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Game findOne(final int gameId) {

		final Game result = this.gameRepository.findOne(gameId);
		return result;

	}

	public Game save(final Game game) {
		Assert.notNull(game);

		final Authority authReferee = new Authority();
		authReferee.setAuthority(Authority.REFEREE);
		final Authority authFederation = new Authority();
		authFederation.setAuthority(Authority.FEDERATION);

		//Hay que estar logeado
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		//Solo se pueden editar partidos amistosos
		if (game.getId() != 0)
			Assert.isTrue(game.getFriendly());

		//solo puede guardar partidos referee's y federation's
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authReferee) || actor.getUserAccount().getAuthorities().contains(authFederation));

		//los equipos del partido deben ser funcionales
		final Collection<Team> functionalTeams = this.teamService.findFunctionalTeams();
		Assert.isTrue(functionalTeams.contains(game.getHomeTeam()) && functionalTeams.contains(game.getVisitorTeam()));

		//posthacking referee y federation
		if (actor.getUserAccount().getAuthorities().contains(authReferee))
			Assert.isTrue(game.getReferee().getId() == actor.getId());

		//si el partido ya pasó no puede ser editado
		final Date currentDate = new Date(System.currentTimeMillis() - 1000);
		if (game.getId() != 0) {
			final Game gameBBDD = this.findOne(game.getId());
			Assert.isTrue(gameBBDD.getGameDate().after(currentDate));
		}

		//el equipo local y visitante no pueden ser el mismo
		Assert.isTrue(!game.getHomeTeam().equals(game.getVisitorTeam()));

		//si el que guarda partido es referee el partido es amistoso, si es federation es competitivo
		if (actor.getUserAccount().getAuthorities().contains(authReferee))
			Assert.isTrue(game.getFriendly());
		else if (actor.getUserAccount().getAuthorities().contains(authFederation))
			Assert.isTrue(!game.getFriendly());

		//Ya sea al crearse o editarse, la fecha ha de ser futura
		Assert.isTrue(game.getGameDate().after(currentDate));

		//el lugar del partido debe coincidir con el estadio del equipo local
		final Team homeTeam = game.getHomeTeam();
		Assert.isTrue(game.getPlace().equals(homeTeam.getStadiumName()));

		final Game result = this.gameRepository.save(game);

		return result;

	}
	public Game saveAlgorithm(final Game game) {

		final Game result = this.gameRepository.save(game);

		return result;

	}

	public void delete(final Game game) {
		Assert.notNull(game);

		final Authority authReferee = new Authority();
		authReferee.setAuthority(Authority.REFEREE);

		//Hay que estar logeado
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		//solo pueden borrar partidos referee's
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authReferee));

		//posthacking referee y federation
		Assert.isTrue(game.getReferee().getId() == actor.getId());

		//la fecha ha de ser futura
		final Date currentDate = new Date(System.currentTimeMillis() - 1000);
		Assert.isTrue(game.getGameDate().after(currentDate));

		//eliminacion relacion con sponsorship
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findSponsorshipsByGameId(game.getId());
		for (final Sponsorship s : sponsorships)
			this.sponsorshipService.deleteForDeleteGame(s);

		//Solo se pueden borrar partidos amistosos
		Assert.isTrue(game.getFriendly());

		this.gameRepository.delete(game);

	}
	//Other business methods
	public Collection<Game> findNextGamesOfTeam(final int teamId) {
		return this.gameRepository.findNextGamesOfTeam(teamId);
	}

	public Collection<Game> findAllGamesOrdered() {
		return this.gameRepository.findAllGamesOrdered();
	}

	public Collection<Game> localGamesByTeamId(final int teamId) {

		final Collection<Game> result = this.gameRepository.localGamesByTeamId(teamId);

		return result;

	}

	public Collection<Game> visitorGamesByTeamId(final int teamId) {

		final Collection<Game> result = this.gameRepository.visitorGamesByTeamId(teamId);

		return result;
	}

	public Collection<Game> findAllEndedGamesWithoutMinutes(final int refereeId) {

		final Collection<Game> res = this.gameRepository.findAllEndedGamesWithoutMinutes(refereeId);

		return res;

	}

	public Collection<Game> findByCompetitionId(final int id) {

		final Collection<Game> res = this.gameRepository.findByCompetitionId(id);

		return res;
	}

	public Collection<Game> findGameByRefereeId(final int refereeId) {
		final Collection<Game> res = this.gameRepository.findGameByRefereeId(refereeId);
		return res;
	}

	public Collection<Game> findFutureGamesByRefereeId(final int refereeId) {
		final Collection<Game> res = this.gameRepository.findFutureGamesByRefereeId(refereeId);
		return res;
	}

	public Collection<Game> findAllEndedGamesWithMinutes(final int refereeId) {
		final Collection<Game> res = this.gameRepository.findAllEndedGamesWithMinutes(refereeId);
		return res;
	}

	public Game reconstruct(final Game game, final BindingResult binding) {
		final Authority authReferee = new Authority();
		authReferee.setAuthority(Authority.REFEREE);
		final Authority authFederation = new Authority();
		authFederation.setAuthority(Authority.FEDERATION);

		//Hay que estar logeado
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		Assert.notNull(game);
		if (game.getId() == 0) {
			if (actor.getUserAccount().getAuthorities().contains(authReferee)) {
				game.setFriendly(true);
				final Referee referee = this.refereeService.findByPrincipal();
				game.setReferee(referee);
			} else
				game.setFriendly(false);
			if (game.getHomeTeam() != null)
				game.setPlace(game.getHomeTeam().getStadiumName());
			else
				game.setPlace("");
		} else {

			final Game gameBBDD = this.findOne(game.getId());
			game.setFriendly(gameBBDD.getFriendly());
			game.setReferee(gameBBDD.getReferee());
			if (game.getHomeTeam() != null)
				game.setPlace(game.getHomeTeam().getStadiumName());
		}

		this.validator.validate(game, binding);

		return game;
	}

	public void flush() {
		this.gameRepository.flush();
	}

	public Collection<Game> findFutureFriendlyGamesByRefereeId(final int refereeId) {
		final Collection<Game> res = this.gameRepository.findFutureFriendlyGamesByRefereeId(refereeId);

		return res;
	}
}
