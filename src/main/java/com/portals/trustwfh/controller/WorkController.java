package com.portals.trustwfh.controller;

import com.portals.trustwfh.dto.ConfidenceResult;
import com.portals.trustwfh.model.LoginContext;
import com.portals.trustwfh.service.PunchInService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work")
@Tag(name = "Work / Risk Evaluation", description = "Endpoints for Work actions and Risk assessment. Step 3 of the flow.")
public class WorkController {

    private final PunchInService punchInService;

    public WorkController(PunchInService punchInService) {
        this.punchInService = punchInService;
    }

    @PostMapping("/punch-in")
    @Operation(summary = "Perform Punch-In", description = "Step 3: Evaluate risk based on current context (Device, Location) and log the event. Requires JWT token.", security = @SecurityRequirement(name = "bearerAuth"))
    public ConfidenceResult punchIn(
            HttpServletRequest request,
            Authentication authentication) {
        // Authenticated user from JWT
        String userId = authentication.getName();

        LoginContext context = LoginContext.from(userId, request);

        return punchInService.punchIn(userId, context);
    }
}
