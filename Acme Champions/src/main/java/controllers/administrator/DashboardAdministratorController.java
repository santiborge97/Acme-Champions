
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;
import services.DashboardService;
import controllers.AbstractController;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configurationService;
	
	@Autowired
	private DashboardService		d;


	// Methods

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView result;

		result = new ModelAndView("administrator/dashboard");

		final String banner = this.configurationService.findConfiguration().getBanner();
		result.addObject("banner",banner);
		
		result.addObject("avgTM",this.d.avgOfTrainingsPerManager());
		result.addObject("minTM",this.d.minOfTrainingsPerManager());
		result.addObject("maxTM",this.d.maxOfTrainingsPerManager());
		result.addObject("stdTM",this.d.stdOfTrainingsPerManager());
		
		result.addObject("avgLT",this.d.avgLengthOfTrainings());
		result.addObject("minLT",this.d.minLengthOfTrainings());
		result.addObject("maxLT",this.d.maxLengthOfTrainings());
		result.addObject("stdLT",this.d.stdLengthOfTrainings());
		
		result.addObject("avgRP",this.d.avgResultsPerFinder());
		result.addObject("minRP",this.d.minResultsPerFinder());
		result.addObject("maxRP",this.d.maxResultsPerFinder());
		result.addObject("stdRP",this.d.stdResultsPerFinder());
		
		result.addObject("rG",this.d.ratioGoalkeepers());
		result.addObject("rD",this.d.ratioDefenders());
		result.addObject("rM",this.d.ratioMidfielders());
		result.addObject("rS",this.d.ratioStrikers());
		
		result.addObject("rMan",this.d.ratioOfManagersWithoutTeam());
		
		result.addObject("sT",this.d.superiorTeams());
		
		// B & A Level
		
		result.addObject("avgMR", this.d.avgMatchesPerReferee());
		result.addObject("minMR", this.d.minMatchesPerReferee());
		result.addObject("maxMR", this.d.maxMatchesPerReferee());
		result.addObject("stdMR", this.d.stdMatchesPerReferee());
		
		result.addObject("avgYP", this.d.avgYellowCardsPerPlayer());
		result.addObject("minYP", this.d.minYellowCardsPerPlayer());
		result.addObject("maxYP", this.d.maxYellowCardsPerPlayer());
		result.addObject("stdYP", this.d.stdYellowCardsPerPlayer());
		
		result.addObject("tP", this.d.topPlayers());
		
		result.addObject("avgTC", this.d.avgTeamsPerCompetition());
		result.addObject("minTC", this.d.minTeamsPerCompetition());
		result.addObject("maxTC", this.d.maxTeamsPerCompetition());
		result.addObject("stdTC", this.d.stdTeamsPerCompetition());
		
		result.addObject("oF", this.d.oldestFederation());

		return result;
	}
}
