package mp3Reorganizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class mp3Reorganizer {

	public static void main(String[] args) {
		//organizar();		
		org();
	}
	
	public static void org(){
		File arquivo = new File(direitorio());
        File[] file = arquivo.listFiles();
		List<String> erros = new ArrayList<String>();
        if (file != null) {
            int length = file.length;
            for (int i = 0; i < length; ++i) {
            	
            	System.out.println(i + ":" + file[i].getName());
                if (file[i].isFile() && file[i].getName().substring(file[i].getName().lastIndexOf("."), file[i].getName().length()).equals(".mp3")) {
                    /*String ARQ = file[i].getPath();
                    String path = ARQ.replace(file[i].getName(), "");
                       File fileNovo = new File(path + msc.getNome() + ".mp3");    // Renomeando arquivo ou diretório  */
                	String nome = null;
                	
                	try{
            			InputStream input = new FileInputStream(new File(file[i].getPath()));
            			ContentHandler handler = new DefaultHandler();
            			Metadata metadata = new Metadata();
            			Parser parser = new Mp3Parser();
            			ParseContext parseCtx = new ParseContext();
            			parser.parse(input, handler, metadata, parseCtx);
            			input.close();	
            			
            			nome = metadata.get("title") + "_ " + metadata.get("xmpDM:artist");
            			
            		}catch(FileNotFoundException e){
            			e.printStackTrace();
            		}catch(IOException e){
            			e.printStackTrace();
            		}catch(SAXException e){
            			e.printStackTrace();
            		}catch(TikaException e){
            			e.printStackTrace();
            		}catch(StringIndexOutOfBoundsException e){
            			e.printStackTrace();
            		}
                	
                	
                	String teste = file[i].getPath().replace(file[i].getName(), "");
                	File fileNovo = new File(teste + nome + ".mp3");
                    boolean success = file[i].renameTo(fileNovo);
                    if (!success)
                    	erros.add(file[i].getName());
                    
                }else
                	erros.add(file[i].getName());                                
            }
            if(!erros.isEmpty())
            	JOptionPane.showMessageDialog(null, join("\n",erros));
            else
            	JOptionPane.showMessageDialog(null, "Diretório organizado com sucesso!");
        } 
	}
	
	public static void organizar(){
		File arquivo = new File(direitorio());
        File[] file = arquivo.listFiles();
		List<Song> musicas = new ArrayList<Song>();
		List<String> erros = new ArrayList<String>();
        if (file != null) {
            int length = file.length;
            for (int i = 0; i < length; ++i) {
                Song msc = new Song(file[i].getPath());
                if(renomear(file[i],msc)){
                	erros.add(msc.getCaminho());
                }
            }
            if(!erros.isEmpty())
            	JOptionPane.showMessageDialog(null, join("\n",erros));
            else
            	JOptionPane.showMessageDialog(null, "Diretório organizado com sucesso!");
        } 
	}
	
	private static String join(String str, List<String> erros) {
		StringBuilder listString = new StringBuilder();

		for (String s : erros)
		{
		    listString.append(s).append(str);
		}
		return listString.toString();
	}

	private static boolean renomear(File file, Song msc){
		boolean erro = false;
		File fileAntigo = file;
        if (fileAntigo.isFile() && fileAntigo.getName().substring(fileAntigo.getName().lastIndexOf("."), fileAntigo.getName().length()).equals(".mp3")) {
            String ARQ = fileAntigo.getPath();
            String path = ARQ.replace(fileAntigo.getName(), "");
               File fileNovo = new File(path + msc.getNome() + ".mp3");    // Renomeando arquivo ou diretório  
            boolean success = fileAntigo.renameTo(fileNovo);
            if (!success) {
            	erro = true;
                //JOptionPane.showMessageDialog(null, "ERRO: " + msc.getCaminho());
            }
        }else
        	erro = true;
        
        return erro;
	}
	
	private static String direitorio() {
        String path = "";
        JFileChooser fc = new JFileChooser();
        // restringe a amostra a diretorios apenas
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = fc.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File diretorio = fc.getSelectedFile();
            path = diretorio.getAbsolutePath();
        } else {
            return null;
        }
        return path;
    }
}
