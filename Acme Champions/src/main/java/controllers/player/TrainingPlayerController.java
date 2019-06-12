
package controllers.player;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.PlayerService;
import services.TrainingService;
import controllers.AbstractController;
import domain.Player;
import domain.Training;

@Controller
@RequestMapping("/training/player")
public class TrainingPlayerController extends AbstractController {

	@Autowired
	private TrainingService			trainingService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ConfigurationService	configurationService;


	//List---------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Training> trainings;
		final Player p;

		p = this.playerService.findByPrincipal();

		trainings = this.trainingService.findTrainingsByPlayerId(p.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("training/list");
		result.addObject("trainings", trainings);
		result.addObject("requestURI", "training/player/list.do");
		result.addObject("pagesize", 5);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());

		return result;

	}

}
