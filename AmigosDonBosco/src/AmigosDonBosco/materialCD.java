/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmigosDonBosco;

import AmigosDonBosco.materialAudioVisual;
import AmigosDonBosco.ConexionBD;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;



/**
 *
 * @author Cartagena
 */
public class materialCD  extends materialAudioVisual{
    private final String SQL_INSERT = "INSERT INTO `cd`(`codc`, `codTM`, `titulo`, `artista`, `genero`, `duracion`, `numeroCanciones`, `unidades`) VALUES (?,?,?,?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE `cd` SET `titulo`=?,`artista`=?,`genero`=?,`duracion`=?,`numeroCanciones`=?,`unidades`=? WHERE codc=?";
    private final String SQL_SELECT = "SELECT * FROM `cd` WHERE 1";
    private final String SQL_SELECTID = "SELECT * FROM `cd` WHERE codc=?";
    private final String SQL_DELETE = "DELETE FROM cd WHERE codc = ?";
    private final String SQL_SEARCH = "SELECT * FROM `cd` WHERE codc LIKE ?";
    
    private final int codTM = 1;
    
    // Componentes
    private JFrame frame;
    private JLabel labelId;
    private JLabel labelTitulo;
    private JLabel labelArtista;
    private JLabel labelGenero;
    private JLabel labelDuracion;
    private JLabel labelNumeroCanciones;
    private JLabel labelUnidades;
    private JTextField campoTitulo;
    private JTextField campoArtista;
    private JTextField campoGenero;
    private JTextField campoDuracion;
    private JTextField campoNumeroCanciones;
    private JTextField campoUnidades;
    private JButton botonActualizar;
    
    public void update(String id){
        Connection  conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int rowCount = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_SELECTID);
            stmt.setString(1, id);
            System.out.println("Ejecutando query: " + SQL_SELECTID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                rowCount++;
            }
            
            if(rowCount > 0){
                ActualizarDatosFrame(rs);
            }else{
                JOptionPane.showMessageDialog(null, "El codigo no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            log.error("Se produjo un error en la consulta.", e);
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
            // Configurar el JFrame
            frame = new JFrame("Actualizar datos DVD");
            frame.setTitle("Actualizar Datos CD");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(null); // Usar diseño nulo para posicionar manualmente los componentes

            // 
            labelId = new JLabel(rs.getString("codc"));
            labelId.setBounds(50, y, 100, 30); // Posición y tamaño
            y += incrementoY;
            
            labelTitulo = new JLabel("Titulo:");
            labelTitulo.setBounds(50, y, 100, 30);
            campoTitulo = new JTextField(rs.getString("titulo"));
            campoTitulo.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelArtista = new JLabel("Artista:");
            labelArtista.setBounds(50, y, 100, 30);
            campoArtista = new JTextField(rs.getString("artista"));
            campoArtista.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelGenero = new JLabel("Genero:");
            labelGenero.setBounds(50, y, 100, 30);
            campoGenero = new JTextField(rs.getString("genero"));
            campoGenero.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelDuracion = new JLabel("Duracion:");
            labelDuracion.setBounds(50, y, 100, 30);
            campoDuracion = new JTextField(rs.getString("duracion"));
            campoDuracion.setBounds(150, y, 200, 30);
            y += incrementoY;

            labelNumeroCanciones = new JLabel("NumeroCanciones:");
            labelNumeroCanciones.setBounds(50, y, 100, 30);
            campoNumeroCanciones = new JTextField(rs.getString("numeroCanciones"));
            campoNumeroCanciones.setBounds(150, y, 200, 30);
            y += incrementoY;

            labelUnidades = new JLabel("Unidades:");
            labelUnidades.setBounds(50, y, 100, 30);
            campoUnidades = new JTextField(rs.getString("unidades"));
            campoUnidades.setBounds(150, y, 200, 30);
            y += incrementoY;

            // Botón para actualizar
            botonActualizar = new JButton("Actualizar");
            botonActualizar.setBounds(150, y, 100, 30);

            // Acción para el botón de actualizar
            botonActualizar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Capturamos los datos en un HashMap
                    HashMap<String, String> datos = new HashMap<>();
                    datos.put("codc", labelId.getText());
                    datos.put("titulo", campoTitulo.getText());
                    datos.put("artista", campoArtista.getText());
                    datos.put("genero", campoGenero.getText());
                    datos.put("duracion", campoDuracion.getText());
                    datos.put("numeroCanciones", campoNumeroCanciones.getText());
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
            frame.add(labelArtista);
            frame.add(campoArtista);
            frame.add(labelGenero);
            frame.add(campoGenero);
            frame.add(labelDuracion);
            frame.add(campoDuracion);
            frame.add(labelNumeroCanciones);
            frame.add(campoNumeroCanciones);
            frame.add(labelUnidades);
            frame.add(campoUnidades);
            frame.add(botonActualizar);

            // Hacer visible el JFrame
            frame.setVisible(true);
        } catch (SQLException e) {
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
            stmt.setString(index++, datos.get("artista"));
            stmt.setString(index++, datos.get("genero"));
            stmt.setString(index++, datos.get("duracion"));
            stmt.setString(index++, datos.get("numeroCanciones"));
            stmt.setString(index++, datos.get("unidades"));
            stmt.setString(index++, datos.get("codc"));
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
    
    public int insert(String id, String titulo, String artista, String genero, int duracion, int numeroCanciones, int unidades){
        
        Connection  conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int rows = 0; //registrosafectados
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;//contador de columnas
            stmt.setString(index++, id);//param 1 => ?
            stmt.setInt(index++, this.codTM);//param 2 => ?
            stmt.setString(index++, titulo);//param 2 => ?
            stmt.setString(index++, artista);//param 2 => ?
            stmt.setString(index++, genero);//param 2 => ?
            stmt.setInt(index++, duracion);//param 2 => ?
            stmt.setInt(index++, numeroCanciones);//param 2 => ?
            stmt.setInt(index++, unidades);
            System.out.println("Ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados:" + rows);
        } catch (SQLException e) {
            log.error("Error al insertar los datos.", e);
        } finally {
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
        }
        return rows;
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
            log.error("Error al consultar los datos.", e);
        } finally {
            ConexionBD.close(rs);
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
        }
        return dtm;
    }
    
    public int delete(String codc) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = ConexionBD.getConnection();
            System.out.println("Ejecutando query:" + SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, codc);
            rows = stmt.executeUpdate();
            System.out.println("Registros eliminados:" + rows);
        } catch (SQLException e) {
            log.error("Error al eliminar los datos.", e);
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
           
            log.error("Error al consultar los datos.", e);
        } finally {
            ConexionBD.close(rs);
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
        }
        return rows;
    }

}
