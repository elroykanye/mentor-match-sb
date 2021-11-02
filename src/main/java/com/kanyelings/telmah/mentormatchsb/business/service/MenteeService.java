package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenteeService {
    ResponseEntity<List<MenteeDto>> getAllMentees();

    ResponseEntity<String> addNewMentee(MenteeDto newMentee, MultipartFile imageFile);
}
