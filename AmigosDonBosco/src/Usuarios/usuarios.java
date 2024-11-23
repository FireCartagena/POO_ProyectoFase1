/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuarios;

import AmigosDonBosco.ConexionBD;
import AmigosDonBosco.material;

import org.apache.log4j.Logger;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 *
 * @author Cartagena
 */
public class usuarios {
    static final Logger log = Logger.getLogger(material.class);
    
    private final String SQL_INSERT = "INSERT INTO `usuarios`( `nombre`, `username`, `password`, `tipo_usuario`) VALUES (?,?,?,?)";
    
    public int insert(String nombre, String usuario, String contra, String tipo){
        Connection  conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int rows = 0; //registrosafectados
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;//contador de columnas
            stmt.setString(index++, nombre);//param 1 => ?
            stmt.setString(index++, usuario);//param 2 => ?
            stmt.setString(index++, contra);//param 2 => ?
            stmt.setString(index++, tipo);//param 2 => ?
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
}
