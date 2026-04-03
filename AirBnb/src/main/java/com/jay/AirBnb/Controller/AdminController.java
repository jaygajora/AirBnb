package com.jay.AirBnb.Controller;


import com.jay.AirBnb.Dto.SetAdminDTO;
import com.jay.AirBnb.Service.Interface.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("set-user-as-admin")
    public ResponseEntity<String> setUserAsAdmin(@RequestBody SetAdminDTO setAdminDTO)
    {
        return new ResponseEntity<>(adminService.setUserAsAdmin(setAdminDTO.getEmail()), HttpStatus.OK);
    }
}
