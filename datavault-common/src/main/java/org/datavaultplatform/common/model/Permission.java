package org.datavaultplatform.common.model;

public enum Permission {

    // Vault permissions
    MANAGE_ROLES("VAULT_PERMISSION_1", PermissionModel.PermissionType.VAULT, "Manage roles", null, false),
    VAULT_PERMISSION_2("VAULT_PERMISSION_2", PermissionModel.PermissionType.VAULT, "Vault Permission 2", null, false),
    VAULT_PERMISSION_3("VAULT_PERMISSION_3", PermissionModel.PermissionType.VAULT, "Vault Permission 3", null, false),
    VAULT_PERMISSION_4("VAULT_PERMISSION_4", PermissionModel.PermissionType.VAULT, "Vault Permission 4", null, false),
    VAULT_PERMISSION_5("VAULT_PERMISSION_5", PermissionModel.PermissionType.VAULT, "Vault Permission 5", null, false),

    // School permissions
    CAN_VIEW_VAULTS_SIZE("CAN_VIEW_VAULTS_SIZE", PermissionModel.PermissionType.SCHOOL, "View school vaults size", null, true),
    CAN_VIEW_IN_PROGRESS_DEPOSITS("CAN_VIEW_IN_PROGRESS_DEPOSITS", PermissionModel.PermissionType.SCHOOL, "View school deposits/retrieves in progress", null, true),
    CAN_VIEW_DEPOSIT_QUEUE("CAN_VIEW_DEPOSIT_QUEUE", PermissionModel.PermissionType.SCHOOL, "View school deposit/retrieval queues", null, true),
    CAN_MANAGE_VAULTS("CAN_MANAGE_VAULTS", PermissionModel.PermissionType.SCHOOL, "Manage school vaults", "ROLE_ADMIN_VAULTS", true),
    CAN_MANAGE_DEPOSITS("CAN_MANAGE_DEPOSITS", PermissionModel.PermissionType.SCHOOL, "Manage school vault deposits", "ROLE_ADMIN_DEPOSITS", true),
    CAN_VIEW_RETRIEVES("CAN_VIEW_RETRIEVES", PermissionModel.PermissionType.SCHOOL, "View school retrieves", "ROLE_ADMIN_RETRIEVES", true),
    CAN_MANAGE_SCHOOL_USERS("CAN_MANAGE_SCHOOL_USERS", PermissionModel.PermissionType.SCHOOL, "Manage school role assignments", "ROLE_ADMIN_SCHOOLS", true),

    // Admin permissions
    CAN_MANAGE_BILLING_DETAILS("CAN_MANAGE_BILLING_DETAILS", PermissionModel.PermissionType.ADMIN, "Manage billing details", "ROLE_ADMIN_BILLING", true),
    CAN_MANAGE_ROLES("CAN_MANAGE_ROLES", PermissionModel.PermissionType.ADMIN, "Manage roles", "ROLE_ADMIN_ROLES", true),
    CAN_VIEW_EVENTS("CAN_VIEW_EVENTS", PermissionModel.PermissionType.ADMIN, "View events", "ROLE_ADMIN_EVENTS", true),
    CAN_MANAGE_RETENTION_POLICIES("CAN_MANAGE_RETENTION_POLICIES", PermissionModel.PermissionType.ADMIN, "Manage retention policies", "ROLE_ADMIN_RETENTIONPOLICIES", true),
    CAN_MANAGE_ARCHIVE_STORES("CAN_MANAGE_ARCHIVE_STORES", PermissionModel.PermissionType.ADMIN, "Manage archive stores", "ROLE_ADMIN_ARCHIVESTORES", true);

    private final String id;

    private final PermissionModel.PermissionType defaultType;

    private final String defaultLabel;

    private final String roleName;

    private final boolean dashboardPermission;

    Permission(String id, PermissionModel.PermissionType defaultType, String defaultLabel, String roleName, boolean dashboardPermission) {
        this.id = id;
        this.defaultType = defaultType;
        this.defaultLabel = defaultLabel;
        this.roleName = roleName;
        this.dashboardPermission = dashboardPermission;
    }

    public String getId() {
        return id;
    }

    public PermissionModel.PermissionType getDefaultType() {
        return defaultType;
    }

    public String getDefaultLabel() {
        return defaultLabel;
    }

    public String getRoleName() {
        return roleName;
    }

    public boolean isDashboardPermission() {
        return dashboardPermission;
    }
}
