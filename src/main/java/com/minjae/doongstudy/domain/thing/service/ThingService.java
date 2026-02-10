package com.minjae.doongstudy.domain.thing.service;

import com.minjae.doongstudy.domain.thing.dto.request.CreateThingRequest;
import com.minjae.doongstudy.domain.thing.dto.request.UpdateThingRequest;
import com.minjae.doongstudy.domain.thing.dto.response.*;

import java.util.List;

public interface ThingService {
    List<GetThingResponse> getThings();
    GetThingResponse getThingById(Long thingId);
    CreateThingResponse createThing(CreateThingRequest createThingRequest);
    UpdateThingResponse updateThing(UpdateThingRequest updateThingRequest);
    DeleteThingResponse deleteThing(Long thingId);
}
