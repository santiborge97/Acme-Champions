
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.GameService;
import services.ManagerService;
import services.TrainingService;
import domain.Game;
import domain.Manager;
import domain.Training;

@Controller
@RequestMapping("/calendar/manager")
public class CalendarController {

	@Autowired
	private TrainingService			trainingService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private GameService				gameService;


	//List---------------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Training> trainings;
		Collection<Game> games;
		final Manager manag;

		manag = this.managerService.findByPrincipal();
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (manag.getTeam() != null) {

			trainings = this.trainingService.findTrainingsByManagerId(manag.getId());

			games = this.gameService.findNextGamesOfTeam(manag.getTeam().getId());

			result = new ModelAndView("team/calendar");
			result.addObject("trainings", trainings);
			result.addObject("requestURI", "calendar/manager/show.do");
			result.addObject("pagesize", 5);
			result.addObject("banner", banner);
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
			result.addObject("autoridad", "manager");
			result.addObject("games", games);

		} else {
			result = new ModelAndView("misc/noTeam");
			result.addObject("banner", banner);
		}

		return result;

	}
}
