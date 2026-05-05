package Modelo.Impl;

import Modelo.Asistencia;
import Modelo.Dao.AsistenciaDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.BooleanProperty;

public class AsistenciaDAOImpl extends BaseDAO implements AsistenciaDAO {

    /**
     * Constructor de la clase
     * @throws Exception Posuble Excepcion
     */
    public AsistenciaDAOImpl() throws Exception {
    }

    /**
     * Guarda en la BD, la asistencia de un empleado en una determinada fecha
     * @param asis Datos del empleado a guardar
     * @param fecha fecha de la asistencia a guardar
     * @return true, si se guardo correctamente/ false en caso contrario
     * @throws Exception Posible error en consulta
     */
    @Override
    public boolean guardarAsistencia(Asistencia asis, LocalDate fecha) throws Exception {
        String sql = "{CALL guardarAsistencia(?, ?, ?)}";
        try (CallableStatement cs = connection.prepareCall(sql)) {
            // Obtenemos el estado de asistencia solo para el día específico de 'fecha'
            int dia = fecha.getDayOfMonth();
            boolean asistio = asis.insertarDiaProperty(dia).get();

            cs.setInt(1, asis.getId());
            cs.setDate(2, Date.valueOf(fecha));
            cs.setBoolean(3, asistio);

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Recupera de la BD una lista de asistencias de cada empleado en un determinado mes y anio.
     * @param year anio a buscar
     * @param mes mes a buscar
     * @return Lista de asistencias de empleados en un mes
     * @throws Exception Posible error en consulta
     */
    @Override
    public List<Asistencia> obtenerAsistencias(int year, int mes) throws Exception {
        List<Asistencia> lista = new ArrayList<>();
        Map<Integer, Asistencia> mapa = new HashMap<>();

        String sql = "{CALL obtener_asistencias_mes(?, ?)}";

        try (CallableStatement cs = connection.prepareCall(sql)) {

            cs.setInt(1, year);
            cs.setInt(2, mes);

            ResultSet rs = cs.executeQuery();

            // recupera datos de una asistencia
            while (rs.next()) {
                int id = rs.getInt("id_empleado");
                String usuario = rs.getString("usuario");
                int dia = rs.getInt("dia");
                boolean asistio = rs.getBoolean("asistio");
                
                // Inicializa una asistencia
                Asistencia asistencia = mapa.get(id);

                // Si el mapa no devuelve nada, queda en null y es necesario crearla con los datos del usuario
                if (asistencia == null) {
                    asistencia = new Asistencia(id, usuario);
                    // inserta en el mapa el id del empleado que es la llave del map y el objeto asistencia
                    mapa.put(id, asistencia);
                }
                // Inserta el dia en la asistencia y lo cambia a true o false.
                asistencia.insertarDiaProperty(dia).set(asistio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Crea una lista con los valores del hashmap que serian asistencias
        lista.addAll(mapa.values());
        return lista;
    }

    /**
     * Verifica si un dia ya fue registrada la asistencia.
     * Se usa para verificar si el dia de hoy(actual) ya fue registrado y no marcarlo como disponible
     * @param fecha Fwecha del dia a consultar
     * @return true, si ya se registro asistencia/ false en caso contrario
     * @throws Exception Posible error en consulta
     */
    @Override
    public boolean diaRegistrado(LocalDate fecha) throws Exception {
        // Solo devolvemos true si existen registros para todos los empleados en esa fecha Y todos están bloqueados
        String sql = "SELECT COUNT(a.id_empleado) = (SELECT COUNT(*) FROM empleado WHERE estado = TRUE) AND COUNT(*) = SUM(bloqueado = TRUE) FROM asistencia a WHERE a.fecha = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Genera asistencia todos en false, en una determinada fecha
     * @param fecha Fecha en la cual se generara asistencia
     * @return true si se pudo generar la asistencia, false en caso contrario.
     * @throws Exception Posible error en consulta
     */
    @Override
    public boolean generarAsistenciaDefecto(LocalDate fecha) throws Exception {
        String sql = "{CALL generarAsistenciaDefecto(?)}";

        try (CallableStatement cs = connection.prepareCall(sql)) {

            cs.setDate(1, Date.valueOf(fecha));
            cs.execute();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
