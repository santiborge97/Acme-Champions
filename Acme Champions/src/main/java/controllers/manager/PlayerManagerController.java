
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.ManagerService;
import services.PlayerService;
import controllers.AbstractController;
import domain.Manager;
import domain.Player;

@Controller
@RequestMapping("/player/manager")
public class PlayerManagerController extends AbstractController {

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ManagerService			managerService;


	@RequestMapping(value = "/injured", method = RequestMethod.GET)
	public ModelAndView addPlayerScored(@RequestParam final int playerId) {
		ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();
		final Player player = this.playerService.findOne(playerId);
		final Manager manager = this.managerService.findByPrincipal();
		if (player != null && player.getTeam() != null && manager.getTeam() != null && player.getTeam().getId() == manager.getTeam().getId())
			try {
				player.setInjured(!player.getInjured());

				this.playerService.save(player);

				result = new ModelAndView("redirect:/team/president,manager/listByManager.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("misc/error");
				result.addObject("banner", banner);
			}
		else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}
		return result;
	}
}
