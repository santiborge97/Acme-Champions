
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.PlayerRecord;

@Component
@Transactional
public class PlayerRecordToStringConverter implements Converter<PlayerRecord, String> {

	@Override
	public String convert(final PlayerRecord playerRecord) {
		String result;

		if (playerRecord == null)
			result = null;
		else
			result = String.valueOf(playerRecord.getId());

		return result;
	}

}
