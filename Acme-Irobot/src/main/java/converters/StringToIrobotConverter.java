package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.IrobotRepository;

import domain.Irobot;



@Component
@Transactional
public class StringToIrobotConverter implements Converter<String,Irobot> {

	@Autowired
	IrobotRepository repository;
	
	@Override
	public Irobot convert(String s) {
		Irobot res;
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
