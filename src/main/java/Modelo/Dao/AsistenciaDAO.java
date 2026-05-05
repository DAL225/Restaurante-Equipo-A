package Modelo.Dao;

import Modelo.Asistencia;
import java.time.LocalDate;
import java.util.List;


public interface AsistenciaDAO {
    
    /**
     * Guarda en la BD, la asistencia de un empleado en una determinada fecha
     * @param asis Datos del empleado a guardar
     * @param fecha fecha de la asistencia a guardar
     * @return true, si se guardo correctamente/ false en caso contrario
     * @throws Exception Posible error en consulta
     */
    boolean guardarAsistencia(Asistencia asis, LocalDate fecha) throws Exception;
    
    /**
     * Recupera de la BD una lista de asistencias de cada empleado en un determinado mes y anio.
     * @param year anio a buscar
     * @param mes mes a buscar
     * @return Lista de asistencias de empleados en un mes
     * @throws Exception Posible error en consulta
     */
    List<Asistencia> obtenerAsistencias(int year, int mes) throws Exception ;
    
    /**
     * Verifica si un dia ya fue registrada la asistencia.
     * Se usa para verificar si el dia de hoy(actual) ya fue registrado y no marcarlo como disponible
     * @param fecha Fwecha del dia a consultar
     * @return true, si ya se registro asistencia/ false en caso contrario
     * @throws Exception Posible error en consulta
     */
    boolean diaRegistrado(LocalDate fechaHoy) throws Exception ;
    
    /**
     * Genera asistencia todos en false, en una determinada fecha
     * @param fecha Fecha en la cual se generara asistencia
     * @return true si se pudo generar la asistencia, false en caso contrario.
     * @throws Exception Posible error en consulta
     */
    boolean generarAsistenciaDefecto(LocalDate fecha) throws Exception;
}
