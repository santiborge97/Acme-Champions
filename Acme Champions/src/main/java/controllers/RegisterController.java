
package controllers;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Credentials;
import security.UserAccount;
import security.UserAccountService;
import services.ConfigurationService;
import services.FederationService;
import services.ManagerService;
import services.PlayerService;
import services.RefereeService;
import services.SponsorService;
import domain.Federation;
import domain.Manager;
import domain.Player;
import domain.Referee;
import domain.Sponsor;
import forms.RegisterFederationForm;
import forms.RegisterManagerForm;
import forms.RegisterPlayerForm;
import forms.RegisterRefereeForm;
import forms.RegisterSponsorForm;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	// Services

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private FederationService		federationService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private UserAccountService		userAccountService;


	//Manager
	@RequestMapping(value = "/createManager", method = RequestMethod.GET)
	public ModelAndView createManager() {
		final ModelAndView result;
		final RegisterManagerForm manager = new RegisterManagerForm();

		result = this.createEditModelAndViewManager(manager);

		return result;
	}

	@RequestMapping(value = "/saveManager", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManager(@ModelAttribute("manager") final RegisterManagerForm form, final BindingResult binding) {
		ModelAndView result;
		final Manager manager;

		manager = this.managerService.reconstruct(form, binding);

		final UserAccount ua = this.userAccountService.findByUsername(form.getUsername());

		if (binding.hasErrors())
			result = this.createEditModelAndViewManager(form);
		else if (ua != null)
			result = this.createEditModelAndViewManager(form, "username.commit.error");
		else
			try {
				Assert.isTrue(form.getCheckbox());
				Assert.isTrue(form.checkPassword());
				this.managerService.save(manager);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(manager.getUserAccount().getUsername());
				credentials.setPassword(manager.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewManager(form, "manager.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndViewManager(final RegisterManagerForm manager) {
		ModelAndView result;

		result = this.createEditModelAndViewManager(manager, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewManager(final RegisterManagerForm manager, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpManager");
		result.addObject("manager", manager);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	//Player
	@RequestMapping(value = "/createPlayer", method = RequestMethod.GET)
	public ModelAndView createPlayer() {
		final ModelAndView result;
		final RegisterPlayerForm player = new RegisterPlayerForm();

		result = this.createEditModelAndViewPlayer(player);

		return result;
	}

	@RequestMapping(value = "/savePlayer", method = RequestMethod.POST, params = "save")
	public ModelAndView savePlayer(@ModelAttribute("player") final RegisterPlayerForm form, final BindingResult binding) {
		ModelAndView result;
		final Player player;

		player = this.playerService.reconstruct(form, binding);

		final UserAccount ua = this.userAccountService.findByUsername(form.getUsername());

		if (binding.hasErrors())
			result = this.createEditModelAndViewPlayer(form);
		else if (ua != null)
			result = this.createEditModelAndViewPlayer(form, "username.commit.error");
		else
			try {
				Assert.isTrue(form.getCheckbox());
				Assert.isTrue(form.checkPassword());
				this.playerService.save(player);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(player.getUserAccount().getUsername());
				credentials.setPassword(player.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewPlayer(form, "player.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndViewPlayer(final RegisterPlayerForm player) {
		ModelAndView result;

		result = this.createEditModelAndViewPlayer(player, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewPlayer(final RegisterPlayerForm player, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Locale locale = LocaleContextHolder.getLocale();
		final String language = locale.getLanguage();

		result = new ModelAndView("security/signUpPlayer");
		result.addObject("player", player);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);
		result.addObject("language", language);

		return result;
	}

	//Sponsor
	@RequestMapping(value = "/createSponsor", method = RequestMethod.GET)
	public ModelAndView createSponsor() {
		final ModelAndView result;
		final RegisterSponsorForm player = new RegisterSponsorForm();

		result = this.createEditModelAndViewSponsor(player);

		return result;
	}

	@RequestMapping(value = "/saveSponsor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsorr(@ModelAttribute("sponsor") final RegisterSponsorForm form, final BindingResult binding) {
		ModelAndView result;
		final Sponsor sponsor;

		sponsor = this.sponsorService.reconstruct(form, binding);

		final UserAccount ua = this.userAccountService.findByUsername(form.getUsername());

		final Collection<String> makes = this.configurationService.findConfiguration().getMakes();

		if (binding.hasErrors())
			result = this.createEditModelAndViewSponsor(form);
		else if (ua != null)
			result = this.createEditModelAndViewSponsor(form, "username.commit.error");
		else
			try {
				Assert.isTrue(makes.contains(form.getCreditCard().getMake()));
				Assert.isTrue(form.getCheckbox());
				Assert.isTrue(form.checkPassword());
				this.sponsorService.save(sponsor);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(sponsor.getUserAccount().getUsername());
				credentials.setPassword(sponsor.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewSponsor(form, "sponsor.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndViewSponsor(final RegisterSponsorForm sponsor) {
		ModelAndView result;

		result = this.createEditModelAndViewSponsor(sponsor, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewSponsor(final RegisterSponsorForm sponsor, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Collection<String> makes = this.configurationService.findConfiguration().getMakes();

		result = new ModelAndView("security/signUpSponsor");
		result.addObject("sponsor", sponsor);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);
		result.addObject("makes", makes);

		return result;
	}

	//Referee
	@RequestMapping(value = "/createReferee", method = RequestMethod.GET)
	public ModelAndView createReferee() {
		final ModelAndView result;
		final RegisterRefereeForm referee = new RegisterRefereeForm();

		result = this.createEditModelAndViewReferee(referee);

		return result;
	}

	@RequestMapping(value = "/saveReferee", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReferee(@ModelAttribute("referee") final RegisterRefereeForm form, final BindingResult binding) {
		ModelAndView result;
		final Referee referee;

		referee = this.refereeService.reconstruct(form, binding);

		final UserAccount ua = this.userAccountService.findByUsername(form.getUsername());

		if (binding.hasErrors())
			result = this.createEditModelAndViewReferee(form);
		else if (ua != null)
			result = this.createEditModelAndViewReferee(form, "username.commit.error");
		else
			try {
				Assert.isTrue(form.getCheckbox());
				Assert.isTrue(form.checkPassword());
				this.refereeService.save(referee);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(referee.getUserAccount().getUsername());
				credentials.setPassword(referee.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewReferee(form, "referee.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndViewReferee(final RegisterRefereeForm referee) {
		ModelAndView result;

		result = this.createEditModelAndViewReferee(referee, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewReferee(final RegisterRefereeForm referee, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpReferee");
		result.addObject("referee", referee);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	//Federation
	@RequestMapping(value = "/createFederation", method = RequestMethod.GET)
	public ModelAndView createFederation() {
		final ModelAndView result;
		final RegisterFederationForm federation = new RegisterFederationForm();

		result = this.createEditModelAndViewFederation(federation);

		return result;
	}

	@RequestMapping(value = "/saveFederation", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsor(@ModelAttribute("federation") final RegisterFederationForm form, final BindingResult binding) {
		ModelAndView result;
		final Federation federation;

		federation = this.federationService.reconstruct(form, binding);

		final UserAccount ua = this.userAccountService.findByUsername(form.getUsername());

		if (binding.hasErrors())
			result = this.createEditModelAndViewFederation(form);
		else if (ua != null)
			result = this.createEditModelAndViewFederation(form, "username.commit.error");
		else
			try {
				Assert.isTrue(form.getCheckbox());
				Assert.isTrue(form.checkPassword());
				this.federationService.save(federation);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(federation.getUserAccount().getUsername());
				credentials.setPassword(federation.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewFederation(form, "federation.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndViewFederation(final RegisterFederationForm federation) {
		ModelAndView result;

		result = this.createEditModelAndViewFederation(federation, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewFederation(final RegisterFederationForm federation, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpFederation");
		result.addObject("federation", federation);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

}
