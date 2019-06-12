
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/manager")
public class ManagerController extends AbstractController {

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int managerId) {
		ModelAndView result;

		final Manager manager = this.managerService.findOne(managerId);
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (manager == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			result = new ModelAndView("manager/display");
			result.addObject("manager", manager);
			result.addObject("banner", banner);
		}
		return result;
	}

}
