package mp3Reorganizer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class mp3Reorganizer {

	public static void main(String[] args) {
		organizar();		
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
