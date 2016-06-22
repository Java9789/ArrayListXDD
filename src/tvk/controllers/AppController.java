package tvk.controllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel;
import tvk.models.Persona;
import tvk.views.App;

public class AppController implements ActionListener, MouseListener {
    private App app;
    private List<Persona> list_persona;
    private boolean flag = true;
    
    public AppController(){
        list_persona = new ArrayList<>();
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                try {
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    UIManager.setLookAndFeel(new SubstanceRavenGraphiteGlassLookAndFeel());
                    app = new App();
                    app.setVisible(true);
                    start();
                } catch(Exception e){
                    System.err.println(e.getMessage());
                }
            } 
        });
    }
    
    public void start(){
        list_persona.add(new Persona("XDDD", 12, 12000));
        list_persona.add(new Persona("XDDD", 5, 4000));
        list_persona.add(new Persona("XDDD", 9, 5000));
        ver_personas();
        app.btnGuardar.setActionCommand("Guardar");
        app.btnGuardar.addActionListener(this);
        app.btnEliminar.setActionCommand("Eliminar");
        app.btnEliminar.addActionListener(this);
        app.btnNuevo.setActionCommand("Nuevo");
        app.btnNuevo.addActionListener(this);
        app.btnEliminarTodo.setActionCommand("Eliminar Todo");
        app.btnEliminarTodo.addActionListener(this);
        app.tbPersona.addMouseListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        switch(command){
            case "Guardar":
                if(flag){
                    guardar_persona();
                } else {
                    actualizar_persona();
                }
                break;
            case "Eliminar":
                eliminar_persona();
                break;
            case "Eliminar Todo":
                eliminar_todo();
                break;
            case "Nuevo":
                nuevo();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e){        
        seleccionar_persona();
    }

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    private void ver_personas(){
        Object[] arr = new Object[4];
        for(int i=0;i<list_persona.size();i++){ 
            arr[0] = i + "";
            arr[1] = list_persona.get(i).getNombre();           
            arr[2] = list_persona.get(i).getHoras();
            arr[3] = list_persona.get(i).getSueldo();
            app.m_persona.addRow(arr);
        }       
        app.tbPersona.setModel(app.m_persona);
    }
    
    private void limpiar_tabla(){
        int filas = app.tbPersona.getRowCount();        
        for(int i=0;i<filas;i++){
            app.m_persona.removeRow(0);
        }        
    }

    private void nuevo(){
        app.txtNombre.setText("");
        app.txtHoras.setText("");
        app.txtSueldo.setText("");
        app.txtNombre.requestFocus();
        flag = true;
        limpiar_tabla();
        ver_personas();
    }    
    
    private void guardar_persona(){
        try {
            String nombre = app.txtNombre.getText();
            int horas = Integer.parseInt(app.txtHoras.getText());
            double sueldo = Double.parseDouble(app.txtSueldo.getText());
            list_persona.add(new Persona(nombre, horas, sueldo));
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Escriba un número en las horas y/o sueldo.", "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
        nuevo();
    }
    
    private void eliminar_persona(){
        list_persona.remove(app.tbPersona.getSelectedRow());
        nuevo();
    }

    private void eliminar_todo(){
        list_persona.clear();
        nuevo();
    }
    
    private void seleccionar_persona(){
        try {
            int row = app.tbPersona.getSelectedRow();
            app.txtNombre.setText(app.m_persona.getValueAt(row, 1).toString());
            app.txtHoras.setText(app.m_persona.getValueAt(row, 2).toString());
            app.txtSueldo.setText(app.m_persona.getValueAt(row, 3).toString());            
        } catch(Exception e){
            e.printStackTrace();
        }
        flag = false;
    }

    private void actualizar_persona(){
        int num = app.tbPersona.getSelectedRow();
        try {
            String nombre = app.txtNombre.getText();
            int horas = Integer.parseInt(app.txtHoras.getText());
            double sueldo = Double.parseDouble(app.txtSueldo.getText());            
            list_persona.set(num, new Persona(nombre, horas, sueldo));            
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Escriba un número en las horas y/o sueldo", "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
        nuevo();
    }
    
}