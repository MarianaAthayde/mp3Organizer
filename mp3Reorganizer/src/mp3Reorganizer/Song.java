package mp3Reorganizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Song {
	private String caminho;
	private String titulo;
	private String artista;
	private String album;
	private String nome;
	
	public Song(String caminho) {
		this.caminho = caminho;
		try{
			InputStream input = new FileInputStream(new File(this.caminho));
			ContentHandler handler = new DefaultHandler();
			Metadata metadata = new Metadata();
			Parser parser = new Mp3Parser();
			ParseContext parseCtx = new ParseContext();
			parser.parse(input, handler, metadata, parseCtx);
			input.close();	
			
			this.titulo = metadata.get("title");
			this.artista = metadata.get("xmpDM:artist");
			this.album = metadata.get("xmpDM:album");
			this.setNome();
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(SAXException e){
			e.printStackTrace();
		}catch(TikaException e){
			e.printStackTrace();
		}
	}
	
	public boolean setNome(){
		boolean erro = false;
		if(this.titulo != null || this.artista != null){
			this.nome = this.titulo + "_ " + this.artista;
		}else
			erro = true;
		return erro;
	}
	
	public String getNome(){
		return nome;
	}
	
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	
}
