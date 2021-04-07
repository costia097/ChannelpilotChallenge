package com.challenge.channelpilotChallenge.thirdparty.firebase.api;

import com.challenge.channelpilotChallenge.thirdparty.firebase.model.note.ItemResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Component
@AllArgsConstructor
public class FirebaseApi {
    private final RestTemplate restTemplate;

    public ItemResponse retrieveItemWithId(int noteId) {
        ResponseEntity<ItemResponse> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity("https://hacker-news.firebaseio.com/v0/item/{noteId}.json", ItemResponse.class, noteId);
        } catch (Exception e) {
            log.error("Error while retrieving note with id {}", noteId, e);
            return null;
        }

        return responseEntity.getBody();
    }
}
