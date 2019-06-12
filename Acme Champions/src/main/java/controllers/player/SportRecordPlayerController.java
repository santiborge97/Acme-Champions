
package controllers.player;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.HistoryService;
import services.PlayerService;
import services.SportRecordService;
import controllers.AbstractController;
import domain.History;
import domain.Player;
import domain.SportRecord;

@Controller
@RequestMapping("/sportRecord/player")
public class SportRecordPlayerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private SportRecordService		sportRecordService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final SportRecord sportRecord;

		final Player player = this.playerService.findByPrincipal();
		final History history = this.historyService.findByPlayerId(player.getId());

		if (history != null) {

			sportRecord = new SportRecord();

			result = this.createEditModelAndView(sportRecord);

		} else
			result = new ModelAndView("redirect:/history/player/create.do");

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sportRecordId) {
		ModelAndView result;
		final SportRecord record;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.sportRecordService.exist(sportRecordId);

		if (!(sportRecordId != 0 && exist)) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			record = this.sportRecordService.findOne(sportRecordId);

			final Boolean security = this.sportRecordService.security(sportRecordId);

			if (security)
				result = this.createEditModelAndView(record);
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SportRecord record, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.sportRecordService.exist(record.getId());

		if (record.getId() == 0 || exist) {

			final Boolean security = this.sportRecordService.security(record.getId());

			if (record.getId() == 0 || security) {

				if (binding.hasErrors())
					result = this.createEditModelAndView(record);
				else
					try {
						if (record.getId() == 0) {
							final Player player = this.playerService.findByPrincipal();
							final History history = this.historyService.findByPlayerId(player.getId());
							final SportRecord r = this.sportRecordService.save(record);
							history.getSportRecords().add(r);
							this.historyService.save(history);

						} else
							this.sportRecordService.save(record);

						result = new ModelAndView("redirect:/history/player/display.do");

					} catch (final Throwable oops) {
						result = this.createEditModelAndView(record, "history.commit.error");
					}
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final SportRecord record, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.sportRecordService.exist(record.getId());

		if (exist) {

			final Boolean security = this.sportRecordService.security(record.getId());

			if (security) {

				if (binding.hasErrors())
					result = this.createEditModelAndView(record);
				else
					try {

						final Player player = this.playerService.findByPrincipal();
						final History history = this.historyService.findByPlayerId(player.getId());
						history.getSportRecords().remove(record);
						this.historyService.save(history);
						this.sportRecordService.delete(record);

						result = new ModelAndView("redirect:/history/player/display.do");

					} catch (final Throwable oops) {
						result = this.createEditModelAndView(record, "history.commit.error");
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

	protected ModelAndView createEditModelAndView(final SportRecord sportRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(sportRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SportRecord sportRecord, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("history/editSportRecord");
		result.addObject("sportRecord", sportRecord);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
