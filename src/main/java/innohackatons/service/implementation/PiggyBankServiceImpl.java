package innohackatons.service.implementation;

import innohackatons.api.exception.ConflictException;
import innohackatons.api.exception.NotFoundEntityException;
import innohackatons.api.model.DeletePiggyBankRequest;
import innohackatons.api.model.DeletePiggyBankResponse;
import innohackatons.api.model.GetAllPiggyBanksByUserResponse;
import innohackatons.api.model.GetPiggyBankInfoResponse;
import innohackatons.api.model.InternalPiggyBankResponse;
import innohackatons.api.model.PostInternalPiggyBankRequest;
import innohackatons.api.model.PostNewPiggyBankResponse;
import innohackatons.entity.Deposit;
import innohackatons.entity.PiggyBank;
import innohackatons.entity.User;
import innohackatons.repository.DepositRepository;
import innohackatons.repository.PiggyBankRepository;
import innohackatons.repository.UserRepository;
import innohackatons.service.PiggyBankService;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PiggyBankServiceImpl implements PiggyBankService {
    private static final String USER_EXCEPTION = "User not found";
    private static final String DEPOSIT_EXCEPTION = "Deposit not found";
    private static final String PBANK_EXCEPTION = "Piggy Bank not found";

    private final UserRepository userRepository;
    private final DepositRepository depositRepository;
    private final PiggyBankRepository piggyBankRepository;

    @Override
    public ResponseEntity<GetAllPiggyBanksByUserResponse> getAllPiggyBanksByUser(long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundEntityException(USER_EXCEPTION));

        var pBanks = piggyBankRepository.findByUser(user);

        return ResponseEntity.ok(
            new GetAllPiggyBanksByUserResponse(
                pBanks.stream()
                    .map(pb -> new GetPiggyBankInfoResponse(pb.getGoal(), pb.getId())).toList(),
                pBanks.size()
            )
        );
    }

    @Override
    public ResponseEntity<GetPiggyBankInfoResponse> getPiggyBank(long userId, String goal) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundEntityException(USER_EXCEPTION));

        var pBank = piggyBankRepository.findByUserAndGoal(user, goal)
            .orElseThrow(() -> new NotFoundEntityException(PBANK_EXCEPTION));

        return ResponseEntity.ok(
            new GetPiggyBankInfoResponse(pBank.getGoal(), pBank.getAmount().doubleValue())
        );
    }

    @Override
    public ResponseEntity<InternalPiggyBankResponse> internalAddTransactionProcess(
        long userId,
        PostInternalPiggyBankRequest request
    ) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundEntityException(USER_EXCEPTION));
        PiggyBank pBank = piggyBankRepository.findByUserAndGoal(user, request.goal())
            .orElseThrow(() -> new NotFoundEntityException(PBANK_EXCEPTION));
        Deposit deposit = depositRepository.findById(request.depositId())
            .orElseThrow(() -> new NotFoundEntityException(DEPOSIT_EXCEPTION));

        if (deposit.getAmount().doubleValue() < request.amount()) {
            throw new ConflictException("There is no such money on the deposit");
        }

        deposit.setAmount(deposit.getAmount().subtract(BigDecimal.valueOf(request.amount())));
        pBank.setAmount(pBank.getAmount().add(BigDecimal.valueOf(request.amount())));

        piggyBankRepository.save(pBank);
        depositRepository.save(deposit);

        return ResponseEntity.ok(
            new InternalPiggyBankResponse(pBank.getAmount().doubleValue())
        );
    }

    @Override
    public ResponseEntity<InternalPiggyBankResponse> internalRetrieveTransactionProcess(
        long userId,
        PostInternalPiggyBankRequest request
    ) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundEntityException(USER_EXCEPTION));
        PiggyBank pBank = piggyBankRepository.findByUserAndGoal(user, request.goal())
            .orElseThrow(() -> new NotFoundEntityException(PBANK_EXCEPTION));
        Deposit deposit = depositRepository.findById(request.depositId())
            .orElseThrow(() -> new NotFoundEntityException(DEPOSIT_EXCEPTION));

        if (pBank.getAmount().doubleValue() < request.amount()) {
            throw new ConflictException("There is no such money on the piggy bank");
        }

        deposit.setAmount(deposit.getAmount().add(BigDecimal.valueOf(request.amount())));
        pBank.setAmount(pBank.getAmount().subtract(BigDecimal.valueOf(request.amount())));

        piggyBankRepository.save(pBank);
        depositRepository.save(deposit);

        return ResponseEntity.ok(
            new InternalPiggyBankResponse(pBank.getAmount().doubleValue())
        );
    }

    @Override
    public ResponseEntity<PostNewPiggyBankResponse> createPiggyBank(long userId, String goal) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundEntityException(USER_EXCEPTION));

        Optional<PiggyBank> maybePBank = piggyBankRepository.findByUserAndGoal(user, goal);
        if (maybePBank.isPresent()) {
            throw new ConflictException("Piggy Bank already exists");
        }

        var pBank = piggyBankRepository.save(new PiggyBank()
            .setAmount(BigDecimal.ZERO)
            .setUser(user)
            .setGoal(goal));

        return ResponseEntity.ok(
            new PostNewPiggyBankResponse(pBank.getId())
        );
    }

    @Override
    public ResponseEntity<DeletePiggyBankResponse> deletePiggyBank(long userId, DeletePiggyBankRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundEntityException(USER_EXCEPTION));
        PiggyBank pBank = piggyBankRepository.findByUserAndGoal(user, request.goal())
            .orElseThrow(() -> new NotFoundEntityException(PBANK_EXCEPTION));
        Deposit deposit = depositRepository.findById(request.depositId())
            .orElseThrow(() -> new NotFoundEntityException(DEPOSIT_EXCEPTION));

        var moneyToTransfer = pBank.getAmount();
        deposit.setAmount(deposit.getAmount().add(moneyToTransfer));

        piggyBankRepository.delete(pBank);

        return ResponseEntity.ok(
            new DeletePiggyBankResponse(moneyToTransfer.doubleValue())
        );
    }
}
