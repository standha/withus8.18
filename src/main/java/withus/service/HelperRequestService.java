package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_helper_request;
import withus.entity.Tbl_symptom_log;
import withus.repository.HelperRequestRepository;

@Service
public class HelperRequestService {
    private final HelperRequestRepository helperRequestRepository;

    @Autowired
    public HelperRequestService(HelperRequestRepository helperRequestRepository) {
        this.helperRequestRepository = helperRequestRepository;
    }

    @NonNull
    public Tbl_helper_request upsertHelperRequest(Tbl_helper_request tbl_helper_request) {
        Tbl_helper_request saved = helperRequestRepository.save(tbl_helper_request);
        return saved;
    }
}
