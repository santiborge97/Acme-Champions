
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BoxService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Box;

@Controller
@RequestMapping("/box/actor")
public class BoxActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Box> boxes;
		Actor a;

		a = this.actorService.findByPrincipal();

		final String banner = this.configurationService.findConfiguration().getBanner();

		boxes = this.boxService.findAllBoxByActor(a.getId());

		result = new ModelAndView("box/list");
		result.addObject("boxes", boxes);
		result.addObject("banner", banner);
		result.addObject("requestURI", "box/actor/list.do");
		return result;

	}

}
