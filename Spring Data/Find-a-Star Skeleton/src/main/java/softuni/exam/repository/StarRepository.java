package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.enums.StarType;

import java.util.List;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {

    boolean existsByName(String name);

    List<Star> findAllByStarTypeAndAstronomers_EmptyOrderByLightYears(StarType starType);

}
