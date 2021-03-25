package com.project.api.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.dto.ChangePasswordDTO;
import com.project.api.dto.ResponseUserDTO;
import com.project.api.dto.UserDTO;
import com.project.config.JwtTokenUtil;
import com.project.exception.AuthenticationException;
import com.project.exception.BusinessRuleException;
import com.project.model.entity.User;
import com.project.service.UserService;

@RestController
@CrossOrigin //para habilitar cors
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/signUp")
	public ResponseEntity signUp(@RequestBody UserDTO userDto) throws NoSuchAlgorithmException, UnsupportedEncodingException{

		User user = User.builder()
				.name(userDto.getName())
				.email(userDto.getEmail())
				.password(userDto.getPasswd())
				.build();
		 
		try {
			user = userService.saveUser(user);
			return new ResponseEntity(user.getId(), HttpStatus.CREATED);
		}catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@PostMapping("/auth")
	public ResponseEntity authUser(@RequestBody UserDTO userDto) {
		try {
			User user = userService.authUser(userDto.getEmail(), userDto.getPasswd());
			ResponseUserDTO responseUser = ResponseUserDTO.builder()
					.id(user.getId())
					.name(user.getName())
					.email(user.getEmail())
					.build();
			return ResponseEntity.ok(responseUser);
		}
		catch(AuthenticationException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
			
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity getAllUsers() {
		try {
			List<User> users = userService.getAllUsers();
			return ResponseEntity.ok(users);
		} catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("/changePassword")
	public ResponseEntity signUp(@RequestHeader("Authorization") String token,
			@RequestBody ChangePasswordDTO changePasswordDto)
	{
		token = token.substring(7);
		String email = jwtTokenUtil.getUsernameFromToken(token);
		System.out.println("email " + email);
		User user = userService.findByEmail(email);
		if(!userService.checkPassword(user.getPassword(), changePasswordDto.getCurrentPassword())) {
			return ResponseEntity.badRequest().body("Senha atual incorreta");
		} 
		if(user.getPassword().equals(changePasswordDto.getNewPassword())) {
			return ResponseEntity.badRequest().body("A nova senha não pode ser a mesma da atual");
		} 
		
		user.setPassword(changePasswordDto.getNewPassword());
		
		try {
			user = userService.updateUserPassword(user);
			return new ResponseEntity(user.getId(), HttpStatus.OK);
		}catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/getUserFromHash")
	public ResponseEntity searchEntries(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "hash") String hash)
	{
		try {
			User user = userService.findByEmailAndHash(email, hash);
			ResponseUserDTO responseUserDTO = ResponseUserDTO.builder()
					.email(user.getEmail())
					.build();
			return new ResponseEntity(responseUserDTO, HttpStatus.OK);
		}catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/redefinePassword")
	public ResponseEntity redefinePassword( @RequestParam(value = "email") String email,
										 	@RequestParam(value = "hash") String hash,
											@RequestBody UserDTO userDTO) {
		
		try {
			User user = userService.findByEmailAndHash(email, hash);
			user.setPassword(userDTO.getPasswd());
			userService.updateUserPassword(user);
			return new ResponseEntity(HttpStatus.OK);
		}catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
		
}
