package com.jay.AirBnb.Repository;

import com.jay.AirBnb.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
