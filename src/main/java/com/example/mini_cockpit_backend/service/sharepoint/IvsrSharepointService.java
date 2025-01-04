package com.example.mini_cockpit_backend.service.sharepoint;

import com.example.mini_cockpit_backend.api.dto.IvsrDTO;
import com.example.mini_cockpit_backend.entity.Ivsr;

import java.util.List;

public interface IvsrSharepointService {

    void saveIvsrData(List<IvsrDTO> ivsrDTOS);
}
