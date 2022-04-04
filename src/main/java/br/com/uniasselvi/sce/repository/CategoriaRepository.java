package br.com.uniasselvi.sce.repository;

import br.com.uniasselvi.sce.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(value = "SELECT * FROM categoria", nativeQuery = true)
    List<Categoria> findCategorias();

    @Query(value = "SELECT * FROM categoria WHERE id=:ID", nativeQuery = true)
    Categoria findCategoriaById(@Param("ID") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM categoria WHERE id=:ID", nativeQuery = true)
    void deleteCategoriaById(@Param("ID") Long ID);
}
