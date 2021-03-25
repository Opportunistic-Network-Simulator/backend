package com.project.model.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
//@Getter
//@Setter
//@AllArgsConstructor(access = AccessLevel.PUBLIC)
//@ToString
//@EqualsAndHashCode
//@RequiredArgsConstructor
@Data //substitui todos acima
@Builder //ex: User user = User.builder().name("gabriel").email("gab@gmail.com").build();
@NoArgsConstructor //se for usado junto ao Builder, precisa ter o @AllArgsConstructor
@AllArgsConstructor //-> ruim nesse caso devido id
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String email;
	private String password;
	private Timestamp singUpDate;
	private String recoverPasswordHash;

}
