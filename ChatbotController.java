import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    private final ImageRecognitionService imageRecognitionService;
    private final GPT2Service gpt2Service;

    public ChatbotController() {
        this.imageRecognitionService = new ImageRecognitionService();
        this.gpt2Service = new GPT2Service();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleImageUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Save the uploaded file temporarily
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get("uploads", fileName);
            Files.createDirectories(filePath.getParent());
            file.transferTo(filePath.toFile());

            // Step 1: Object detection
            var detectedObjects = imageRecognitionService.detectObjects(filePath.toString());

            // Step 2: Text generation
            String detectedObjectsString = String.join(", ", detectedObjects);
            String response = gpt2Service.generateResponse(detectedObjectsString);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
