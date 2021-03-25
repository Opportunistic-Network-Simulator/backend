package com.project.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.model.entity.User;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TestEntityManager testEntityManager;
	
	@Test
	public void verifyExistingEmail() {
		//cenário
		User user = User.builder().name("gabriel").email("gabriel@gmail.com").build();
		testEntityManager.persist(user);
		
		//ação/execução
		boolean exists = userRepository.existsByEmail(user.getEmail());
		
		//verificação
		Assertions.assertThat(exists).isTrue();
	}
	
	@Test
	public void verifyUnexistingEmail() {
		//cenário
								
		//ação/execução
		boolean exists = userRepository.existsByEmail("gabriel@gmail.com");
		
		//verificação
		Assertions.assertThat(exists).isFalse();
	}
}
