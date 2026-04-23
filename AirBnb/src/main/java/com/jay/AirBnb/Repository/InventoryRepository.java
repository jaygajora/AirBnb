package com.jay.AirBnb.Repository;

import com.jay.AirBnb.Dto.InventoryDTO;
import com.jay.AirBnb.Entity.InventoryEntity;
import com.jay.AirBnb.Entity.RoomEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    void deleteByRoom(RoomEntity room);
    List<InventoryEntity> findByRoomOrderByDate(RoomEntity roomId);

    @Query("""
        SELECT i
            FROM InventoryEntity i
                WHERE i.room.id = :roomId
                    AND i.date BETWEEN :startDate AND :endDate
    """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<InventoryEntity> getInventoryAndLockBeforeUpdate(@Param("roomId") Long roomId,
                                                       @Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate
    );

    @Modifying
    @Query("""
            UPDATE InventoryEntity i
                       SET i.closed = :closed,
                           i.surgeFactor = :surgeFactor
                       WHERE i.room.id = :roomId 
                            AND i.date BETWEEN :startDate AND :endDate
           """)
    void updateInventory(@Param("roomId") Long roomId,
                         @Param("closed") Boolean closed,
                         @Param("surgeFactor")BigDecimal surgeFactor,
                         @Param("startDate")LocalDate startDate,
                         @Param("endDate") LocalDate endDate

    );

    @Query("""
        SELECT i
        FROM InventoryEntity i
                WHERE roomId = :roomId
                AND i.date BETWEEN :checkInDate AND :checkOutDate
                AND i.close = false
                AND (i.totalCount - i.bookCount - i.reservedCount) >= :roomsCount
        """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<InventoryEntity> findAndLockAvailableInventory(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("roomsCount") Integer roomsCount
    );

    @Modifying
    @Query("""
                UPDATE i
                SET i.reservedCount = i.reservedCount + :roomsCount
                                WHERE roomId = :roomId
                                AND i.date BETWEEN :checkInDate AND :checkOutDate
                                AND (i.totalCount - i.reservedCount - i.bookCount) >= :roomsCount
                                AND i.close = false                                                         
            """)
    void initBooking(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("roomsCount") Integer roomsCount
    );
}
