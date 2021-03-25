package com.project.service;

import java.util.Optional;

//import org.junit.Test.None;
import org.junit.jupiter.api.Assertions;
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

import com.project.exception.BusinessRuleException;
import com.project.model.entity.User;
import com.project.model.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserServiceTest {

//	@Autowired
//	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TestEntityManager testEntityManager;
	
//	@Test
//	public void mustValidateEmail(){
//		//cenário
//		userRepository.deleteAll();
//		
//		//ação
//		userService.validateUser("user@email.com");
//		
//		//verificação
//		Assertions.assertThrows(None.class,
//	            ()->{
//	                //do whatever you want to do here
//	                //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
//	                });
//	}
	
//	@Test
//	public void mustNotValidateEmail() {
//		//cenário
//		User user = User.builder().name("user").email("user@email.com").password("1234").build();
//		userRepository.save(user);
//		
//		//ação
//		userService.validateUser(user.getEmail());
//		
//		//verificação
//		Assertions.assertThrows(BusinessRuleException.class,
//	            ()->{
//	                //do whatever you want to do here
//	                //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
//	                });
//	}
//	@Test
//	public void verifyDate() {
//		Optional<User> user = userRepository.findByName("gabriel");
//		System.out.println(user.get().getSingUpDate());
//	}
}
