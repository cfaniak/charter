package org.tobiaszpietryga.charter.beans;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tobiaszpietryga.charter.dto.RewardDto;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rewards")
@AllArgsConstructor
public class RewardController {
    private final ClientRepository clientRepository;
    private final RewardService rewardService;

    @GetMapping("/{clientId}")
    public ResponseEntity calculateRewards(@PathVariable Long clientId) {
        try {
            clientRepository.findById(clientId)
                    .orElseThrow(() -> new RewardProgramException("Client not found " + clientId));

            return ResponseEntity.ok().body(rewardService.calculate(clientId, LocalDate.now()));
        } catch (RewardProgramException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}