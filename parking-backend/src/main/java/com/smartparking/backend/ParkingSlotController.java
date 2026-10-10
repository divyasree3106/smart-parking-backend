
package com.smartparking.backend;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/parking-slots")
@CrossOrigin(origins = "http://localhost:5173")
public class ParkingSlotController {

    private final ParkingSlotRepository repository;
    private final BookingRepository bookingRepository;

    public ParkingSlotController(
            ParkingSlotRepository repository,
            BookingRepository bookingRepository) {
        this.repository = repository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping
    public List<ParkingSlot> getAllSlots() {
        return repository.findAll();
    }

    @PostMapping
    public ParkingSlot addSlot(@RequestBody ParkingSlot slot) {
        return repository.save(slot);
    }

    @PutMapping("/{id}")
    public ParkingSlot updateSlot(
            @PathVariable Long id,
            @RequestBody ParkingSlot updatedSlot) {

        ParkingSlot slot = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Parking slot not found: " + id));

        slot.setSlotNumber(updatedSlot.getSlotNumber());
        slot.setStatus(updatedSlot.getStatus());
        slot.setPricePerHour(updatedSlot.getPricePerHour());

        return repository.save(slot);
    }

    @Transactional
    @PostMapping("/{id}/book")
    public ParkingSlot bookSlot(@PathVariable Long id) {

        ParkingSlot slot = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Parking slot not found: " + id));

        if ("BOOKED".equalsIgnoreCase(slot.getStatus())) {
            throw new RuntimeException("Slot is already booked!");
        }

        slot.setStatus("BOOKED");
        repository.save(slot);

        Booking booking = new Booking(slot.getId(), slot.getSlotNumber());
        bookingRepository.save(booking);

        return slot;
    }
    
    @GetMapping("/history")
    public List<Booking> getBookingHistory() {
        return bookingRepository.findAll();
    }
}
