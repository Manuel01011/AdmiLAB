package instrumentos.Presentations.Instrumento;

import instrumentos.Application;

import instrumentos.logic.Service;
import instrumentos.logic.Instrumento;
import instrumentos.logic.TipoInstrumento;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.File;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;

import static org.apache.pdfbox.pdmodel.font.PDType1Font.TIMES_ITALIC;

public class Controller {

        view view;
        instrumentos.Presentations.Instrumento.Model model;



        public Controller(view view, Model model) {
            model.init(Service.instance().search(new Instrumento()),Service.instance().search(new TipoInstrumento()));
            this.view = view;
            this.model = model;
            view.setController(this);
            view.setModel(model);
        }

        public Instrumento curre(){
            return model.getCurrent();
        }

        public void search(Instrumento filter) throws  Exception{
            List<Instrumento> rows = Service.instance().search(filter);
            if (rows.isEmpty()){
                throw new Exception("NINGUN REGISTRO COINCIDE");
            }
            model.setList(rows);
            // model.setCurrent(new TipoInstrumento());
            model.setCurrent(rows.get(0));
            model.commit();
        }

        public void modificar(Instrumento _istrumento) throws Exception {
            Service.instance().update(_istrumento);
            this.search(new Instrumento()); //sirve para acutualizar la lista
        }

        public void borrar(Instrumento _istrumento) throws Exception {
            Service.instance().delete(_istrumento);
            model.setModo(1);
            this.search(new Instrumento());
        }

        public void imprimir() throws FileNotFoundException {
                try {
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage();
                    document.addPage(page);

                    PDPageContentStream contentStreamm = new PDPageContentStream(document, page);

                    // Cargar la imagen desde un archivo

                    // Definir la fuente y el tamaño del texto
                    PDFont fuente = new PDType1Font(TIMES_ITALIC.getCOSObject());
                    contentStreamm.setFont(fuente, 10);
                    File imagenFile = new File("C:\\Users\\Usuario\\Desktop\\InstrumentoV\\InstrumentoV\\InstrumentoV\\Instrumentos\\instrumentos.jpg");
                    PDImageXObject imagen = PDImageXObject.createFromFileByContent(imagenFile, document);
                    contentStreamm.drawImage(imagen, 60, 480, imagen.getWidth()-180, imagen.getHeight()-180);

                    // Agregar texto
                    contentStreamm.beginText();
                    contentStreamm.newLineAtOffset(250, 740);
                    contentStreamm.showText("Lista de Instrumentos");
                    contentStreamm.endText();

                    contentStreamm.beginText();
                    float x = 10; // Coordenada X inicial
                    float y = 200; // Coordenada Y inicial;
                    contentStreamm.newLineAtOffset(x, y);
                    for (int i =0; i < model.list.size(); i++) {
                        contentStreamm.newLineAtOffset(x, y);
                        contentStreamm.showText(model.getList().get(i).toString());;
                        y =- 20;
                        x =-1;
                    }
                    contentStreamm.endText();

                    // Cerrar el contentStream
                    contentStreamm.close();

                    // Guardar el documento en un archivo
                    document.save("archivo.pdf");
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }

        public void save(Instrumento a) throws Exception {
            try {
                Service.instance().create(a);
                model.setModo(1);
                this.search(new Instrumento());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        public void edit(int row){
            //poner el current y cambiar el modo
            Instrumento e = model.getList().get(row);
            try {
                model.setCurrent(Service.instance().read(e));
                model.commit();
            } catch (Exception ex) {}
            Application.CALIBRACIONCONTROLLER.setIstrumento(model.getCurrent());
        }

        public Instrumento retornaIstrumento(int row){
            Instrumento e = model.getList().get(row);;
            return  e;
        }

        public void show(){
            model.setListTipos(Service.instance().search(new TipoInstrumento()));
            model.commit();
        }
    }

