package modelos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.RequestContext;

//@WebServlet("/ImagemUpload")
public class ImagemUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// caminho da pasta de imagens:
    private static final String DIRETORIO = System.getProperty("user.dir") + "/upload_imagens/";
    // outra opcao : getServletContext().getRealPath("") + File.separator + pasta?!?;
 
    // Se quiser limitar o tamanho da imagem: - testar
    private static final int TAMANHO_MAX   = 1024 * 1024 * 10;  // 10MB
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// verifica se e multipart (parametro do form)
		if (!ServletFileUpload.isMultipartContent(request)) {
			
            PrintWriter writer = response.getWriter();
            //writer.println("Error: Form must has enctype=multipart/form-data.");
            //writer.flush();
            return;
        }
	    
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		//factory.setRepository(new File("\\temp"));
        //? factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);

		//Limitar o tamanho da foto (opcional):
		//upload.setFileSizeMax(TAMANHO_MAX);
         

        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    
                    if (!item.isFormField()) {
                        //String fileName = new File(item.getName()).getName();
                        //String filePath = uploadPath + File.separator + fileName;
                    	
                        String fileName = item.getName();    
                        //String root = getServletContext().getRealPath("/");
                        File path = new File(DIRETORIO);
                        if (!path.exists()) {
                        	path.mkdir();
                        }

                        // saves the file on disk
                        File uploadedFile = new File(path + fileName);
                        //System.out.println(uploadedFile.getAbsolutePath());
                        item.write(uploadedFile);
                        
                        //request.setAttribute("message", "Upload has been done successfully!");
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "There was an error: " + ex.getMessage());
        }
    }
}

/* SERVLET 3.0 Exemplo - testando
 * 
 	Part file = request.getPart("file"); // Retrieves <input type="file" name="file">
	InputStream input = file.getInputStream();
	OutputStream output = new FileOutputStream(new File(System.getProperty("user.dir") + "/pasta_fotos/", file.getName()));
	try {
        IOUtils.copy(input, output);
    } finally {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }
 */
