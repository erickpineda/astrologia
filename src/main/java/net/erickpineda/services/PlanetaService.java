package net.erickpineda.services;

import java.util.List;

import net.erickpineda.models.Planeta;

public interface PlanetaService {
	/**
	 * Interface de planetas, guardará en un a lista todos los planetas de la
	 * BDD.
	 * 
	 * @return Retorna una lista de planetas.
	 */
	public List<Planeta> findAll();
}
