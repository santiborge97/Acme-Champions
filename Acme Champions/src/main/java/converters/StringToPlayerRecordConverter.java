
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.PlayerRecordRepository;
import domain.PlayerRecord;

@Component
@Transactional
public class StringToPlayerRecordConverter implements Converter<String, PlayerRecord> {

	@Autowired
	private PlayerRecordRepository	playerRecordRepository;


	@Override
	public PlayerRecord convert(final String text) {
		PlayerRecord result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.playerRecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
