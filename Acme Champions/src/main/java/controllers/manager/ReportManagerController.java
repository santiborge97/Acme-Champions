
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.ManagerService;
import services.PlayerService;
import services.ReportService;
import controllers.AbstractController;
import domain.Manager;
import domain.Report;
import forms.ReportForm;

@Controller
@RequestMapping("/report/manager")
public class ReportManagerController extends AbstractController {

	@Autowired
	private ReportService			reportService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Report> reports;
		final Manager manag;

		manag = this.managerService.findByPrincipal();

		if (manag.getTeam() != null)
			reports = this.reportService.findByTeamId(manag.getTeam().getId());
		else
			reports = null;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("report/list");
		result.addObject("requestUri", "/report/manager/list.do");
		result.addObject("reports", reports);
		result.addObject("banner", banner);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int playerId) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.playerService.exist(playerId);

		if (exist) {

			final Boolean security = this.reportService.securityPlayer(playerId);

			if (security) {
				final ReportForm form = new ReportForm();
				form.setPlayerId(playerId);

				result = this.createEditModelAndView(form);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("report") final ReportForm form, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.playerService.exist(form.getPlayerId());

		if (exist) {

			final Report reportReconstruct = this.reportService.reconstruct(form, binding);

			final Boolean security = this.reportService.securityPlayer(form.getPlayerId());

			if (security) {
				if (binding.hasErrors())
					result = this.createEditModelAndView(form);
				else
					try {
						this.reportService.save(reportReconstruct);
						result = new ModelAndView("redirect:/report/manager/list.do");
					} catch (final Throwable oops) {
						result = this.createEditModelAndView(form, "report.commit.error");
					}
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int reportId) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.reportService.exist(reportId);

		if (exist) {

			final Report report = this.reportService.findOne(reportId);

			final Boolean security = this.reportService.securityPlayer(report.getPlayer().getId());

			if (security)
				try {
					this.reportService.delete(report);
					result = new ModelAndView("redirect:/report/manager/list.do");
				} catch (final Throwable oops) {
					result = new ModelAndView("redirect:/report/manager/list.do");
				}
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;

	}

	//Ancillary methods---------------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ReportForm report) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ReportForm report, final String messageCode) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("report/create");
		result.addObject("report", report);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);
		return result;
	}

}
