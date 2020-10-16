package com.spring.boot.rest.service.redelivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageMapper storageMapper;

    public void insert(String uuid, String context, LocalDateTime activationDate){
        storageMapper.insert(uuid, context, activationDate);
    }
}
