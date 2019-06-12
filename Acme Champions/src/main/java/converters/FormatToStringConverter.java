
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Format;

@Component
@Transactional
public class FormatToStringConverter implements Converter<Format, String> {

	@Override
	public String convert(final Format format) {
		String result;

		if (format == null)
			result = null;
		else
			result = String.valueOf(format.getId());

		return result;
	}

}
