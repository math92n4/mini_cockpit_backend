package com.example.mini_cockpit_backend.service.isvr;

import com.example.mini_cockpit_backend.api.dto.IvsrDTO;
import com.example.mini_cockpit_backend.entity.Ivsr;

import java.util.List;

public interface IvsrService {

    void saveList(List<Ivsr> list);
    void save(Ivsr ivsr);

    List<IvsrDTO> getAll();

    boolean doesExist(String productionNumber);

    Ivsr getByProductionNumber(String productionNumber);

    void deleteByProductionNumber(String productionNumber);

    IvsrDTO updateCar(IvsrDTO ivsrDTO);

}
