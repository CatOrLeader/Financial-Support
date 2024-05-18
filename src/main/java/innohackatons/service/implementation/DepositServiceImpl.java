package innohackatons.service.implementation;

import innohackatons.api.model.GetAllDepositsInfoResponse;
import innohackatons.api.model.GetDepositInfoResponse;
import innohackatons.api.model.PostNewDepositResponse;
import innohackatons.entity.Bank;
import innohackatons.entity.Deposit;
import innohackatons.entity.User;
import innohackatons.repository.BankRepository;
import innohackatons.repository.DepositRepository;
import innohackatons.repository.UserRepository;
import innohackatons.service.DepositService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {
    private final DepositRepository depositRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;

    @Override
    public ResponseEntity<PostNewDepositResponse> createNewDeposit(long userId, long bankId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Bank bank = bankRepository.findById(bankId)
            .orElseThrow(() -> new RuntimeException("Bank not found"));

        Deposit deposit = new Deposit();
        deposit.setUser(user);
        deposit.setBank(bank);
        deposit.setAmount(BigDecimal.ZERO); // initial amount is zero

        Deposit savedDeposit = depositRepository.save(deposit);

        PostNewDepositResponse response = new PostNewDepositResponse(savedDeposit.getId());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GetDepositInfoResponse> getDeposit(long depositId) {
        Deposit deposit = depositRepository.findById(depositId)
            .orElseThrow(() -> new RuntimeException("Deposit not found"));

        GetDepositInfoResponse response = new GetDepositInfoResponse(
            deposit.getBank().getId(),
            deposit.getAmount().doubleValue()
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GetAllDepositsInfoResponse> getAllDepositsByUserId(long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found("));

        List<Deposit> deposits = depositRepository.findByUser(user);

        List<GetDepositInfoResponse> depositInfoResponses = deposits.stream()
            .map(deposit -> new GetDepositInfoResponse(deposit.getBank().getId(), deposit.getAmount().doubleValue()))
            .collect(Collectors.toList());

        GetAllDepositsInfoResponse response = new GetAllDepositsInfoResponse(depositInfoResponses, deposits.size());

        return ResponseEntity.ok(response);
    }
}
