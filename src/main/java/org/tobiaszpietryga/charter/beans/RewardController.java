package org.tobiaszpietryga.charter.beans;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/rewards")
@AllArgsConstructor
public class RewardController {
    private final ClientRepository clientRepository;
    private final RewardService rewardService;

    @GetMapping("/{clientId}")
    public ResponseEntity calculateRewards(
            @PathVariable Long clientId,
            @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        try {
            clientRepository.findById(clientId)
                    .orElseThrow(() -> new RewardProgramException("Client not found " + clientId));

            return ResponseEntity.ok().body(rewardService.calculate(clientId, date));
        } catch (RewardProgramException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}