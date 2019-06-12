
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Player;

@Component
@Transactional
public class PlayerToStringConverter implements Converter<Player, String> {

	@Override
	public String convert(final Player player) {
		String result;

		if (player == null)
			result = null;
		else
			result = String.valueOf(player.getId());

		return result;
	}

}
