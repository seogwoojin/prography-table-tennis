package com.prography.tabletennis.domain.user.entity;

import java.util.List;

import com.prography.tabletennis.domain.user.entity.enums.UserStatus;
import com.prography.tabletennis.global.entity.BaseTimeEntity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	Integer fakerId;
	String name;
	String email;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	UserStatus userStatus;

	@OneToOne(mappedBy = "user")
	UserRoom userRoom;

	@Builder
	public User(int fakerId, String name, String email, UserStatus userStatus) {
		this.fakerId = fakerId;
		this.name = name;
		this.email = email;
		this.userStatus = userStatus;
	}
}
