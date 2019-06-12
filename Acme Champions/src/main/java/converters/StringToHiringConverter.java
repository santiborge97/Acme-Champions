
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.HiringRepository;
import domain.Hiring;

@Component
@Transactional
public class StringToHiringConverter implements Converter<String, Hiring> {

	@Autowired
	private HiringRepository	hiringRepository;


	@Override
	public Hiring convert(final String text) {
		Hiring result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.hiringRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
