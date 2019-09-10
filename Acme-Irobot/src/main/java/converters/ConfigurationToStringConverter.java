package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Configuration;

@Component
@Transactional
public class ConfigurationToStringConverter implements Converter<Configuration, String> {

	@Override
	public String convert(Configuration o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}
}
