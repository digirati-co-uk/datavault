package org.datavaultplatform.webapp.controllers.admin;

import org.datavaultplatform.common.model.Group;
import org.datavaultplatform.common.model.User;
import org.datavaultplatform.webapp.exception.EntityNotFoundException;
import org.datavaultplatform.webapp.services.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminSchoolsController {

    private static final Logger logger = LoggerFactory.getLogger(AdminSchoolsController.class);

    private static final Role ROLE_A = new Role("A", "Role A",
            "Role A is a great role. You really want it. It lets you do whatever you want.");
    private static final Role ROLE_B = new Role("B", "Role B",
            "Role B isn't so good tbh. It won't even give you enough permissions to do your job.");

    private List<RoleAssignment> roleAssignments;

    private RestService restService;

    public void setRestService(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/admin/schools")
    public ModelAndView getSchoolsListingPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/schools/index");
        mav.addObject("schools", restService.getGroups());
        return mav;
    }

    @GetMapping("/admin/schools/{school}")
    public ModelAndView getSchoolRoleAssignmentsPage(@PathVariable("school") String schoolId) {

        Optional<Group> school = getGroup(schoolId);
        if (!school.isPresent()) {
            logger.error("Attempted to access school role management page for group {} but no such group was found",
                    schoolId);
            throw new EntityNotFoundException(Group.class, schoolId);
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/schools/schoolRoles");
        mav.addObject("school", school.get());
        mav.addObject("roles", getRoles());
        mav.addObject("roleAssignments", getRoleAssignments(school.get()));
        return mav;
    }

    @PostMapping("/admin/schools/{school}/user")
    public String addNewRoleAssignment(@PathVariable("school") String schoolId,
                                       @RequestParam("user") String userId,
                                       @RequestParam("role") String roleId,
                                       RedirectAttributes redirectAttributes) {

        Optional<Group> school = getGroup(schoolId);
        Optional<User> user = getUser(userId);
        Optional<Role> role = getRole(roleId);

        if (!school.isPresent()) {
            logger.error("Attempted to add a new school role assignment to group {} but no such group was found",
                    schoolId);
            throw new EntityNotFoundException(Group.class, schoolId);

        } else if (!role.isPresent()) {
            logger.error("Attempted to add a new school role assignment with role {} but no such role was found",
                    roleId);
            throw new EntityNotFoundException(Role.class, roleId);

        } else if (!user.isPresent()) {
            logger.info("Attempted to add a new school role assignment to user {} but no such user was found", userId);
            redirectAttributes.addFlashAttribute("error", "Could not find user with ID=" + userId);

        } else if (getRoleAssignment(schoolId, userId).isPresent()) {
            logger.info("Attempted to add a new school role assignment for user {} and school {} when one already exists",
                    userId,
                    schoolId);
            redirectAttributes.addFlashAttribute("error", "User " + userId + " already has a role assigned to them.");

        } else {
            logger.info("Adding new school role assignment: [userId={},groupId={},roleId={}]",
                    userId,
                    schoolId,
                    roleId);
            RoleAssignment newRoleAssignment = new RoleAssignment(role.get(), user.get(), school.get());
            createNewRoleAssignment(newRoleAssignment);
        }

        return "redirect:/admin/schools/" + schoolId;
    }

    @PostMapping("/admin/schools/{school}/user/update")
    public String updateExistingRoleAssignment(@PathVariable("school") String schoolId,
                                               @RequestParam("user") String userId,
                                               @RequestParam("role") String roleId) {

        Optional<Role> role = getRole(roleId);
        Optional<RoleAssignment> userRoleAssignment = getRoleAssignment(schoolId, userId);

        if (!role.isPresent()) {
            logger.error("Attempted to update a school role assignment to role {} but no such role was found",
                    roleId);
            throw new EntityNotFoundException(Role.class, roleId);

        } else if (!userRoleAssignment.isPresent()) {
            logger.error("Attempted to update a school role assignment [userId={},groupId={}] but no such role assignment was found",
                    userId,
                    schoolId);
            throw new EntityNotFoundException(RoleAssignment.class, "[userId=" + userId + ",groupId=" + schoolId + "]");
        }

        logger.info("Updating school role assignment. New role assignment: [userId={},groupId={},roleId={}]",
                userId,
                schoolId,
                roleId);
        updateRoleAssignment(userRoleAssignment.get(), role.get());

        return "redirect:/admin/schools/" + schoolId;
    }

    @PostMapping("/admin/schools/{school}/user/delete")
    public String deleteRoleAssignment(@PathVariable("school") String schoolId,
                                       @RequestParam("user") String userId) {

        Optional<RoleAssignment> userRoleAssignment = getRoleAssignment(schoolId, userId);
        if (!userRoleAssignment.isPresent()) {
            logger.error("Attempted to delete a school role assignment [userId={},groupId={}] but no such role assignment was found",
                    userId,
                    schoolId);
            throw new EntityNotFoundException(RoleAssignment.class, "[userId=" + userId + ",groupId=" + schoolId + "]");
        }

        logger.info("Deleting school role assignment: [userId={},groupId={}]", userId, schoolId);
        deleteRoleAssignment(userRoleAssignment.get());

        return "redirect:/admin/schools/" + schoolId;
    }

    private List<Role> getRoles() {
        return Arrays.asList(ROLE_A, ROLE_B);
    }

    private List<RoleAssignment> getRoleAssignments(Group school) {
        if (roleAssignments == null) {
            User user1 = restService.getUser("user1");
            User user2 = restService.getUser("user2");
            User user3 = restService.getUser("user3");
            roleAssignments = new ArrayList<>();
            roleAssignments.add(new RoleAssignment(ROLE_A, user1, school));
            roleAssignments.add(new RoleAssignment(ROLE_A, user2, school));
            roleAssignments.add(new RoleAssignment(ROLE_B, user3, school));
        }
        return roleAssignments;
    }

    private Optional<Group> getGroup(String groupId) {
        return Optional.ofNullable(restService.getGroup(groupId));
    }

    private Optional<User> getUser(String userId) {
        return Optional.ofNullable(restService.getUser(userId));
    }

    private Optional<Role> getRole(String roleId) {
        return getRoles().stream().filter(role -> role.getId().equals(roleId)).findFirst();
    }

    private void createNewRoleAssignment(RoleAssignment toCreate) {
        roleAssignments.add(toCreate);
    }

    private Optional<RoleAssignment> getRoleAssignment(String groupId, String userId) {
        return roleAssignments.stream()
                .filter(roleAssignment ->
                                roleAssignment.getGroup().getID().equals(groupId)
                                        && roleAssignment.getUser().getID().equals(userId))
                .findFirst();
    }

    private void updateRoleAssignment(RoleAssignment originalRoleAssignment, Role newRole) {
        originalRoleAssignment.setRole(newRole);
    }

    private void deleteRoleAssignment(RoleAssignment toDelete) {
        roleAssignments.remove(toDelete);
    }

    public static class Role {
        private final String id;
        private final String name;
        private final String description;

        private Role(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class RoleAssignment {
        private Role role;
        private final User user;
        private final Group group;

        private RoleAssignment(Role role, User user, Group group) {
            this.role = role;
            this.user = user;
            this.group = group;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public User getUser() {
            return user;
        }

        public Group getGroup() {
            return group;
        }
    }
}
