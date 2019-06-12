
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Hiring;

@Component
@Transactional
public class HiringToStringConverter implements Converter<Hiring, String> {

	@Override
	public String convert(final Hiring hiring) {
		String result;

		if (hiring == null)
			result = null;
		else
			result = String.valueOf(hiring.getId());

		return result;
	}

}
