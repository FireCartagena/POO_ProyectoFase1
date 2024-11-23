/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmigosDonBosco;

import java.sql.*;
import AmigosDonBosco.ConexionBD;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;

import AmigosDonBosco.materialEscrito;

/**
 *
 * @author Cartagena
 */
public class materialLibro extends materialEscrito{
    private final String SQL_INSERT = "INSERT INTO `libro`(`codl`, `codTM`, `titulo`, `autor`, `numeroPaginas`, `editorial`, `isbn`, `anioPublicacion`, `unidades`) VALUES (?,?,?,?,?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE `libro` SET `titulo`=?, `autor`=?, `numeroPaginas`=?, `editorial`=?, `isbn`=?, `anioPublicacion`=?, `unidades`=? WHERE codl=?";
    private final String SQL_SELECT = "SELECT * FROM `libro` WHERE 1";
    private final String SQL_SELECTID = "SELECT * FROM `libro` WHERE codl=?";
    private final String SQL_DELETE = "DELETE FROM libro WHERE codl = ?";
    private final String SQL_SEARCH = "SELECT * FROM `libro` WHERE codl LIKE ?";
    
    // Componentes
    private JFrame frame;
    private JLabel labelId;
    private JLabel labelTitulo;
    private JLabel labelAutor;
    private JLabel labelNumeroPaginas;
    private JLabel labelEditorial;
    private JLabel labelIsbn;
    private JLabel labelAnioPublicacion;
    private JLabel labelUnidades;
    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JTextField campoNumeroPaginas;
    private JTextField campoEditorial;
    private JTextField campoIsbn;
    private JTextField campoAnioPublicacion;
    private JTextField campoUnidades;
    private JButton botonActualizar;
    
    public int insert(String codl, String titulo, String autor, int numeroPaginas, String editorial, String isbn, String anioPublicacion, int unidades){
        Connection  conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int rows = 0; //registrosafectados
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;//contador de columnas
            stmt.setString(index++, codl);
            stmt.setInt(index++, this.codTM);
            stmt.setString(index++, titulo);
            stmt.setString(index++, autor);
            stmt.setInt(index++, numeroPaginas);
            stmt.setString(index++, editorial);
            stmt.setString(index++, isbn);
            stmt.setString(index++, anioPublicacion);
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
            frame.setSize(400, 600);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(null);
            
            labelId = new JLabel(rs.getString("codl"));
            labelId.setBounds(50, y, 100, 30); // Posición y tamaño
            y += incrementoY;
            
            labelTitulo = new JLabel("Titulo:");
            labelTitulo.setBounds(50, y, 100, 30);
            campoTitulo = new JTextField(rs.getString("titulo"));
            campoTitulo.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelAutor = new JLabel("Autor:");
            labelAutor.setBounds(50, y, 100, 30);
            campoAutor = new JTextField(rs.getString("autor"));
            campoAutor.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelNumeroPaginas = new JLabel("Numero paginas:");
            labelNumeroPaginas.setBounds(50, y, 100, 30);
            campoNumeroPaginas = new JTextField(rs.getString("numeroPaginas"));
            campoNumeroPaginas.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelEditorial = new JLabel("Editorial:");
            labelEditorial.setBounds(50, y, 100, 30);
            campoEditorial = new JTextField(rs.getString("editorial"));
            campoEditorial.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelIsbn = new JLabel("ISBN:");
            labelIsbn.setBounds(50, y, 100, 30);
            campoIsbn = new JTextField(rs.getString("isbn"));
            campoIsbn.setBounds(150, y, 200, 30);
            y += incrementoY;
            
            labelAnioPublicacion = new JLabel("Año de publicacion:");
            labelAnioPublicacion.setBounds(50, y, 100, 30);
            campoAnioPublicacion = new JTextField(rs.getString("anioPublicacion"));
            campoAnioPublicacion.setBounds(150, y, 200, 30);
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
                    datos.put("codl", labelId.getText());
                    datos.put("titulo", campoTitulo.getText());
                    datos.put("autor", campoAutor.getText());
                    datos.put("numeroPaginas", campoNumeroPaginas.getText());
                    datos.put("editorial", campoEditorial.getText());
                    datos.put("isbn", campoIsbn.getText());
                    datos.put("anioPublicacion", campoAnioPublicacion.getText());
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
            frame.add(labelAutor);
            frame.add(campoAutor);
            frame.add(labelNumeroPaginas);
            frame.add(campoNumeroPaginas);
            frame.add(labelEditorial);
            frame.add(campoEditorial);
            frame.add(labelIsbn);
            frame.add(campoIsbn);
            frame.add(labelAnioPublicacion);
            frame.add(campoAnioPublicacion);
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
            stmt.setString(index++, datos.get("autor"));
            stmt.setString(index++, datos.get("numeroPaginas"));
            stmt.setString(index++, datos.get("editorial"));
            stmt.setString(index++, datos.get("isbn"));
            stmt.setString(index++, datos.get("anioPublicacion"));
            stmt.setString(index++, datos.get("unidades"));
            stmt.setString(index++, datos.get("codl"));
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
    
    public int delete(String codl) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = ConexionBD.getConnection();
            System.out.println("Ejecutando query:" + SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, codl);
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
