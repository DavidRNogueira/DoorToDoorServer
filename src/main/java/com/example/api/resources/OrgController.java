package com.example.api.resources;

import com.example.api.dto.OrganizationDto;
import com.example.api.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/organization")
public class OrgController {
    private OrganizationService organizationService;

    public OrgController (final OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/{orgUserName}/username")
    public OrganizationDto isOrgUserNameTaken (@PathVariable final String orgUserName) {
        return organizationService.isOrgUserNameAvailable(orgUserName);
    }

    @GetMapping("/{id}")
    public OrganizationDto get (@PathVariable final String id) {
        return organizationService.getOrgById(id);
    }
}
