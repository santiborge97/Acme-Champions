
package controllers.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.HistoryService;
import services.PersonalDataService;
import services.PlayerService;
import controllers.AbstractController;
import domain.History;
import domain.PersonalData;
import domain.Player;
import forms.PersonalDataForm;

@Controller
@RequestMapping("/personalData/player")
public class PersonalDataPlayerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private PersonalDataService		personalDataService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final PersonalDataForm form;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Player player = this.playerService.findByPrincipal();
		final History history = this.historyService.findByPlayerId(player.getId());

		if (history == null || history.getPersonalData() == null || history.getPersonalData().getId() == 0) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			form = this.personalDataService.createForm(history.getPersonalData().getId());

			result = this.createEditModelAndView(form);

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("personalData") final PersonalDataForm form, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final PersonalData personalReconstruct = this.personalDataService.reconstruct(form, binding);

		final Player player = this.playerService.findByPrincipal();
		final History history = this.historyService.findByPlayerId(player.getId());

		if (history != null && form.getId() != 0) {

			final Boolean security = this.personalDataService.security(personalReconstruct.getId());

			if (security) {

				if (binding.hasErrors())
					result = this.createEditModelAndView(form);
				else
					try {
						this.personalDataService.save(personalReconstruct);
						result = new ModelAndView("redirect:/history/player/display.do");

					} catch (final Throwable oops) {
						result = this.createEditModelAndView(form, "history.commit.error");
					}

			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final PersonalDataForm form) {
		ModelAndView result;

		result = this.createEditModelAndView(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalDataForm form, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("history/editPersonalData");
		result.addObject("personalData", form);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}
}
