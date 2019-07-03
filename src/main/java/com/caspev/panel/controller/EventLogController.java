package com.caspev.panel.controller;

import com.caspev.panel.security.RolesConstants;
import com.caspev.panel.service.EventLogService;
import com.caspev.panel.service.dto.EventLogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Web controller for managing EventLog.
 */
@Secured({RolesConstants.ADMIN})
@Controller
public class EventLogController {

    private final EventLogService eventLogService;
    private final Logger          log = LoggerFactory.getLogger(EventLogController.class);

    public EventLogController(EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @GetMapping("/event-logs")
    public String listEventLogs(Model model) {
        log.debug("GET request to get all EventLogs");
        List<EventLogDTO> eventLogDTOList = eventLogService.findAll();
        model.addAttribute("eventLogDTOList", eventLogDTOList);
        return "event-log/list";
    }
}