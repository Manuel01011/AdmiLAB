package instrumentos;

import instrumentos.Presentations.AcercaDe.ControlerAcercade;
import instrumentos.Presentations.AcercaDe.ModelAcercaDe;
import instrumentos.Presentations.AcercaDe.viewAcercade;
import instrumentos.Presentations.Calibraciones.ViewCalibracion;
import instrumentos.Presentations.Instrumento.view;
import instrumentos.Presentations.TipoInstrumento.Controller;
import instrumentos.Presentations.TipoInstrumento.Model;
import instrumentos.Presentations.TipoInstrumento.View;
import instrumentos.logic.Service;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Application {
    public static void main(String[] args) {
        try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {};

        window = new JFrame();
        window.setContentPane(new JTabbedPane());
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Service.instance().stop();
            }
        });

        Model tiposModel= new Model();
        instrumentos.Presentations.Instrumento.Model tiposModel2 = new instrumentos.Presentations.Instrumento.Model();
        instrumentos.Presentations.Calibraciones.Model tiposModel3 = new instrumentos.Presentations.Calibraciones.Model();
        ModelAcercaDe model4 = new ModelAcercaDe();

        View tiposView = new View();
        view tiposVie = new view();
        ViewCalibracion tiposVi = new ViewCalibracion();
        instrumentos.Presentations.AcercaDe.viewAcercade acercaVi = new viewAcercade();

        tiposController = new Controller(tiposView,tiposModel);
        INSTRUMENTOCONTROLLER = new instrumentos.Presentations.Instrumento.Controller(tiposVie,tiposModel2);
        CALIBRACIONCONTROLLER = new instrumentos.Presentations.Calibraciones.Controller(tiposVi,tiposModel3);
        controlerAcercade = new ControlerAcercade(model4, acercaVi);

        window.getContentPane().add("Tipos de Instrumento",tiposView.getPanel());
        window.getContentPane().add("Instrumento",tiposVie.getPanel1());
        window.getContentPane().add("Calibraciones",tiposVi.getPanel2());
        window.getContentPane().add("Acerca de",acercaVi.getPanel3());

        window.setSize(900,450);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage((new ImageIcon(Application.class.getResource("presentation/icons/icon.png"))).getImage());
        window.setTitle("SILAB: Sistema de Laboratorio Industrial");
        window.setVisible(true);

    }

    public static Controller tiposController;
    public static instrumentos.Presentations.Instrumento.Controller INSTRUMENTOCONTROLLER;
    public static instrumentos.Presentations.Calibraciones.Controller CALIBRACIONCONTROLLER;
    public static JFrame window;
    public static instrumentos.Presentations.AcercaDe.ControlerAcercade controlerAcercade;
}
