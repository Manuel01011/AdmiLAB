package instrumentos.Presentations.Calibraciones;

import instrumentos.logic.Calibraciones;
import instrumentos.logic.Instrumento;
import instrumentos.logic.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.apache.pdfbox.pdmodel.font.PDType1Font.TIMES_ITALIC;


public class Controller {
        ViewCalibracion view;
        instrumentos.Presentations.Calibraciones.Model model;
        instrumentos.Presentations.Instrumento.Controller contro;

        public Controller(ViewCalibracion view, Model model) {
            model.init(Service.instance().search2(new Instrumento(),new Calibraciones()));
            this.view = view;
            this.model = model;
            //model.setInstrumento(contro.curre());
            view.setController(this);
            view.setModel(model);
        }

        public Model getModel(){
            return model;
        }
    public void search2(Instrumento a,Calibraciones filter) throws  Exception {
        List<Calibraciones> rows = Service.instance().search2(a,filter);
        if (rows.isEmpty()) {
            throw new Exception("NINGUN REGISTRO COINCIDE");
        }
        model.setList(rows);
        // model.setCurrent(new TipoInstrumento());
        model.setCurrent(rows.get(0));
        model.commit();
    }
    public void modificar(Instrumento a,Calibraciones _calibracion) throws Exception {
        Service.instance().update(a,_calibracion);
        this.search2(a,new Calibraciones()); //sirve para acutualizar la lista
    }
    public void borrar2(Instrumento a,Calibraciones _istrumento) throws Exception {
        Service.instance().deletee(a,_istrumento);
        model.setModo(1);
        this.search2(a,new Calibraciones());
    }
        public void save(Instrumento b,Calibraciones a) throws Exception {
            try {
                Service.instance().createe(b,a);
                model.setModo(1);
                this.search2(b,new Calibraciones());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        public void edit(int row){
            //poner el current y cambiar el modo
            Calibraciones e = model.getInstrumento().getLista().get(row);
            try {
                model.setCurrent(Service.instance().readd(model.getInstrumento(),e));
                model.setListMedicione(e.getListMediciones());
                model.commit();
            } catch (Exception ex) {}
        }


        public void setIstrumento(Instrumento a){
            model.setInstrumento(a);
            model.commit();

        }

    public void imprimir(Instrumento a) {
        try {
            PDDocument document3 = new PDDocument();
            PDPage page = new PDPage();
            document3.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document3, page);

            // Cargar la imagen desde un archivo

            // Definir la fuente y el tamaño del texto
            PDFont fuente = new PDType1Font(TIMES_ITALIC.getCOSObject());
            contentStream.setFont(fuente, 12);
            File imagenFile = new File("C:\\Users\\Usuario\\Desktop\\InstrumentoV\\InstrumentoV\\InstrumentoV\\Instrumentos\\instrumentos.jpg");
            PDImageXObject imagen = PDImageXObject.createFromFileByContent(imagenFile, document3);
            contentStream.drawImage(imagen, 60, 480, imagen.getWidth()-180, imagen.getHeight()-180);

            // Agregar texto
            contentStream.beginText();
            contentStream.newLineAtOffset(170, 740);
            contentStream.showText("Lista de Calibraciones del Instrumentos seleccionado");
            contentStream.endText();

            contentStream.beginText();
            float x = 20; // Coordenada X inicial
            float y = 440; // Coordenada Y inicial
            for (int i =0; i < a.getLista().size(); i++) {
                contentStream.newLineAtOffset(x, y);
                contentStream.showText(a.getLista().get(i).toString());
                y =- 20;
                x =-1;
            }
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(170, 280);
            contentStream.showText("Lista de Mediciones del Instrumentos seleccionado");
            contentStream.endText();

            contentStream.beginText();
            float z = 20; // Coordenada X inicial
            float w = 240; // Coordenada Y inicial
            for (int m =0; m < a.getLista().size(); m++) {
                for (int j =0; j<a.getLista().get(m).getListMediciones().size();j++){
                    contentStream.newLineAtOffset(z, w);
                    contentStream.showText(a.getLista().get(m).getListMediciones().get(j).toString());
                    w =- 20;
                    z =-1;
                }
            }
            contentStream.endText();



            // Cerrar el contentStream
            contentStream.close();

            // Guardar el documento en un archivo
            document3.save("ListadeCalibraciones.pdf");
            document3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

