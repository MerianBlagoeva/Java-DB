package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

    boolean existsByTownName(String townName);

    Town findByTownName(String town);
}
