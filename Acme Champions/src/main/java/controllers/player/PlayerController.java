
package controllers.player;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.PlayerService;
import services.SponsorshipService;
import services.StatisticalDataService;
import controllers.AbstractController;
import domain.Player;
import domain.Sponsorship;
import domain.StatisticalData;

@Controller
@RequestMapping("/player")
public class PlayerController extends AbstractController {

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private StatisticalDataService	statisticalDataService;

	@Autowired
	private SponsorshipService		sponsorshipService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int playerId) {
		ModelAndView result;

		final Player player = this.playerService.findOne(playerId);
		final String banner = this.configurationService.findConfiguration().getBanner();
		final String language = LocaleContextHolder.getLocale().getLanguage();

		if (player == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {
			final StatisticalData statisticalData = this.statisticalDataService.findStatisticalDataByPlayerId(playerId);
			result = new ModelAndView("player/display");
			result.addObject("player", player);
			result.addObject("statisticalData", statisticalData);
			result.addObject("banner", banner);
			result.addObject("language", language);

			//Esto es para ver los sponsorships
			final Collection<Sponsorship> sponsorships = this.sponsorshipService.findSponsorshipsByPlayerId(playerId);
			if (sponsorships != null && !sponsorships.isEmpty())
				result.addObject("sponsorships", sponsorships);
		}
		return result;
	}
}
