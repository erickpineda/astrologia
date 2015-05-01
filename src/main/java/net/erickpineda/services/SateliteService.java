package net.erickpineda.services;

import java.util.List;

import net.erickpineda.models.Satelite;

public interface SateliteService {
	/**
	 * Interface de sat�lites, guardar� en un a lista todos los sat�lites de la
	 * BDD.
	 * 
	 * @return Retorna una lista de sat�lites.
	 */
	public List<Satelite> findAll();
}
