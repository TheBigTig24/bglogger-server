package com.example.bglogger.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bglogger.dto.ExternalGameWrapper;
import com.example.bglogger.services.GameService;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
    
    @GetMapping(value = "/get")
    public ResponseEntity<ExternalGameWrapper> testApi(@RequestParam String gameId) {
        ExternalGameWrapper res = gameService.getExternalGame(Integer.parseInt(gameId));
        return ResponseEntity.ok().body(res);
    }

    @GetMapping(value = "/query")
    public ResponseEntity<String> testApi2(@RequestParam String query) {
        String res = gameService.getExternalGameByName(query);
        return ResponseEntity.ok().body(res);
    }
    
    @GetMapping(value = "/string-response")
    public ResponseEntity<String> getStringResponse(@RequestParam String gameId) {
        String res = gameService.getStringResponse(Integer.parseInt(gameId));
        return ResponseEntity.ok().body(res);
    }
}
