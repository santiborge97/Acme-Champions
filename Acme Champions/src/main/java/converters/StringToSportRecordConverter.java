
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.SportRecordRepository;
import domain.SportRecord;

@Component
@Transactional
public class StringToSportRecordConverter implements Converter<String, SportRecord> {

	@Autowired
	private SportRecordRepository	sportRecordRepository;


	@Override
	public SportRecord convert(final String text) {
		SportRecord result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.sportRecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
