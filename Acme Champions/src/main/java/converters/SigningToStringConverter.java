
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Signing;

@Component
@Transactional
public class SigningToStringConverter implements Converter<Signing, String> {

	@Override
	public String convert(final Signing signing) {
		String result;

		if (signing == null)
			result = null;
		else
			result = String.valueOf(signing.getId());

		return result;
	}

}
