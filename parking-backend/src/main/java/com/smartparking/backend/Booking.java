
package com.smartparking.backend;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parkingSlotId;

    private String slotNumber;

    private LocalDateTime bookingTime;

    public Booking() {
    }

    public Booking(Long parkingSlotId, String slotNumber) {
        this.parkingSlotId = parkingSlotId;
        this.slotNumber = slotNumber;
        this.bookingTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getParkingSlotId() {
        return parkingSlotId;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
}
