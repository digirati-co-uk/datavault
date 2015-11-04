package org.datavaultplatform.webapp.controllers.admin;


import org.datavaultplatform.common.model.Policy;
import org.datavaultplatform.common.model.User;
import org.datavaultplatform.common.model.Vault;
import org.datavaultplatform.webapp.services.RestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Stuart Lewis
 * Date: 20/09/2015
 */

@Controller
public class AdminUsersController {

    private RestService restService;

    public void setRestService(RestService restService) {
        this.restService = restService;
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String getUsersListing(ModelMap model) {
        model.addAttribute("users", restService.getUsers());
        return "admin/users/index";
    }

    // Return an empty 'create new user' page
    @RequestMapping(value = "/admin/users/create", method = RequestMethod.GET)
    public String createUser(ModelMap model) {
        // pass the view an empty User since the form expects it
        model.addAttribute("user", new User());

        return "admin/users/create";
    }

    // Process the completed 'create new user' page
    @RequestMapping(value = "/admin/users/create", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user, ModelMap model, @RequestParam String action) {
        // Was the cancel button pressed?
        if ("cancel".equals(action)) {
            return "redirect:/";
        }

        // todo: check to see if a user with this id already exists
        User newUser = restService.addUser(user);
        return "redirect:/admin/users";
    }
}

