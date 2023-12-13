package org.example;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class AircraftInfoController {

    private static final Logger logger = LoggerFactory.getLogger(AircraftInfoController.class);
    @GetMapping("/aircraft/{model}")
    public ResponseEntity<?> getAircraftInfo(@PathVariable String model) {
        try {
            Path filePath = Paths.get("aircraft-info-service/src/main/java/org/example/aircraftData.json");
            logger.info("Reading aircraft data from {}", filePath);

            String content = Files.readString(filePath);
            logger.info("Read content: {}", content);

            JSONObject aircraftData = new JSONObject(content);
            if (aircraftData.has(model)) {
                return ResponseEntity.ok(aircraftData.getJSONObject(model).toString());
            } else {
                logger.warn("Model {} not found in aircraft data", model);
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            logger.error("Error reading aircraft data file", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
