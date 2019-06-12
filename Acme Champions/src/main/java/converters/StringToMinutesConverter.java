
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.MinutesRepository;
import domain.Minutes;

@Component
@Transactional
public class StringToMinutesConverter implements Converter<String, Minutes> {

	@Autowired
	private MinutesRepository	minutesRepository;


	@Override
	public Minutes convert(final String text) {
		Minutes result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.minutesRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
