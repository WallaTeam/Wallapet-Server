/*
 * Nombre: Anuncio.java
 * Version 0.1
 * Autor: Ismael Rodriguez.
 * Fecha: 27-3-15
 * Descripcion: Este fichero implementa la representacion de un anuncio.
 * Copyright (C) 2015 Hyena Technologies
*/
package persistencia;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class Anuncio {

	// Atributos de un anuncio.
	private int idAnuncio;
	private String email;
	private String estado;
	private String descripcion;
	private String tipoIntercambio;
	private String especie;
	private String rutaImagen;
	private double precio;
	private String titulo;
	
	/**
	 * Pre: Cierto.
	 * Post: getPrecio() = this.precio.
	 */
	public double getPrecio() {
		return precio;
	}
	
	/**
	 * Pre: Cierto
	 * Post: this.precio = precio.
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}	
	
	/**
	 * Pre: Cierto
	 * Post: getTitulo() = this.titulo.
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * Pre: Cierto
	 * Post: this.titulo = titulo.
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * Pre: a != null
	 * Post: Transforma 'a' a JSON.
	 */
	public static String toJson(Anuncio a){
		Gson gson = new Gson();
		return gson.toJson(a);
	}
	
	/**
	 * Pre: a != null
	 * Post: Transforma 'a' a JSON.
	 */
	public static String listToJson(List<Anuncio> a){
		Gson gson = new Gson();
		return gson.toJson(a);
	}
	
	/**
	 * Pre: json != null
	 * Post: Obtiene un objeto de la clase Anuncio de json.
	 */
	public static Anuncio fromJson(String json) throws JsonParseException{
		Gson gson = new Gson();
		return gson.fromJson(json, Anuncio.class);
	}
	
	/**
	 * Pre: Cierto
	 * Post: getIdAnuncio() = this.idAnuncio.
	 */
	public int getIdAnuncio() {
		return idAnuncio;
	}
	
	/**
	 * Pre: Cierto
	 * Post: this.idAnuncio = idAnuncio.
	 */
	public void setIdAnuncio(int idAnuncio) {
		this.idAnuncio = idAnuncio;
	}
	
	/**
	 * Pre: Cierto
	 * Post: getEmail() = this.email.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Pre: Cierto
	 * Post: this.email = email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Pre: Cierto
	 * Post:  getEstado() = this.estado.
	 */
	public String getEstado() {
		return estado;
	}
	
	/**
	 * Pre: Cierto
	 * Post: this.estado = estado.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * Pre: Cierto
	 * Post: getDescripcion() = this.descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Pre: Cierto
	 * Post: this.descripcion = descripcion.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * Pre: Cierto
	 * Post: getTipoIntercambio() = this.tipoIntercambio.
	 */
	public String getTipoIntercambio() {
		return tipoIntercambio;
	}

	/**
	 * Pre: Cierto
	 * Post: this.tipoIntercambio = tipoIntercambio.
	 */
	public void setTipoIntercambio(String tipoIntercambio) {
		this.tipoIntercambio = tipoIntercambio;
	}
	
	/**
	 * Pre: Cierto
	 * Post: getEspecie() = this.especie.
	 */
	public String getEspecie() {
		return especie;
	}
	
	/**
	 * Pre: Cierto
	 * Post: this.especie = especie.
	 */
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	
	/**
	 * Pre: Cierto
	 * Post: getRutaImagen() = this.rutaImagen.
	 */
	public String getRutaImagen() {
		return rutaImagen;
	}
	
	/**
	 * Pre: Cierto
	 * Post: this.rutaImagen = rutaImagen.
	 */
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
}
