package net.erickpineda.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "planeta")
public class Planeta implements Serializable {
	/**
	 * Identificador de la clase.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Nombre del planeta, que además será el ID en la BDD.
	 */
	@Id
	private String nombrePlaneta;
	/**
	 * Lista de satélites, que sera la relación 1:N, entre las tablas Planeta
	 * que tiene muchos planetas.
	 */
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "nombrePlaneta")
	private List<Satelite> satelites;
	/**
	 * Para comprobar si es un planeta enano.
	 */
	private boolean nan;
	/**
	 * Diámetro del planeta enano.
	 */
	private String diamtero;
	/**
	 * Distancia del planeta enano.
	 */
	private String distancia;
	/**
	 * Velocidad del planeta enano.
	 */
	private String velocidad;

	public Planeta() {
		super();
	}

	public Planeta(String nombre, boolean esNAN) {
		this.nombrePlaneta = nombre;
		this.nan = esNAN;
	}

	public String getNombrePlaneta() {
		return nombrePlaneta;
	}

	public void setNombrePlaneta(String nombre) {
		this.nombrePlaneta = nombre;
	}

	public List<Satelite> getSatelites() {
		return satelites;
	}

	public void setSatelites(List<Satelite> satelites) {
		this.satelites = satelites;
	}

	public boolean isNan() {
		return nan;
	}

	public void setNan(boolean nan) {
		this.nan = nan;
	}

	public String getDiamtero() {
		return diamtero;
	}

	public void setDiamtero(String diamtero) {
		this.diamtero = diamtero;
	}

	public String getDistancia() {
		return distancia;
	}

	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}

	public String getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(String velocidad) {
		this.velocidad = velocidad;
	}

	@Override
	public String toString() {
		return "Planeta [getNombre()=" + getNombrePlaneta() + ", getSatelites()="
				+ getSatelites() + ", isNan()=" + isNan() + ", getDiamtero()="
				+ getDiamtero() + ", getDistancia()=" + getDistancia()
				+ ", getVelocidad()=" + getVelocidad() + "]";
	}

}
