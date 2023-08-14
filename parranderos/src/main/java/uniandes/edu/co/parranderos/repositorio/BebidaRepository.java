package uniandes.edu.co.parranderos.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.parranderos.modelo.Bebida;

import java.util.Collection;


public interface BebidaRepository extends JpaRepository<Bebida, Integer> {

        public interface RespuestaInformacionBebidas {

                int getTOTAL_BEBIDAS();
                double getPROMEDIO_GRADO();
                int getMAYOR_GRADO();
                int getMENOR_GRADO();
        }


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

        @Query(value = "SELECT DISTINCT B.*\r\n" + //
        "FROM bebidas B\r\n" + //
        "INNER JOIN sirven SB ON B.ID = SB.ID_BEBIDA\r\n" + //
        "INNER JOIN bares BR ON SB.ID_BAR = BR.ID\r\n" + //
        "WHERE BR.CIUDAD = :ciudad AND B.GRADO_ALCOHOL BETWEEN :mingrado AND :maxgrado", nativeQuery = true)
        Collection<Bebida> darBebidasPorCiudadYGrado(@Param("ciudad") String ciudad,
        @Param("mingrado") int minGradoAlcohol, @Param("maxgrado") int maxGradoAlcohol);

        @Query(value = "SELECT COUNT(*) AS TOTAL_BEBIDAS, \r\n" + //
                        "ROUND(AVG(grado_alcohol),2) AS PROMEDIO_GRADO, \r\n" + //
                        "MAX(grado_alcohol) AS MAYOR_GRADO, \r\n" + //
                        "MIN(grado_alcohol) AS MENOR_GRADO \r\n" + //
                        "FROM bebidas", nativeQuery = true)
        Collection<RespuestaInformacionBebidas> darInformacionBebidas();
}
