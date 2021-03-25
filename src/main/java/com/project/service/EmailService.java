//package com.project.service;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.sql.Timestamp;
//
//import javax.mail.internet.MimeMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import com.project.exception.BusinessRuleException;
//import com.project.model.entity.User;
//import com.project.model.repository.UserRepository;
//import com.project.service.UserService;
//
//@Service
//public class EmailService{
//	
//	@Autowired
//	private JavaMailSender mailSender;
//	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private UserRepository userRepository;
//	
//	
//	public String sendRecoverPasswordEmail(User user, String url) {
//		
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		String password = user.getEmail() + timestamp.toString();
//		
//		String hash = userService.encryptPassword(password);
//		user.setRecoverPasswordHash(hash);
//		userRepository.save(user);
//		
//		String emailContent = url + "/" + user.getEmail() + "/" + hash;
//		try {
//			 MimeMessage mail = mailSender.createMimeMessage();
//		
//		     MimeMessageHelper helper = new MimeMessageHelper( mail );
//		     helper.setTo( user.getEmail() );
//		     helper.setSubject( "Recuperação de Senha" );
//		     helper.setText("<p>Acesse o link abaixo para redifinir sua senha: <p> <p>" + emailContent +"</p>", true);
//		     mailSender.send(mail);
//		} catch (Exception e) {
//            e.printStackTrace();
//        }
//		return emailContent;
//		
//	}
//	
//	public String hashRecoverPassword(String email, String password) {
//		
//		return password;
//			
//	}
//
//}
