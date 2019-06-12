
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.TrainingRepository;
import domain.Training;

@Component
@Transactional
public class StringToTrainingConverter implements Converter<String, Training> {

	@Autowired
	private TrainingRepository	trainingRepository;


	@Override
	public Training convert(final String text) {
		Training result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.trainingRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
