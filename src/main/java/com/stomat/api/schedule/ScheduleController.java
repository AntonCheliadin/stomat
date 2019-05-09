package com.stomat.api.schedule;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Anton Chelyadin.
 * @since 02.01.19.
 */
@Controller
public class ScheduleController {

    @RequestMapping(value = {"/schedule"}, method = RequestMethod.GET)
    public String schedule(Model model) {
        return "schedule/scheduleEditor";
    }
}
