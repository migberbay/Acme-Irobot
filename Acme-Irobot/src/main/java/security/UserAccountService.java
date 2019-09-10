
package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAccountService {

	//Managed Repository -----
	@Autowired
	private UserAccountRepository	userAccountRepository;


	//Supporting Services -----

	//@Autowired
	//private SomeService serviceName 

	//Simple CRUD methods -----
	public UserAccount create(final String username, final String hashedPassword, final String authority) {
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		final Authority auth = new Authority();
		auth.setAuthority(authority);
		final List<Authority> auths = new ArrayList<>();
		auths.add(auth);

		final UserAccount res = new UserAccount();
		res.setAuthorities(auths);
		res.setUsername(username);
		res.setPassword(hashedPassword);
		return res;
	}

	public UserAccount create() {
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.		
		final UserAccount res = new UserAccount();
		res.setAuthorities(new ArrayList<Authority>());

		return res;
	}

	public Collection<UserAccount> findAll() {
		return this.userAccountRepository.findAll();
	}

	public UserAccount findOne(final int Id) {
		return this.userAccountRepository.findOne(Id);
	}

	public UserAccount save(final UserAccount a) {
		UserAccount saved;
		final String pass = a.getPassword();
		String hashedPass;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		hashedPass = encoder.encodePassword(pass, null);
		a.setPassword(hashedPass);
		saved = this.userAccountRepository.saveAndFlush(a);
		return saved;
	}

	public void delete(final UserAccount a) {

		this.userAccountRepository.delete(a);
	}

	//Other business methods -----
	public UserAccount register(final String username, final String hashedPassword, final String type) {
		//type can be either CUSTOMER, HANDYWORKER or SPONSOR(select)
		final UserAccount nueva = this.create(username, hashedPassword, type);
		this.save(nueva);
		return nueva;
	}

	public UserAccount findByUsername(final String username) {
		return this.userAccountRepository.findByUsername(username);
	}
}
