package com.prography.tabletennis.domain.room.entity;

import java.util.List;

import com.prography.tabletennis.domain.user.entity.UserRoom;
import com.prography.tabletennis.global.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Room extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	String title;
	Integer host;
	RoomType roomType;
	RoomStatus roomStatus;

	@OneToMany(mappedBy = "room")
	List<UserRoom> userRoomList;
}