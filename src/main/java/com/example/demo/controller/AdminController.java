package com.example.demo.controller;

import com.example.demo.dto.CommonResponseBody;
import com.example.demo.dto.ReportRequestDto;
import com.example.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * create on 2024. 12. 12.
 * create by IntelliJ IDEA.
 *
 * <p> Auth Controller. </p>
 *
 * @author Hochan Son
 * @version 1.0
 */
@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    /**
     * Report 조회.
     *
     * @param reportRequestDto Reposrt 조회 ids.
     * @return 조회된 결과
     */
    @PostMapping("/report-users")
    public ResponseEntity<CommonResponseBody<Void>> reportUsers(@RequestBody ReportRequestDto reportRequestDto) {
        adminService.reportUsers(reportRequestDto.getUserIds());
        return ResponseEntity.ok()
                .body(new CommonResponseBody<>("success"));
    }
}
