package com.example.outofmemoryrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class OutOfMemoryRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutOfMemoryRestApplication.class, args);
    }
}

@RestController
class OutOfMemoryController {

    @GetMapping("/simulate-out-of-memory")
    public String simulateOutOfMemory() {
        // Set an uncaught exception handler
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Uncaught exception: " + throwable.getMessage());
            generateHeapDump();
            System.exit(1); // Terminate the application
        });

        try {
            // Allocate a large continuous block of memory to simulate OutOfMemoryError
            long totalMemory = Runtime.getRuntime().totalMemory();
            long maxMemory = Runtime.getRuntime().maxMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            long remainingMemory = maxMemory - (totalMemory - freeMemory);

            // Attempt to allocate a memory chunk larger than the remaining available memory
            byte[] memoryChunk = new byte[(int) remainingMemory];

            // The code will not reach this point because an OutOfMemoryError is expected
            System.out.println("Memory allocation successful!"); // This line should not be reached

        } catch (OutOfMemoryError e) {
            // This catch block will be invoked when an OutOfMemoryError occurs
            System.err.println("OutOfMemoryError occurred: " + e.getMessage());
        }

        // The application should exit on its own after handling the OutOfMemoryError
        return "Simulation completed";
    }

    private void generateHeapDump() {
        // Implement heap dump generation logic here
        System.out.println("Generating heap dump...");
    }
}
