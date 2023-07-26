package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_diet;

import java.util.List;

@Repository
public interface DietRepository extends JpaRepository<Tbl_diet, Integer> {

    @Transactional
    Tbl_diet findAllById(Integer id);

    @Transactional
    List<Tbl_diet> findByCategoryOrderById(String category);
}
