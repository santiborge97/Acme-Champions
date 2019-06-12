
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
import forms.CreateHistoryForm;

@Controller
@RequestMapping("/history/player")
public class HistoryPlayerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private PersonalDataService		personalDataService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final History history;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Player player = this.playerService.findByPrincipal();
		history = this.historyService.findByPlayerId(player.getId());

		if (history != null) {

			result = new ModelAndView("history/display");
			result.addObject("history", history);
			result.addObject("banner", banner);
			result.addObject("requestUri", "/history/player/display.do");

		} else
			result = new ModelAndView("redirect:/history/player/create.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final CreateHistoryForm createHistoryForm;

		final Player player = this.playerService.findByPrincipal();

		final History history = this.historyService.findByPlayerId(player.getId());

		if (history == null) {
			createHistoryForm = new CreateHistoryForm();

			result = this.createEditModelAndView(createHistoryForm);
		} else
			result = new ModelAndView("redirect:/history/player/display.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("history") final CreateHistoryForm form, final BindingResult binding) {
		ModelAndView result;

		final Player player = this.playerService.findByPrincipal();
		final History history = this.historyService.create();
		final PersonalData personalReconstruct = this.personalDataService.reconstruct(form, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(form);
		else
			try {
				final PersonalData p = this.personalDataService.save(personalReconstruct);
				history.setPersonalData(p);
				history.setPlayer(player);
				this.historyService.save(history);
				result = new ModelAndView("redirect:/history/player/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(form, "history.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;

		final Player player = this.playerService.findByPrincipal();
		final History history = this.historyService.findByPlayerId(player.getId());

		if (history != null)
			try {
				this.historyService.delete(history);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/display.do");
			}
		else
			result = new ModelAndView("redirect:/history/player/create.do");

		return result;
	}
	// Ancillary methods

	protected ModelAndView createEditModelAndView(final CreateHistoryForm createHistoryForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createHistoryForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateHistoryForm createHistoryForm, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("history/createHistory");
		result.addObject("history", createHistoryForm);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}
}
