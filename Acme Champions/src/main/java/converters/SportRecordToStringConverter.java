
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.SportRecord;

@Component
@Transactional
public class SportRecordToStringConverter implements Converter<SportRecord, String> {

	@Override
	public String convert(final SportRecord sportRecord) {
		String result;

		if (sportRecord == null)
			result = null;
		else
			result = String.valueOf(sportRecord.getId());

		return result;
	}

}
