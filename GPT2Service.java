import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpUriRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;

public class GPT2Service {

    private static final String GPT2_API_URL = "https://api-inference.huggingface.co/models/gpt2";
    private static final String AUTH_TOKEN = "your_huggingface_api_token";  // Hugging Face API token

    public String generateResponse(String detectedObjects) throws Exception {
        // Prepare the prompt for GPT-2
        String prompt = "Detected objects: " + detectedObjects + ". Please describe these objects.";

        // Create HTTP request
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(GPT2_API_URL);
        post.setHeader("Authorization", "Bearer " + AUTH_TOKEN);
        post.setEntity(new StringEntity("{\"inputs\": \"" + prompt + "\"}"));

        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);

        // Parse the response JSON and extract the generated text
        return parseResponse(responseString);
    }

    private String parseResponse(String response) {
        // Example: Extract the generated response from JSON (Assuming Hugging Face returns the text)
        return response;  // This needs to be improved with actual JSON parsing logic
    }
}
