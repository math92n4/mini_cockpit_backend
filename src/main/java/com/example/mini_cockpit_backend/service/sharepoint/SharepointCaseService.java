package com.example.mini_cockpit_backend.service.sharepoint;

import com.example.mini_cockpit_backend.dto.SharepointCaseDTO;
import com.example.mini_cockpit_backend.entity.Ivsr;

import java.util.List;

public interface SharepointCaseService {
    void saveSharepointData();
    List<SharepointCaseDTO> getSharePointData();



}
