
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.SigningRepository;
import domain.Signing;

@Component
@Transactional
public class StringToSigningConverter implements Converter<String, Signing> {

	@Autowired
	private SigningRepository	signingRepository;


	@Override
	public Signing convert(final String text) {
		Signing result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.signingRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
