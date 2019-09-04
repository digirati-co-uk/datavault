package org.datavaultplatform.common.util;

import org.datavaultplatform.common.model.Permission;
import org.datavaultplatform.common.model.RoleAssignment;

public class RoleUtils {

    public static final String IS_ADMIN_ROLE_NAME = "IS Admin";
    public static final String IS_ADMIN_ROLE_DESCRIPTION = "An admin of the whole system, with full permissions over the system.";
    public static final String DATA_OWNER_ROLE_NAME = "Data Owner";
    public static final String DATA_OWNER_ROLE_DESCRIPTION = "An admin of a specific vault, with full permissions over that vault.";

    private RoleUtils() {}

    public static boolean isReservedRoleName(String roleName) {
        return IS_ADMIN_ROLE_NAME.equalsIgnoreCase(roleName) || DATA_OWNER_ROLE_NAME.equalsIgnoreCase(roleName);
    }

    public static boolean isDataOwner(RoleAssignment roleAssignment) {
        return DATA_OWNER_ROLE_NAME.equals(roleAssignment.getRole().getName());
    }

    public static boolean isISAdmin(RoleAssignment roleAssignment) {
        return IS_ADMIN_ROLE_NAME.equals(roleAssignment.getRole().getName());
    }

    public static boolean isRoleInVault(RoleAssignment roleAssignment, String vaultId) {
        return roleAssignment.getVault() != null && roleAssignment.getVault().getID().equals(vaultId);
    }

    public static boolean isRoleInSchool(RoleAssignment roleAssignment, String schoolId) {
        return roleAssignment.getSchool() != null && roleAssignment.getSchool().getID().equals(schoolId);
    }

    public static boolean hasPermission(RoleAssignment roleAssignment, Permission permission) {
        return roleAssignment.getRole().getPermissions().stream()
                .anyMatch(permissionModel -> permission == permissionModel.getPermission());
    }
}
