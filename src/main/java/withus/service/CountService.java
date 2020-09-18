package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.ProgressKey;
import withus.entity.Tbl_button_count;
import withus.repository.CountRepository;

import javax.annotation.Nullable;

@Service
public class CountService {
    private final CountRepository countRepository;

    @Autowired
    public CountService(CountRepository countRepository){
        this.countRepository = countRepository;
    }

    @NonNull
    public Tbl_button_count upsertCount(Tbl_button_count tbl_button_count){
        Tbl_button_count saved = countRepository.save(tbl_button_count);
        return saved;
    }

    @Nullable
    public Tbl_button_count getCount(ProgressKey pk){
        return countRepository.findByKey(pk).orElse(null);
    }
}
