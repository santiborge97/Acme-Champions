
package controllers.president;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.PlayerService;
import services.PresidentService;
import services.ReportService;
import services.TeamService;
import controllers.AbstractController;
import domain.Player;
import domain.President;
import domain.Report;
import domain.Team;

@Controller
@RequestMapping("/report/president")
public class ReportPresidentController extends AbstractController {

	@Autowired
	private ReportService			reportService;

	@Autowired
	private PresidentService		presidentService;

	@Autowired
	private TeamService				teamService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/listPlayers", method = RequestMethod.GET)
	public ModelAndView listPlayer() {

		final ModelAndView result;
		final Collection<Player> players;

		final President president = this.presidentService.findByPrincipal();

		final Team team = this.teamService.findTeamByPresidentId(president.getId());

		if (team != null)
			players = this.reportService.findPlayerWithReportPerTeamId(team.getId());
		else
			players = null;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("report/listPlayer");
		result.addObject("requestUri", "/report/president/listPlayers.do");
		result.addObject("players", players);
		result.addObject("banner", banner);

		return result;

	}

	@RequestMapping(value = "/listReports", method = RequestMethod.GET)
	public ModelAndView listReport(@RequestParam final int playerId) {

		final ModelAndView result;
		final Collection<Report> reports;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final President president = this.presidentService.findByPrincipal();

		final Boolean exist = this.playerService.exist(playerId);

		if (exist) {

			final Player player = this.playerService.findOne(playerId);

			final Team team = this.teamService.findTeamByPresidentId(president.getId());

			if (team != null && player.getTeam() != null && player.getTeam().equals(team)) {

				reports = this.reportService.findByPlayerId(playerId);

				result = new ModelAndView("report/listReport");
				result.addObject("requestUri", "/report/president/listReports.do");
				result.addObject("reports", reports);
				result.addObject("banner", banner);
				result.addObject("player", player);

			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;

	}

	@RequestMapping(value = "/punish", method = RequestMethod.GET)
	public ModelAndView punish(@RequestParam final int playerId) {

		ModelAndView result;
		final Collection<Report> reports;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final President president = this.presidentService.findByPrincipal();

		final Boolean exist = this.playerService.exist(playerId);

		if (exist) {

			final Player player = this.playerService.findOne(playerId);

			final Team team = this.teamService.findTeamByPresidentId(president.getId());

			reports = this.reportService.findByPlayerId(playerId);

			if (team != null && player.getTeam() != null && player.getTeam().equals(team) && !reports.isEmpty() && !player.getPunished()) {

				try {
					player.setPunished(true);
					this.playerService.savePresident(player);
				} catch (final Throwable oops) {
					result = new ModelAndView("misc/error");
					result.addObject("banner", banner);
				}

				result = new ModelAndView("redirect:/report/president/listReports.do?playerId=" + playerId);

			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;

	}
}
