package org.example;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
@RestController
public class AircraftInfoController {

    private static final Logger logger = LoggerFactory.getLogger(AircraftInfoController.class);
    @GetMapping("/aircraft/{model}")
    public ResponseEntity<?> getAircraftInfo(@PathVariable String model) {
        try {
            ClassPathResource resource = new ClassPathResource("aircraftData.json");


            InputStream inputStream = resource.getInputStream();
            String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
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
