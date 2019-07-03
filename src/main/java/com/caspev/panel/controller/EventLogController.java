package com.caspev.panel.controller;

import com.caspev.panel.controller.errors.ResourceNotFoundException;
import com.caspev.panel.service.EventLogService;
import com.caspev.panel.service.dto.EventLogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Web controller for managing EventLog.
 */
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

    @GetMapping("/event-logs/{uuid}")
    public String getEventLog(@PathVariable String uuid, Model model) {
        log.debug("GET request to get EventLog : {}", uuid);
        model.addAttribute("formAction", "/event-logs/" + uuid);
        EventLogDTO eventLogDTO = eventLogService.findOneByUuid(uuid)
                .orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("eventLogDTO", eventLogDTO);

        return "event-log/form";
    }
}