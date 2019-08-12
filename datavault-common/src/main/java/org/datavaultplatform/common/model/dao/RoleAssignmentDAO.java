package org.datavaultplatform.common.model.dao;

import org.datavaultplatform.common.model.RoleAssignment;

import java.util.List;

public interface RoleAssignmentDAO {

    boolean roleAssignmentExists(RoleAssignment roleAssignment);

    void store(RoleAssignment roleAssignment);

    long count(Long id);

    RoleAssignment find(Long id);

    List<RoleAssignment> findAll();

    List<RoleAssignment> findBySchoolId(String schoolId);

    List<RoleAssignment> findByVaultId(String vaultId);

    List<RoleAssignment> findByUserId(String userId);

    void update(RoleAssignment roleAssignment);

    void delete(Long id);
}
