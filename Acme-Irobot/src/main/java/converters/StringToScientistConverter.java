package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ScientistRepository;

import domain.Scientist;



@Component
@Transactional
public class StringToScientistConverter implements Converter<String,Scientist> {

	@Autowired
	ScientistRepository repository;
	
	@Override
	public Scientist convert(String s) {
		Scientist res;
		int id;
		
		try {
			if(StringUtils.isEmpty(s))
				res=null;
			else{
				id = Integer.valueOf(s);
				res = repository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
