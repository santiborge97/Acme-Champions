
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Federation;

@Component
@Transactional
public class FederationToStringConverter implements Converter<Federation, String> {

	@Override
	public String convert(final Federation federation) {
		String result;

		if (federation == null)
			result = null;
		else
			result = String.valueOf(federation.getId());

		return result;
	}

}
