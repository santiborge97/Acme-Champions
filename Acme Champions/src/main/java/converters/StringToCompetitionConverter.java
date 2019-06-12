
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.CompetitionRepository;
import domain.Competition;

@Component
@Transactional
public class StringToCompetitionConverter implements Converter<String, Competition> {

	@Autowired
	private CompetitionRepository	competitionRepository;


	@Override
	public Competition convert(final String text) {
		Competition result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.competitionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
