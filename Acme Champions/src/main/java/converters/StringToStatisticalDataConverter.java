
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.StatisticalDataRepository;
import domain.StatisticalData;

@Component
@Transactional
public class StringToStatisticalDataConverter implements Converter<String, StatisticalData> {

	@Autowired
	private StatisticalDataRepository	statisticalDataRepository;


	@Override
	public StatisticalData convert(final String text) {
		StatisticalData result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.statisticalDataRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
