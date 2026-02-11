package com.minjae.doongstudy.domain.thing.controller;

import com.minjae.doongstudy.domain.thing.dto.request.CreateThingRequest;
import com.minjae.doongstudy.domain.thing.dto.request.UpdateThingRequest;
import com.minjae.doongstudy.domain.thing.dto.response.*;
import com.minjae.doongstudy.domain.thing.service.ThingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thing")
@RequiredArgsConstructor
public class ThingController {
    private final ThingService thingService;

    @GetMapping("/{memberId}")
    public ResponseEntity<List<GetThingResponse>> getThings(
            @PathVariable Long memberId) {
        return ResponseEntity.ok(thingService.getThings(memberId));
    }

    @GetMapping("/{thingId}/{memberId}")
    public ResponseEntity<GetThingResponse> getThing(
            @PathVariable Long thingId,
            @PathVariable Long memberId) {
        return ResponseEntity.ok(thingService.getThingById(thingId, memberId));
    }

    @PostMapping
    public ResponseEntity<CreateThingResponse> createThing(
            @Valid @RequestBody CreateThingRequest createThingRequest) {
        return ResponseEntity.ok(thingService.createThing(createThingRequest));
    }

    @PatchMapping
    public ResponseEntity<UpdateThingResponse> updateThing(
            @Valid @RequestBody UpdateThingRequest updateThingRequest) {
        return ResponseEntity.ok(thingService.updateThing(updateThingRequest));
    }

    @DeleteMapping("/{thingId}")
    public ResponseEntity<DeleteThingResponse> deleteThing(@PathVariable Long thingId) {
        return ResponseEntity.ok(thingService.deleteThing(thingId));
    }
}
