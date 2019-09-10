package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Irobot;

@Component
@Transactional
public class IrobotToStringConverter implements Converter<Irobot, String> {

	@Override
	public String convert(Irobot o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}
}
