package org.example;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AircraftInfoController {
    @GetMapping("/aircraft/{model}")
    public ResponseEntity<?> getAircraftInfo(@PathVariable String model) {
        try {
            Path filePath = Paths.get("aircraft-info-service/src/main/java/org/example/aircraftData.json");
            String content = Files.readString(filePath);

            JSONObject aircraftData = new JSONObject(content);
            if (aircraftData.has(model)) {
                return ResponseEntity.ok(aircraftData.getJSONObject(model).toString());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
