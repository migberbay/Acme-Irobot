package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Scientist;

@Component
@Transactional
public class ScientistToStringConverter implements Converter<Scientist, String> {

	@Override
	public String convert(Scientist o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}
}
