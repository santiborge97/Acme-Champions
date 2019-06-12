
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Competition;

@Component
@Transactional
public class CompetitionToStringConverter implements Converter<Competition, String> {

	@Override
	public String convert(final Competition competition) {
		String result;

		if (competition == null)
			result = null;
		else
			result = String.valueOf(competition.getId());

		return result;
	}

}
