package uniandes.edu.co.parranderos.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.parranderos.modelo.Bebida;

import java.util.Collection;


public interface BebidaRepository extends JpaRepository<Bebida, Integer> {

        @Query(value = "SELECT * FROM bebidas", nativeQuery = true)
        Collection<Bebida> darBebidas();

        @Query(value = "SELECT * FROM bebidas WHERE id = :id", nativeQuery = true)
        Bebida darBebida(@Param("id") long id);

        @Modifying
        @Transactional
        @Query(value = "DELETE FROM bebidas WHERE id = :id", nativeQuery = true)
        void eliminarBebida(@Param("id") long id);

        @Modifying
        @Transactional
        @Query(value = "UPDATE bebidas SET nombre = :nombre, grado_alcohol = :grado_alcohol, tipo = :tipo WHERE id = :id", nativeQuery = true)
        void actualizarBebida(@Param("id") long id, @Param("nombre") String nombre,
                        @Param("grado_alcohol") Integer grado_alcohol,
                        @Param("tipo") Integer tipo);

        @Modifying
        @Transactional
        @Query(value = "INSERT INTO bebidas (id, nombre, grado_alcohol, tipo) VALUES ( parranderos_sequence.nextval , :nombre, :grado_alcohol, :tipo)", nativeQuery = true)
        void insertarBebida(@Param("nombre") String nombre, @Param("grado_alcohol") Integer grado_alcohol,
                        @Param("tipo") Integer tipo);

}