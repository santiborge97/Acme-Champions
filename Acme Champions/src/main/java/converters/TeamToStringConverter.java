
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Team;

@Component
@Transactional
public class TeamToStringConverter implements Converter<Team, String> {

	@Override
	public String convert(final Team team) {
		String result;

		if (team == null)
			result = null;
		else
			result = String.valueOf(team.getId());

		return result;
	}

}
