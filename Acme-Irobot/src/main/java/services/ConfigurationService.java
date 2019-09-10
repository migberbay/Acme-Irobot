package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.ConfigurationRepository;
import security.LoginService;
import domain.Configuration;


@Service
@Transactional
public class ConfigurationService {

	//Managed Repository -----
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	//Supporting Services -----
	
	@Autowired
	private Validator validator;

	//Simple CRUD methods -----
	public Configuration create(){
		Configuration res = new Configuration();
		return res;
	}
	
	public Collection<Configuration> findAll(){
		return configurationRepository.findAll();
	}
	
	public Configuration findOne(int Id){
		return configurationRepository.findOne(Id);
	}
	
	public Configuration find(){
		return configurationRepository.findAll().iterator().next();
		}
	
	public Configuration save(Configuration a){
		Configuration saved;
		Assert.isTrue(LoginService.hasRole("ADMIN"));
		//a configuration must be unique in the database.
		Assert.isTrue(this.findAll().size()==1);
		Assert.isTrue(this.findAll().toArray()[0].equals(a));
		saved = configurationRepository.saveAndFlush(a);
		return saved;
	}
	
	//TODO: this methos shouldn't even exist.
	public void delete(Configuration a){
		LoginService.hasRole("ADMIN");
		configurationRepository.delete(a);
	}
	
	//Other business methods -----
	
//	public Configuration reconstruct(ConfigurationForm configurationForm, BindingResult binding){
//		Configuration result;
//		Word a = wordService.create();
//		result = this.find();
//		result.setBanner(configurationForm.getBanner());
//		result.setDefaultPhoneCode(configurationForm.getDefaultPhoneCode());
//		result.setFinderCacheTime(configurationForm.getFinderCacheTime());
//
//		result.setFinderQueryResults(configurationForm.getFinderQueryResults());
//		result.setWelcomeTextEnglish(configurationForm.getWelcomeTextEnglish());
//		result.setWelcomeTextSpanish(configurationForm.getWelcomeTextSpanish());
//		result.setSystemName(configurationForm.getSystemName());
//		result.setVatPercentage(configurationForm.getVatPercentage());
//		result.setSponsorshipFare(configurationForm.getSponsorshipFare());
//		result.setFreeFunds(configurationForm.getFreeFunds());
//		if(!(configurationForm.getWordEnglishName().isEmpty()) && !(configurationForm.getWordSpanishName().isEmpty())){
//			a.setEnglishName(configurationForm.getWordEnglishName());
//			a.setSpanishName(configurationForm.getWordSpanishName());
//			result.getspamWords().add(wordService.save(a));
//		}
//
//		validator.validate(result, binding);
//		if(binding.hasErrors()){
//			throw new ValidationException();
//		}
//		return result;
//	}
//	
//	public ConfigurationForm construct(Configuration conf){
//		ConfigurationForm result = new ConfigurationForm();		
//		result.setBanner(conf.getBanner());
//		result.setDefaultPhoneCode(conf.getDefaultPhoneCode());
//		result.setFinderCacheTime(conf.getFinderCacheTime());
//		result.setFinderQueryResults(conf.getFinderQueryResults());
//
//		result.setVatPercentage(conf.getVatPercentage());
//		result.setSponsorshipFare(conf.getSponsorshipFare());
//		result.setFreeFunds(conf.getFreeFunds());
//		result.setSystemName(conf.getSystemName());
//		result.setWelcomeTextEnglish(conf.getWelcomeTextEnglish());
//		result.setWelcomeTextSpanish(conf.getWelcomeTextSpanish());
//		result.setSpamWords(conf.getspamWords());
//		return result;
//	}

	
}