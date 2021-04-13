package com.doortodoor.dao;

import com.doortodoor.dao.bean.OrganizationDaoBean;

import java.util.UUID;

public interface OrganizationDao {
    OrganizationDaoBean getOrganizationById(UUID id);
}
