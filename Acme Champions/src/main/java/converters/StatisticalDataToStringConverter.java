
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.StatisticalData;

@Component
@Transactional
public class StatisticalDataToStringConverter implements Converter<StatisticalData, String> {

	@Override
	public String convert(final StatisticalData statisticalData) {
		String result;

		if (statisticalData == null)
			result = null;
		else
			result = String.valueOf(statisticalData.getId());

		return result;
	}

}
