package uniandes.edu.co.parranderos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.parranderos.modelo.Bebedor;

import java.util.Collection;


public interface BebedorRepository extends JpaRepository<Bebedor, Integer> {

    @Query(value = "SELECT * FROM bebedores", nativeQuery = true)
    Collection<Bebedor> darBebedores();

    @Query(value = "SELECT * FROM bebedores WHERE id = :id", nativeQuery = true)
    Bebedor darBebedor(@Param("id") long id);

    @Query(value = "SELECT * FROM bebedores b WHERE b.nombre LIKE '%' || :nombre || '%'", nativeQuery=true)
    Collection<Bebedor> darBebedoresPorNombre(@Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bebedores WHERE id = :id", nativeQuery = true)
    void eliminarBebedor(@Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE bebedores SET nombre = :nombre, ciudad = :ciudad, presupuesto = :presupuesto WHERE id = :id", nativeQuery = true)
    void actualizarBebedor(@Param("id") long id, @Param("nombre") String nombre, @Param("ciudad") String ciudad,
            @Param("presupuesto") String presupuesto);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bebedores (id, nombre, ciudad, presupuesto) VALUES ( parranderos_sequence.nextval , :nombre, :ciudad, :presupuesto)", nativeQuery = true)
    void insertarBebedor(@Param("nombre") String nombre, @Param("ciudad") String ciudad,
            @Param("presupuesto") String presupuesto);

    
    @Query(value = "SELECT COUNT(*)\r\n" + //
            "FROM (SELECT G.id_bebedor\r\n" + //
            "FROM (SELECT B.id\r\n" + //
            "FROM bebidas B\r\n" + //
            "WHERE grado_alcohol = (SELECT MAX(grado_alcohol) AS GRADO_MAX FROM bebidas)) B \r\n" + //
            "INNER JOIN gustan G ON B.ID = G.id_bebida) IDB\r\n" + //
            "INNER JOIN bebedores B ON IDB.id_bebedor = B.id\r\n", nativeQuery = true)
    int darNumeroDeBebedoresQueGustanDeBebidasConMayorGradoAlcohol();

    
    @Query(value = "SELECT COUNT(*)\r\n" + //
            "FROM (SELECT G.id_bebedor\r\n" + //
            "FROM (SELECT B.id\r\n" + //
            "FROM bebidas B\r\n" + //
            "WHERE grado_alcohol = (SELECT MIN(grado_alcohol) AS GRADO_MIN FROM bebidas)) B \r\n" + //
            "INNER JOIN gustan G ON B.ID = G.id_bebida) IDB\r\n" + //
            "INNER JOIN bebedores B ON IDB.id_bebedor = B.id\r\n", nativeQuery = true)
    int darNumeroDeBebedoresQueGustanDeBebidasConMenorGradoAlcohol();

}
