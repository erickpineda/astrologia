package net.erickpineda.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "satelite")
public class Satelite implements Serializable {
	/**
	 * Identificador de la clase.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Nombre del satélite, que además será la clave primaria de la tabla
	 * Satélite.
	 */
	@Id
	private String nombreSatelite;
	/**
	 * Clave foránea de la tabla Planeta.
	 */
	@Column(name = "nombrePlaneta")
	private String nombrePlaneta;
	/**
	 * Masa del satélite.
	 */
	private String masa;
	/**
	 * Densidad del satélite.
	 */
	private String densidad;
	/**
	 * Área de la superficie, de un satélite.
	 */
	private String areaDeSuperficie;
	/**
	 * Diámetro del satélite.
	 */
	private String diametro;
	/**
	 * Gravedad del satélite.
	 */
	private String gravedad;

	public Satelite() {
		super();
	}

	public Satelite(String nombreSatelite, String nombrePlaneta) {
		this.nombreSatelite = nombreSatelite;
		this.nombrePlaneta = nombrePlaneta;
	}

	public String getNombrePlaneta() {
		return nombrePlaneta;
	}

	public void setNombrePlaneta(String nombrePlaneta) {
		this.nombrePlaneta = nombrePlaneta;
	}

	public String getNombreSatelite() {
		return nombreSatelite;
	}

	public void setNombreSatelite(String nombre) {
		this.nombreSatelite = nombre;
	}

	public String getMasa() {
		return masa;
	}

	public void setMasa(String masa) {
		this.masa = masa;
	}

	public String getDensidad() {
		return densidad;
	}

	public void setDensidad(String densidad) {
		this.densidad = densidad;
	}

	public String getAreaDeSuperficie() {
		return areaDeSuperficie;
	}

	public void setAreaDeSuperficie(String areaDeSuperficie) {
		this.areaDeSuperficie = areaDeSuperficie;
	}

	public String getDiametro() {
		return diametro;
	}

	public void setDiametro(String diametro) {
		this.diametro = diametro;
	}

	public String getGravedad() {
		return gravedad;
	}

	public void setGravedad(String gravedad) {
		this.gravedad = gravedad;
	}

	@Override
	public String toString() {
		return "Satelite [getNombrePlaneta()=" + getNombrePlaneta()
				+ ", getNombre()=" + getNombreSatelite() + ", getMasa()="
				+ getMasa() + ", getDensidad()=" + getDensidad()
				+ ", getAreaDeSuperficie()=" + getAreaDeSuperficie()
				+ ", getDiametro()=" + getDiametro() + ", getGravedad()="
				+ getGravedad() + "]";
	}

}
