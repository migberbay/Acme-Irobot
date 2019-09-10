package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Purchase;

@Component
@Transactional
public class PurchaseToStringConverter implements Converter<Purchase, String> {

	@Override
	public String convert(Purchase o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}
}
