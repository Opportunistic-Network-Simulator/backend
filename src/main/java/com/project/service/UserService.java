package com.project.service;

import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.exception.AuthenticationException;
import com.project.exception.BusinessRuleException;
import com.project.model.entity.User;
import com.project.model.repository.UserRepository;

@Service
public class UserService{

	@Autowired
	UserRepository userRepository;
	
	public User authUser(String email, String password) {
		Optional<User> user = userRepository.findByEmail(email);
		
		if(!user.isPresent()) {
			throw new AuthenticationException("Email não cadastrado");
		}
		if(!checkPassword(user.get().getPassword(), password)) {
			throw new AuthenticationException("Senha incorreta");
		}
		
		return user.get();
	}

	@Transactional
	public User saveUser(User user) {
		this.validateEmail(user.getEmail());
		this.validateName(user.getName());
		LocalDateTime date = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(date);
		user.setSingUpDate(timestamp);
		user.setPassword(encryptPassword(user.getPassword()));
		return userRepository.save(user);
	}


	public void validateEmail(String email) {
		boolean exists = userRepository.existsByEmail(email);
		if(exists) {
			throw new BusinessRuleException("Email já cadastrado");
		}
		
	}
	
	public void validateName(String name) {
		boolean exists = userRepository.existsByName(name);
		if(exists) {
			throw new BusinessRuleException("Nome já cadastrado");
		}
		
	}
	

	 
	public Optional<User> findById(Long Id) {
		Optional<User> user = userRepository.findById(Id);
		if(!user.isPresent()) {
			throw new BusinessRuleException("Usuário de lançamento não existente");
		}
		return user;
		
	}
	
	 
	public User findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(!user.isPresent()) {
			throw new BusinessRuleException("Usuário não existente");
		}
		return user.get();
		
	}
	 
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	 
	@Transactional
	public User updateUserPassword(User user) {
		user.setPassword(encryptPassword(user.getPassword()));
		if(user.getRecoverPasswordHash() != null) {
			user.setRecoverPasswordHash(null);
		}
		return userRepository.save(user);
	}

	 
	public String encryptPassword(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new BusinessRuleException(e.getMessage());
		}
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();

        for (byte b : digest)
            sb.append(String.format("%02x", b & 0xff));
        
		return sb.toString();
	}

	 
	public User findByEmailAndHash(String email, String hash) {
		Optional<User> user = userRepository.findByEmailAndRecoverPasswordHash(email, hash);
		if(!user.isPresent()) {
			throw new BusinessRuleException("Solicitação de recuperação de senha não encontrada");
		}
		return user.get();
	}

	
	public boolean checkPassword(String userPassword, String typedPassword) {
		return userPassword.equals(encryptPassword(typedPassword));
	}
}
