
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ConfigurationService	configurationService;


	// Methods

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Configuration configuration;

		configuration = this.configurationService.findConfiguration();
		Assert.notNull(configuration);
		result = this.createEditModelAndView(configuration);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Configuration configuration, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.configurationService.exist(configuration.getId());

		final Configuration reconstruct = this.configurationService.reconstruct(configuration, binding);

		if (configuration.getId() != 0 && exist) {
			if (binding.hasErrors())
				result = this.createEditModelAndView(configuration);
			else
				try {
					this.configurationService.save(reconstruct);
					result = new ModelAndView("redirect:edit.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(configuration, "configuration.commit.error");
				}
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);

		}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Configuration configuration) {
		ModelAndView result;

		result = this.createEditModelAndView(configuration, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("administrator/editConfiguration");
		result.addObject("configuration", configuration);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
