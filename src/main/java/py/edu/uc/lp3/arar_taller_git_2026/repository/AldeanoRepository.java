package py.edu.uc.lp3.arar_taller_git_2026.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import py.edu.uc.lp3.arar_taller_git_2026.domain.Aldeano;

public interface AldeanoRepository extends JpaRepository<Aldeano, Long> {

    List<Aldeano> findByProfesionIgnoreCase(String profesion);

    List<Aldeano> findByVidaLessThan(int vida);

    long countByProfesionIgnoreCase(String profesion);
}
