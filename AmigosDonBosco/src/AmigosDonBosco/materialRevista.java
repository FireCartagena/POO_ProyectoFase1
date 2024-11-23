/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmigosDonBosco;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.sql.Date;

import AmigosDonBosco.ConexionBD;
import AmigosDonBosco.materialEscrito;

/**
 *
 * @author Cartagena
 */
public class materialRevista extends materialEscrito{
    private final String SQL_INSERT = "INSERT INTO `revista`(`codr`, `codTM`, `titulo`, `editorial`, `periocidad`, `fechaPublicacion`, `unidades`) VALUES (?,?,?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE `revista` SET `titulo`=?, `editorial`=?, `periocidad`=?, `fechaPublicacion`=?, `unidades`=? WHERE codc=?";
    private final String SQL_SELECT = "SELECT * FROM `revista` WHERE 1";
    private final String SQL_SELECTID = "SELECT * FROM `revista` WHERE codr=?";
    private final String SQL_DELETE = "DELETE FROM revista WHERE codr = ?";
    private final String SQL_SEARCH = "SELECT * FROM `revista` WHERE codr LIKE ?";
    
    // Componentes
    private JFrame frame;
    private JLabel labelId;
    private JLabel labelTitulo;
    private JLabel labelEditorial;
    private JLabel labelPeriocidad;
    private JLabel labelFechaPublicacion;
    private JLabel labelUnidades;
    private JTextField campoTitulo;
    private JTextField campoEditorial;
    private JTextField campoPeriocidad;
    private JTextField campoFechaPublicacion;
    private JTextField campoUnidades;
    private JButton botonActualizar;
    
