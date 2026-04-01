package com._s.api.infra.schedules;

import com._s.api.domain.rent.RentRepository;
import com._s.api.domain.rent.RentStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class RentScheduler {

    private static final String SAO_PAULO_ZONE = "America/Sao_Paulo";
    private static final ZoneId SAO_PAULO_ZONE_ID = ZoneId.of(SAO_PAULO_ZONE);

    private final RentRepository rentRepository;

    public RentScheduler(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = SAO_PAULO_ZONE)
    public void updateRentsToAguardandoEntrega() {
        LocalDate today = LocalDate.now(SAO_PAULO_ZONE_ID);
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        rentRepository.updateStatusToAguardandoEntrega(
                start,
                end,
                List.of(
                        RentStatus.REALIZADO,
                        RentStatus.CONTRATO_ASSINADO,
                        RentStatus.PAGAMENTO_APROVADO
                )
        );
    }

    @Scheduled(cron = "0 0 0 * * *", zone = SAO_PAULO_ZONE)
    public void updateRentsToDevolucaoAtrasada() {
        LocalDate yesterday = LocalDate.now(SAO_PAULO_ZONE_ID).minusDays(1);
        LocalDateTime start = yesterday.atStartOfDay();
        LocalDateTime end = yesterday.plusDays(1).atStartOfDay();

        rentRepository.updateStatusToDevolucaoAtrasada(
                start,
                end,
                RentStatus.ENTREGUE
        );
    }
}
