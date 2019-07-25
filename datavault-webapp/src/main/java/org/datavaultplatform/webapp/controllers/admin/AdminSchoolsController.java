package org.datavaultplatform.webapp.controllers.admin;

import org.datavaultplatform.webapp.services.RestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminSchoolsController {

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
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/schools/schoolRoles");
        mav.addObject("school", restService.getGroup(schoolId));
        return mav;
    }
}
