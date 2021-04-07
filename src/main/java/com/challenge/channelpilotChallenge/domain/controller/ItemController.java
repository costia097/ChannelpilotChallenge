package com.challenge.channelpilotChallenge.domain.controller;

import com.challenge.channelpilotChallenge.domain.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/consume")
    public void consumeItems(@RequestParam(required = false, defaultValue = "10000") int limit) throws InterruptedException {
        itemService.consumeItems(limit);
    }
}
