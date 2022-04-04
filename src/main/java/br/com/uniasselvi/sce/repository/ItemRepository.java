package br.com.uniasselvi.sce.repository;

import br.com.uniasselvi.sce.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM item WHERE categoria_id=:ID_CATEGORIA", nativeQuery = true)
    List<Item> findItemByCategoria(@Param("ID_CATEGORIA") Long ID_CATEGORIA);

    @Query(value = "SELECT * FROM item WHERE id=:ID", nativeQuery = true)
    Item findItemById(@Param("ID") Long id);

    @Query(value = "SELECT * FROM item WHERE codigo=:CODIGO", nativeQuery = true)
    Item findItemByCodigo(@Param("CODIGO") Long CODIGO);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM item WHERE id=:ID", nativeQuery = true)
    void deleteItemById(@Param("ID") Long ID);

}
