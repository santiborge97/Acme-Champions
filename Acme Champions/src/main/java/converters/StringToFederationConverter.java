
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.FederationRepository;
import domain.Federation;

@Component
@Transactional
public class StringToFederationConverter implements Converter<String, Federation> {

	@Autowired
	private FederationRepository	federationRepository;


	@Override
	public Federation convert(final String text) {
		Federation result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.federationRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
