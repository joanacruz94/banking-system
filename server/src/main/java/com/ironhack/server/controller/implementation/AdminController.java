package com.ironhack.server.controller.implementation;

import com.ironhack.server.controller.interfaces.AdminInterface;
import com.ironhack.server.dto.SignUpAdminRequest;
import com.ironhack.server.dto.AdminSummary;
import com.ironhack.server.model.Admin;
import com.ironhack.server.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController implements AdminInterface {
    @Autowired
    AdminService adminService;

    @ApiOperation(value = "See information of an admin user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdminSummary getAdminById(@PathVariable Long id){
        return adminService.findAdminById(id);
    }

    @ApiOperation(value = "Check all the admin users in the system")
    @GetMapping("/admins")
    @ResponseStatus(HttpStatus.OK)
    public List<AdminSummary> getAdmins(){
        return adminService.findAllAdmins();
    }

    @ApiOperation(value = "Insert a new admin. Method returns the ID generated for the user.")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createAdmin(@RequestBody SignUpAdminRequest adminDTO){
        return adminService.registerAdmin(adminDTO);
    }
}
