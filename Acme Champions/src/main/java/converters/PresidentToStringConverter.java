
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.President;

@Component
@Transactional
public class PresidentToStringConverter implements Converter<President, String> {

	@Override
	public String convert(final President president) {
		String result;

		if (president == null)
			result = null;
		else
			result = String.valueOf(president.getId());

		return result;
	}

}
