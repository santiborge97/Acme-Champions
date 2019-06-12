
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import security.UserAccountService;
import services.AdministratorService;
import services.ConfigurationService;
import services.PresidentService;
import controllers.AbstractController;
import domain.Administrator;
import domain.President;
import forms.RegisterAdministratorForm;
import forms.RegisterPresidentForm;

@Controller
@RequestMapping("/administrator")
public class RegisterAdministratorController extends AbstractController {

	//Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private PresidentService		presidentService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private UserAccountService		userAccountService;


	// Methods

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final RegisterAdministratorForm administrator;

		administrator = new RegisterAdministratorForm();
		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("administrator") final RegisterAdministratorForm form, final BindingResult binding) {
		ModelAndView result;

		final Administrator adminReconstruct = this.administratorService.reconstruct(form, binding);

		final UserAccount ua = this.userAccountService.findByUsername(form.getUsername());

		if (binding.hasErrors())
			result = this.createEditModelAndView(form);
		else if (!form.checkPassword() || !form.getCheckbox())
			result = this.createEditModelAndView(form, "actor.commit.error");
		else if (ua != null)
			result = this.createEditModelAndView(form, "username.commit.error");
		else
			try {
				this.administratorService.save(adminReconstruct);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(form, "actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/createPresident", method = RequestMethod.GET)
	public ModelAndView createPresident() {
		final ModelAndView result;
		final RegisterPresidentForm president;

		president = new RegisterPresidentForm();
		result = this.createEditModelAndViewPresident(president);

		return result;
	}

	@RequestMapping(value = "/editPresident", method = RequestMethod.POST, params = "save")
	public ModelAndView savePresident(@ModelAttribute("president") final RegisterPresidentForm form, final BindingResult binding) {
		ModelAndView result;

		final President presidentReconstruct = this.presidentService.reconstruct(form, binding);

		final UserAccount ua = this.userAccountService.findByUsername(form.getUsername());

		if (binding.hasErrors())
			result = this.createEditModelAndViewPresident(form);
		else if (!form.checkPassword() || !form.getCheckbox())
			result = this.createEditModelAndViewPresident(form, "actor.commit.error");
		else if (ua != null)
			result = this.createEditModelAndViewPresident(form, "username.commit.error");
		else
			try {
				this.presidentService.save(presidentReconstruct);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewPresident(form, "actor.commit.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final RegisterAdministratorForm administrator) {
		ModelAndView result;

		result = this.createEditModelAndView(administrator, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RegisterAdministratorForm administrator, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("administrator/signUpAdministrator");
		result.addObject("administrator", administrator);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	protected ModelAndView createEditModelAndViewPresident(final RegisterPresidentForm president) {
		ModelAndView result;

		result = this.createEditModelAndViewPresident(president, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewPresident(final RegisterPresidentForm president, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("administrator/signUpPresident");
		result.addObject("president", president);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

}
