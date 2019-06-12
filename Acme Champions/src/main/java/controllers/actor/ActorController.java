
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;
import services.FederationService;
import services.ManagerService;
import services.PlayerService;
import services.PresidentService;
import services.RefereeService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Federation;
import domain.Manager;
import domain.Player;
import domain.President;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services-----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private PresidentService		presidentService;

	@Autowired
	private FederationService		federationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Actor actor = this.actorService.findByPrincipal();

		Collection<Player> players;
		Collection<Federation> federations;
		Collection<Administrator> administrators;
		Collection<President> presidents;
		Collection<Referee> referees;
		Collection<Manager> managers;
		Collection<Sponsor> sponsors;

		players = this.playerService.findAll();
		federations = this.federationService.findAll();
		administrators = this.administratorService.findAll();
		presidents = this.presidentService.findAll();
		referees = this.refereeService.findAll();
		sponsors = this.sponsorService.findAll();
		managers = this.managerService.findAll();

		if (players.contains(actor))
			players.remove(actor);
		else if (federations.contains(actor))
			federations.remove(actor);
		else if (administrators.contains(actor))
			administrators.remove(actor);
		else if (presidents.contains(actor))
			presidents.remove(actor);
		else if (referees.contains(actor))
			referees.remove(actor);
		else if (managers.contains(actor))
			managers.remove(actor);
		else if (sponsors.contains(actor))
			sponsors.remove(actor);

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/list");
		result.addObject("players", players);
		result.addObject("federations", federations);
		result.addObject("administrators", administrators);
		result.addObject("presidents", presidents);
		result.addObject("referees", referees);
		result.addObject("managers", managers);
		result.addObject("sponsors", sponsors);
		result.addObject("banner", banner);

		result.addObject("requestURI", "actor/list.do");

		return result;

	}

}