    public int insert(String codr, String titulo, String editorial, String periocidad, Date fechaPublicacion, int unidades){
        
        Connection  conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int rows = 0; //registrosafectados
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;//contador de columnas
            stmt.setString(index++, codr);
            stmt.setInt(index++, this.codTM);
            stmt.setString(index++, titulo);
            stmt.setString(index++, editorial);
            stmt.setString(index++, periocidad);
            java.sql.Date fechaPublicacion_v = new java.sql.Date(fechaPublicacion.getTime());
            stmt.setDate(index++, fechaPublicacion_v);
            stmt.setInt(index++, unidades);
            
            System.out.println("Ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados:" + rows);
        } catch (SQLException e) {
            log.error("Error al procesar los datos.", e);
        } finally {
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
        }
        return rows;
    }
    
    public void update(String id){
        Connection  conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int rowCount = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_SELECTID);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                rowCount++;
            }
            
            if(rowCount > 0){
                ActualizarDatosFrame(rs);
            }else{
                JOptionPane.showMessageDialog(null, "El codigo no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("Se ejecuto query: " + SQL_SELECTID);
        } catch (SQLException e) {
            log.error("Error al procesar los datos.", e);
        } finally {
            ConexionBD.close(rs);
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
        }
    }
    
    public void ActualizarDatosFrame(ResultSet rs) {
        // Etiquetas y campos de texto
        int y = 20; // Posición inicial en Y
        int incrementoY = 40; // Espacio vertical entre componentes
        
        try{
            rs.beforeFirst();
            rs.next();
            
            // Configurar el JFrame
            frame = new JFrame("Actualizar datos LIBRO");
            frame.setTitle("Actualizar Datos LIBRO");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(null);
            
            labelId = new JLabel(rs.getString("codr"));
            labelId.setBounds(50, y, 100, 30); // Posición y tamaño
            y += incrementoY;
            
            labelTitulo = new JLabel("Titulo:");
            labelTitulo.setBounds(50, y, 100, 30);
            campoTitulo = new JTextField(rs.getString("titulo"));
            campoTitulo.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelEditorial = new JLabel("Editorial:");
            labelEditorial.setBounds(50, y, 100, 30);
            campoEditorial = new JTextField(rs.getString("editorial"));
            campoEditorial.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelPeriocidad = new JLabel("Periocidad:");
            labelPeriocidad.setBounds(50, y, 100, 30);
            campoPeriocidad = new JTextField(rs.getString("periocidad"));
            campoPeriocidad.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelFechaPublicacion = new JLabel("Fecha Publicacion:");
            labelFechaPublicacion.setBounds(50, y, 100, 30);
            campoFechaPublicacion = new JTextField(rs.getString("fechaPublicacion"));
            campoFechaPublicacion.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelUnidades = new JLabel("Unidades:");
            labelUnidades.setBounds(50, y, 100, 30);
            campoUnidades = new JTextField(rs.getString("unidades"));
            campoUnidades.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            // Acción para el botón de actualizar
            botonActualizar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Capturamos los datos en un HashMap
                    HashMap<String, String> datos = new HashMap<>();
                    datos.put("codr", labelId.getText());
                    datos.put("titulo", campoTitulo.getText());
                    datos.put("editorial", campoEditorial.getText());
                    datos.put("periocidad", campoPeriocidad.getText());
                    datos.put("fechaPublicacion", campoFechaPublicacion.getText());
                    datos.put("unidades", campoUnidades.getText());
                    
                    // Lo enviamos a la funcion
                    updateData(datos);
                    
                    // Cerramos
                    frame.dispose();
                }
            });
            
             // Agregar un WindowListener para detectar el cierre
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {}
            });
            
            // Agregar los componentes al JFrame
            frame.add(labelId);
            frame.add(labelTitulo);
            frame.add(campoTitulo);
            frame.add(labelEditorial);
            frame.add(campoEditorial);
            frame.add(labelPeriocidad);
            frame.add(campoPeriocidad);
            frame.add(labelFechaPublicacion);
            frame.add(campoFechaPublicacion);
            frame.add(labelUnidades);
            frame.add(campoUnidades);
            
            frame.add(botonActualizar);
            
            frame.setVisible(true);
        }catch (SQLException e) {
            log.error("Error al procesar los datos.", e);
        }
    }
    
   public void updateData(HashMap<String, String> datos){
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = ConexionBD.getConnection();
            System.out.println("Ejecutando query:" + SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            stmt.setString(index++, datos.get("titulo"));
            stmt.setString(index++, datos.get("editorial"));
            stmt.setString(index++, datos.get("periocidad"));
            stmt.setString(index++, datos.get("fechaPublicacion"));
            stmt.setString(index++, datos.get("unidades"));
            stmt.setString(index++, datos.get("codr"));
            rows = stmt.executeUpdate();
            System.out.println("Registros actualizados:" + rows);
        } catch (SQLException e) {
            log.error("Error al procesar los datos.", e);
        } finally {
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
        }
        
        if(rows != 0){
            JOptionPane.showMessageDialog(null, "Datos actualizados correctamente", "Success", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "No se actualizaron los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    
    public DefaultTableModel select(){
        DefaultTableModel dtm = new DefaultTableModel();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int numberOfColumns = meta.getColumnCount();
            //Formando encabezado
            for (int i = 1; i<= numberOfColumns; i++) {
                dtm.addColumn(meta.getColumnLabel(i));
            }
            //Creandolasfilaspara el JTable
            while (rs.next()) {
                Object[] fila = new Object[numberOfColumns];
                for (int i = 0; i<numberOfColumns; i++) {
                    fila[i]=rs.getObject(i+1);
                }
                dtm.addRow(fila);
            }
        } catch (SQLException e) {
            log.error("Error al procesar los datos.", e);
        } finally {
            ConexionBD.close(rs);
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
        }
        return dtm;
    }
    
    public int delete(String codr) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = ConexionBD.getConnection();
            System.out.println("Ejecutando query:" + SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, codr);
            rows = stmt.executeUpdate();
            System.out.println("Registros eliminados:" + rows);
        } catch (SQLException e) {
            log.error("Error al procesar los datos.", e);
        } finally {
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
        } 
        return rows;
    }
    
    public int countRows(){
        int rows = 0;
        
        Connection  conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            System.out.println("Ejecutando query: " + SQL_SELECT);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                rows++;
            }
        } catch (SQLException e) {
            log.error("Error al procesar los datos.", e);
        } finally {
            ConexionBD.close(rs);
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
        }
        return rows;
    }
}
