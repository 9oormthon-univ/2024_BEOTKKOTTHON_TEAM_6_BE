package org.goormthon.beotkkotthon.rebook.utility;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.goormthon.beotkkotthon.rebook.dto.type.ERecycleCategory;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Component
public class AnalysisUtil {
    @Value("${spring.ai.google-vision.uri}")
    private String GOOGLE_CLOUD_VISION_URL;

    @Value("${spring.ai.google-vision.credentials.key}")
    private String GOOGLE_CLOUD_VISION_KEY;


    private final RestClient restClient = RestClient.create();

    public List<String> getImageAnalysis(MultipartFile multipartFile) {
        Map<String, Object> response;

        try {
            String  base64EncodingMultipartFile = Base64.getEncoder().encodeToString(multipartFile.getBytes());

            JSONObject image = new JSONObject();
            image.put("content", base64EncodingMultipartFile);

            JSONArray features = new JSONArray();
            JSONObject object = new JSONObject();
            object.put("maxResults", 15);
            object.put("type", "LABEL_DETECTION");
            features.add(object);

            JSONObject objects = new JSONObject();
            objects.put("image", image);
            objects.put("features", features);

            JSONArray requests = new JSONArray();
            requests.add(objects);

            JSONObject request = new JSONObject();
            request.put("requests", requests);

//            System.out.println(request);

            response = Objects.requireNonNull(restClient.post()
                    .uri(String.format("%s?%s=%s",
                            GOOGLE_CLOUD_VISION_URL,
                            "key",
                            GOOGLE_CLOUD_VISION_KEY))
                    .headers(httpHeaders -> {
                        httpHeaders.set("Content-Type", "application/json;charset=utf-8");
                    })
                    .body(request.toJSONString())
                    .retrieve()
                    .toEntity(Map.class).getBody());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(ErrorCode.EXTERNAL_SERVER_ERROR);
        }

//        System.out.println(response);

        List<Object> responses = (List<Object>) response.get("responses");
//        System.out.println(responses);
        Map<String, Object> responsesObjects = (Map<String, Object>) responses.get(0);
//        System.out.println(responsesObjects);
        List<Map<String,Object>> labelAnnotations = (List<Map<String, Object>>) responsesObjects.get("labelAnnotations");
        List<String> descriptionList = new ArrayList<>();
        for (Map<String, Object> labelAnnotation: labelAnnotations) {
            descriptionList.add((String) labelAnnotation.get("description").toString().toLowerCase());
        }

        return descriptionList;
    }

    public String getImageRecycleCategory(List<String> descriptionList) {
        String recycleCategory = null;
        for (String description: descriptionList) {
           recycleCategory = ERecycleCategory.getRecycleCategory(description);

           if (recycleCategory != null) {
               return recycleCategory;
           }
        }
        return recycleCategory;
    }
}
