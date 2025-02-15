package com.prography.tabletennis.domain.user.entity;

import com.prography.tabletennis.domain.room.entity.Room;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class UserRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@JoinColumn(name = "room_id", nullable = false)
	@ManyToOne
	Room room;

	@JoinColumn(name = "user_id", nullable = false)
	@OneToOne
	User user;
}
