package net.erickpineda.services;

import java.util.List;

import net.erickpineda.models.Satelite;

public interface SateliteService {
	/**
	 * Interface de satélites, guardará en un a lista todos los satélites de la
	 * BDD.
	 * 
	 * @return Retorna una lista de satélites.
	 */
	public List<Satelite> findAll();
}
