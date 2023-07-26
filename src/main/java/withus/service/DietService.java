package withus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_diet;
import withus.repository.DietRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;

    public Tbl_diet getDiet(Integer id) {
        return dietRepository.findAllById(id);
    }

    public List<Tbl_diet> getListByCategory(String category) {
        return dietRepository.findByCategoryOrderById(category);
    }
}
