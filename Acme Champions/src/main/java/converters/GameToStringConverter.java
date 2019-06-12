
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Game;

@Component
@Transactional
public class GameToStringConverter implements Converter<Game, String> {

	@Override
	public String convert(final Game game) {
		String result;

		if (game == null)
			result = null;
		else
			result = String.valueOf(game.getId());

		return result;
	}

}
