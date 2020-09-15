package com.server.interfaces;

import com.server.dto.SignUpAdminRequest;
import com.server.dto.AdminSummary;

import java.util.List;

public interface AdminInterface {
    AdminSummary getAdminById(Long id);
    List<AdminSummary> getAdmins();
    Long createAdmin(SignUpAdminRequest adminDTO);
}
