
package controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.GameService;
import services.ManagerService;
import services.PlayerService;
import services.PresidentService;
import services.TeamService;
import domain.Game;
import domain.Manager;
import domain.Player;
import domain.President;
import domain.Team;

@Controller
@RequestMapping("/team/president,manager")
public class TeamPresidentManagerController extends AbstractController {

	@Autowired
	private PresidentService		presidentService;

	@Autowired
	private TeamService				teamService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private GameService				gameService;


	//Members Team--------------------------------------------------------------------------------------

	@RequestMapping(value = "/listByPresident", method = RequestMethod.GET)
	public ModelAndView listByPresident() {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final President president = this.presidentService.findByPrincipal();
		Manager manager;
		final Team team;
		final Collection<Player> players;
		final Collection<Manager> managers = new HashSet<Manager>();

		team = this.teamService.findTeamByPresidentId(president.getId());

		if (team != null) {

			players = this.playerService.findPlayersOfTeam(team.getId());
			manager = this.managerService.findManagerByTeamId(team.getId());
			if (manager != null)
				managers.add(manager);

			boolean canFire = false;

			final Collection<Game> games = this.gameService.findNextGamesOfTeam(team.getId());

			if (games.isEmpty())
				canFire = true;

			result = new ModelAndView("actor/listPlayerManager");
			result.addObject("players", players);
			result.addObject("managers", managers);
			result.addObject("requestURI", "team/president,manager/listByPresident.do");
			result.addObject("pagesize", 5);
			result.addObject("banner", banner);
			result.addObject("canFire", canFire);
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
			result.addObject("AmInFinder", false);

		} else {
			result = new ModelAndView("misc/noTeam");
			result.addObject("banner", banner);
		}

		return result;

	}

	@RequestMapping(value = "/listByManager", method = RequestMethod.GET)
	public ModelAndView listByManager() {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Manager manager = this.managerService.findByPrincipal();
		final Team team;
		final Collection<Player> players;
		final Collection<Manager> managers = new HashSet<Manager>();
		Double goalPrediction = 0.0;

		team = manager.getTeam();

		if (team != null) {

			goalPrediction = this.configurationService.goalPrediction(team.getId());
			players = this.playerService.findPlayersOfTeam(team.getId());
			if (manager != null)
				managers.add(manager);

			result = new ModelAndView("actor/listPlayerManager");
			result.addObject("players", players);
			result.addObject("managers", managers);
			result.addObject("requestURI", "team/president,manager/listByManager.do");
			result.addObject("pagesize", 5);
			result.addObject("banner", banner);
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
			result.addObject("AmInFinder", false);
			result.addObject("goalPrediction", goalPrediction);

		} else {
			result = new ModelAndView("misc/noTeam");
			result.addObject("banner", banner);
		}

		return result;

	}
}
