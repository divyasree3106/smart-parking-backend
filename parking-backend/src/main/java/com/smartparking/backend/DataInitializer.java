
package com.smartparking.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeParkingSlots(ParkingSlotRepository repository) {
        return args -> {

            if (repository.count() == 0) {

                String[] slots = {"A1", "A2", "A3", "B1", "B2"};

                for (String slotNumber : slots) {
                    ParkingSlot slot = new ParkingSlot();
                    slot.setSlotNumber(slotNumber);
                    slot.setStatus("AVAILABLE");
                    slot.setPricePerHour(30.0);

                    repository.save(slot);
                }
            }
        };
    }
}
