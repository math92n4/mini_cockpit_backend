package com.example.mini_cockpit_backend.controller;

import com.example.mini_cockpit_backend.api.ApiService;
import com.example.mini_cockpit_backend.api.dto.IvsrDTO;
import com.example.mini_cockpit_backend.api.dto.PostIvsrDTO;
import com.example.mini_cockpit_backend.dto.FileResponse;
import com.example.mini_cockpit_backend.dto.graphs.GraphData;
import com.example.mini_cockpit_backend.entity.Ivsr;
import com.example.mini_cockpit_backend.exception.FileException;
import com.example.mini_cockpit_backend.service.file.FileService;
import com.example.mini_cockpit_backend.service.graph.GraphService;
import com.example.mini_cockpit_backend.service.graph.GraphServiceImpl;
import com.example.mini_cockpit_backend.service.isvr.IvsrService;
import com.example.mini_cockpit_backend.service.sharepoint.IvsrSharepointService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://matwn.dk")
@RequestMapping("/api/mini/ivsr")
@RequiredArgsConstructor
public class IvsrController {

    private final IvsrService ivsrService;

    private final GraphService graphService;

    private final IvsrSharepointService ivsrSharepointService;
    private final FileService fileService;
    private final ApiService apiService;

    @PostMapping("")
    public ResponseEntity<String> postIvsr() {
        try {
            List<IvsrDTO> ivsrDTOS = apiService.sendIvsrRequest().getBody();
            ivsrSharepointService.saveIvsrData(ivsrDTOS);
            return new ResponseEntity<>("List updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Not ok", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("")
    public ResponseEntity<List<IvsrDTO>> getIvsr() {
        try {
            List<IvsrDTO> list = ivsrService.getAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<PostIvsrDTO> postIvsr(@RequestBody PostIvsrDTO postIvsrDTO) {

        Ivsr foundIvsr = ivsrService.getByProductionNumber(postIvsrDTO.getProductionNumber().toUpperCase().trim());
        if (foundIvsr != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Ivsr ivsr = new Ivsr();
            ivsr.setProductionNumber(postIvsrDTO.getProductionNumber().toUpperCase());
            ivsr.setIvsrData(false);
            ivsr.setSharepointData(false);
            ivsrService.save(ivsr);
            return new ResponseEntity<>(postIvsrDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(postIvsrDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadIvsr(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        FileResponse fileResponse = new FileResponse();
        File isvr = null;

        try {
            isvr = fileService.multipartToFile(multipartFile);

        } catch (IOException e) {
            e.printStackTrace();
            fileResponse.addMessage("Kunne ikke konvertere fil");
            return new ResponseEntity<>(fileResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            List<IvsrDTO> list = fileService.parseFile(String.valueOf(isvr));
            ivsrSharepointService.saveIvsrData(list);
        } catch (FileException e) {
            e.printStackTrace();
            fileResponse.setMessages(e.getErrorMessages());
            return new ResponseEntity<>(fileResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/graph")
    public ResponseEntity<GraphData> getGraph() {
        try {
            GraphData graphData = graphService.getGraphData();
            return new ResponseEntity<>(graphData, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{productionNumber}")
    public ResponseEntity<String> delete(@PathVariable String productionNumber) {
        System.out.println("DELETE " + productionNumber);
        try {
            ivsrService.deleteByProductionNumber(productionNumber);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<IvsrDTO> put(@RequestBody IvsrDTO ivsrDTO) {
        System.out.println("PUT " + ivsrDTO.getProductionNumber());
        try {
            ivsrService.updateCar(ivsrDTO);
            return new ResponseEntity<>(ivsrDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
