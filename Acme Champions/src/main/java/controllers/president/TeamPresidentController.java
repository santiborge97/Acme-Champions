
package controllers.president;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ConfigurationService;
import services.PresidentService;
import services.SponsorshipService;
import services.TeamService;
import controllers.AbstractController;
import domain.Actor;
import domain.President;
import domain.Sponsorship;
import domain.Team;

@Controller
@RequestMapping("/team/president")
public class TeamPresidentController extends AbstractController {

	@Autowired
	private PresidentService		presidentService;

	@Autowired
	private TeamService				teamService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private ConfigurationService	configurationService;


	//Create-----------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Actor actor = this.actorService.findByPrincipal();
		final Authority authority = new Authority();
		authority.setAuthority("PRESIDENT");

		final Team t = this.teamService.findTeamByPresidentId(actor.getId());

		if (actor.getUserAccount().getAuthorities().contains(authority) && t == null) {
			final Team team = this.teamService.create();
			result = this.createEditModelAndView(team, null);
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("banner", banner);
		}
		return result;

	}

	//Edit-------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Boolean security;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final President president = this.presidentService.findByPrincipal();

		final Team teamFind = this.teamService.findTeamByPresidentId(president.getId());

		if (teamFind == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			security = this.teamService.teamPresidentSecurity(teamFind.getId());

			if (security)
				result = this.createEditModelAndView(teamFind, null);
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute(value = "team") Team team, final BindingResult binding) {
		ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (team.getId() != 0 && this.teamService.findOne(team.getId()) == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else if ((team.getId() != 0 && this.teamService.findOne(team.getId()).getPresident() != this.presidentService.findByPrincipal()))
			result = new ModelAndView("redirect:/welcome/index.do");
		else {

			team = this.teamService.reconstruct(team, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(team, null);
			else
				try {
					this.teamService.save(team);
					result = new ModelAndView("redirect:display.do");
				} catch (final Throwable oops) {
					if (oops.getMessage() == "Invalid Dates")
						result = this.createEditModelAndView(team, "url.error");
					else
						result = this.createEditModelAndView(team, "team.commit.error");
				}
		}
		return result;
	}

	//Display------------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Boolean security;

		final President president = this.presidentService.findByPrincipal();

		final Team teamFind = this.teamService.findTeamByPresidentId(president.getId());
		final String banner = this.configurationService.findConfiguration().getBanner();

		Boolean existTeam = false;

		if (teamFind == null) {
			//			result = new ModelAndView("misc/notExist");
			result = new ModelAndView("team/display");
			result.addObject("banner", banner);
			result.addObject("existTeam", existTeam);

		} else {
			security = this.teamService.teamPresidentSecurity(teamFind.getId());

			if (security) {

				existTeam = true;

				result = new ModelAndView("team/display");
				result.addObject("team", teamFind);
				result.addObject("banner", banner);
				result.addObject("existTeam", existTeam);

				//Esto es para ver los sponsorships
				final Collection<Sponsorship> sponsorships = this.sponsorshipService.findSponsorshipsByTeamId(teamFind.getId());
				if (sponsorships != null && !sponsorships.isEmpty())
					result.addObject("sponsorships", sponsorships);

			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	//Ancillary methods---------------------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Team team, final String messageCode) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("team/edit");
		result.addObject("team", team);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		return result;
	}

}
