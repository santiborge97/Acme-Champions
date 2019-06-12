
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
import services.PlayerRecordService;
import services.PlayerService;
import controllers.AbstractController;
import domain.History;
import domain.Player;
import domain.PlayerRecord;

@Controller
@RequestMapping("/playerRecord/player")
public class PlayerRecordPlayerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private PlayerRecordService		playerRecordService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final PlayerRecord playerRecord;

		final Player player = this.playerService.findByPrincipal();
		final History history = this.historyService.findByPlayerId(player.getId());

		if (history != null) {

			playerRecord = new PlayerRecord();

			result = this.createEditModelAndView(playerRecord);

		} else
			result = new ModelAndView("redirect:/history/player/create.do");

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int playerRecordId) {
		ModelAndView result;
		final PlayerRecord record;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.playerRecordService.exist(playerRecordId);

		if (!(playerRecordId != 0 && exist)) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			record = this.playerRecordService.findOne(playerRecordId);

			final Boolean security = this.playerRecordService.security(playerRecordId);

			if (security)
				result = this.createEditModelAndView(record);
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PlayerRecord record, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.playerRecordService.exist(record.getId());

		if (record.getId() == 0 || exist) {

			final Boolean security = this.playerRecordService.security(record.getId());

			if (record.getId() == 0 || security) {

				if (binding.hasErrors())
					result = this.createEditModelAndView(record);
				else
					try {
						if (record.getId() == 0) {
							final Player player = this.playerService.findByPrincipal();
							final History history = this.historyService.findByPlayerId(player.getId());
							final PlayerRecord p = this.playerRecordService.save(record);
							history.getPlayerRecords().add(p);
							this.historyService.save(history);

						} else
							this.playerRecordService.save(record);

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
	public ModelAndView delete(@Valid final PlayerRecord record, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.playerRecordService.exist(record.getId());

		if (exist) {

			final Boolean security = this.playerRecordService.security(record.getId());

			if (security) {

				if (binding.hasErrors())
					result = this.createEditModelAndView(record);
				else
					try {

						final Player player = this.playerService.findByPrincipal();
						final History history = this.historyService.findByPlayerId(player.getId());
						history.getPlayerRecords().remove(record);
						this.historyService.save(history);
						this.playerRecordService.delete(record);

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

	protected ModelAndView createEditModelAndView(final PlayerRecord playerRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(playerRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PlayerRecord playerRecord, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("history/editPlayerRecord");
		result.addObject("playerRecord", playerRecord);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
