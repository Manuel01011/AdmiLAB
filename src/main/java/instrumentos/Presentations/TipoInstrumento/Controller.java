package instrumentos.Presentations.TipoInstrumento;
import instrumentos.logic.Service;
import instrumentos.logic.TipoInstrumento;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.apache.pdfbox.pdmodel.font.PDType1Font.TIMES_ITALIC;

public class Controller{
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new TipoInstrumento()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(TipoInstrumento filter) throws  Exception{
        List<TipoInstrumento> rows = Service.instance().search(filter);
        if (rows.isEmpty()){
            throw new Exception("NINGUN REGISTRO COINCIDE");
        }
        model.setList(rows);
       // model.setCurrent(new TipoInstrumento());
        model.setCurrent(rows.get(0));
        model.commit();
    }

    public void modificar(TipoInstrumento _istrumento) throws Exception {
        Service.instance().update(_istrumento);
        this.search(new TipoInstrumento()); //sirve para acutualizar la lista
    }

    public void borrar(TipoInstrumento _istrumento) throws Exception {
        Service.instance().delete(_istrumento);
        model.setModo(1);
        this.search(new TipoInstrumento());
    }


    public void save(TipoInstrumento a) throws Exception {
        try {
            Service.instance().create(a);
            model.setModo(1);
            this.search(new TipoInstrumento());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void edit(int row){
        //poner el current y cambiar el modo

        TipoInstrumento e = model.getList().get(row);
        try {
            model.setCurrent(Service.instance().read(e));
            model.commit();
        } catch (Exception ex) {}
    }

    public void imprimir() throws FileNotFoundException {
        try {
            PDDocument document2 = new PDDocument();
            PDPage page = new PDPage();
            document2.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document2, page);

            // Cargar la imagen desde un archivo

            // Definir la fuente y el tamaño del texto
            PDFont fuente = new PDType1Font(TIMES_ITALIC.getCOSObject());
            contentStream.setFont(fuente, 12);
           File imagenFile = new File("C:\\Users\\Usuario\\Desktop\\InstrumentoV\\InstrumentoV\\InstrumentoV\\Instrumentos\\instrumentos.jpg");
            PDImageXObject imagen = PDImageXObject.createFromFileByContent(imagenFile, document2);
            contentStream.drawImage(imagen, 60, 480, imagen.getWidth()-180, imagen.getHeight()-180);

            // Agregar texto
            contentStream.beginText();
            contentStream.newLineAtOffset(250, 740);
            contentStream.showText("Lista de Tipos Instrumentos");
            contentStream.endText();

            contentStream.beginText();
            float x = 20; // Coordenada X inicial
            float y = 400; // Coordenada Y inicial
            for (int i =0; i < model.getList().size(); i++) {
                contentStream.newLineAtOffset(x, y);
                contentStream.showText(model.list.get(i).toStriing());
                y =- 20;
                x =-1;
            }
            contentStream.endText();

            // Cerrar el contentStream
            contentStream.close();

            // Guardar el documento en un archivo
            document2.save("ListadeTiposInstrumentos.pdf");
            document2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
