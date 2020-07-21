package com.ironhack.server.controller.interfaces;

import com.ironhack.server.dto.SignUpAdminRequest;
import com.ironhack.server.dto.AdminSummary;
import com.ironhack.server.model.Admin;

import java.util.List;

public interface AdminInterface {
    AdminSummary getAdminById(Long id);
    List<AdminSummary> getAdmins();
    Long createAdmin(SignUpAdminRequest adminDTO);
}
