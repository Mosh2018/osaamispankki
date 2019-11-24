package com.netum.osaamispankki;

import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.domain.UserCompany;
import com.netum.osaamispankki.user.repository.UserCompanyRepository;
import com.netum.osaamispankki.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.netum.osaamispankki.user.modals.Role.ROLE_MASTER;

@SpringBootApplication
@EnableJpaRepositories
public class OsaamispankkiApplication {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(OsaamispankkiApplication.class, args);
	}
}

@Component
class initMasterData implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserCompanyRepository userCompanyRepository;

	@Override
	public void run(String... args) throws Exception {
		try {
			User user = new User();
			user.setFirstName("Muhammed");
			user.setSurname("Al-amiir");
			user.setEnabled(true);
			user.setPassword("$2a$10$Js.uENG1vvRtWiJfjfu8SexyEUd4UpLhSoMJ/ZzFPpV7hl2BlZ.sq");
			user.setUsername("mosh-is-master");
			user.setEmail("moshtampere@gmail.com");
			user.setPhoneNo("089 90906778");
			userRepository.save(user);

			UserCompany userCompany = new UserCompany();
			userCompany.setRole(ROLE_MASTER);
			userCompany.setCompany_name("OWN_COMPANY");
			userCompany.setPosition("MASTER-ADMIN");
			userCompany.setUser(user);
			userCompanyRepository.save(userCompany);
		} catch (Exception e) {
			throw new Exception();
		}
	}
}
