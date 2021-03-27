//package com.project.api.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.project.api.dto.JwtRequest;
//import com.project.api.dto.JwtResponse;
//import com.project.api.dto.ResponseUserDTO;
//import com.project.api.dto.SessionInfoDTO;
//import com.project.config.JwtTokenUtil;
//import com.project.exception.AuthenticationException;
//import com.project.model.entity.User;
//import com.project.service.JwtUserDetailsService;
//import com.project.service.UserService;
//
//import io.jsonwebtoken.ExpiredJwtException;
//
//
//@RestController
//@CrossOrigin
//@RequestMapping("/api/jwt")
//public class JwtAuthenticationController {
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//	
//	@Autowired
//	private JwtUserDetailsService userDetailsService;
//	
//	@Autowired
//	private UserService userService;
//	
//	@PostMapping("/authenticate")
//	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//		
////		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//		try {
//			User user = userService.authUser(authenticationRequest.getEmail(), authenticationRequest.getPasswd());
//			ResponseUserDTO responseUser = ResponseUserDTO.builder()
//					.id(user.getId())
//					.name(user.getName())
//					.email(user.getEmail())
//					.build();
//			
//			final UserDetails userDetails = userDetailsService
//					.loadUserByUsername(authenticationRequest.getEmail());
//			
//			final String token = jwtTokenUtil.generateToken(userDetails);
//			
//			return ResponseEntity.ok(new JwtResponse( new SessionInfoDTO(responseUser, token)));
//		}
//		catch(AuthenticationException e){
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//		
//	}
//	
//	@GetMapping("/checkSession")
//	public ResponseEntity<?> checkTokenExpirationTime(){
//			//se token tiver expirado, JwtRequestFilter já vai lançar a exceção 401
//			return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
//		
//	}
//	
////	private void authenticate(String username, String password) throws Exception {
////		System.out.println("entrou no auth");
////	try {
////		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
////		System.out.println("try do auth");
////	} catch (DisabledException e) {
////		System.out.println("disabled");
////		throw new Exception("USER_DISABLED", e);
////	} catch (BadCredentialsException e) {
////		System.out.println("invalid");
////		throw new Exception("INVALID_CREDENTIALS", e);
////	}
////	}
//}