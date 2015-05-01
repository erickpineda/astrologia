package net.erickpineda.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import net.erickpineda.models.Planeta;

public class PlanetaServiceImpl implements PlanetaService {
	/**
	 * Interface para interactuar con el persistent context.
	 */
	private EntityManager em;
	/**
	 * Nombre de la base de datos.
	 */
	private static final String BDD = "astrologia";

	/**
	 * Constructor para el servicio de planetas.
	 */
	public PlanetaServiceImpl() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory(BDD);
		em = emf.createEntityManager();

		planetaEjemplo();
	}

	/**
	 * Crear por defecto el planeta Tierra.
	 */
	private void planetaEjemplo() {
		Planeta tierra = new Planeta();
		tierra.setNombrePlaneta("Tierra");

		tierra.setNan(false);
		tierra.setDiamtero(null);
		tierra.setDistancia(null);
		tierra.setDistancia(null);

		em.getTransaction().begin();
		em.persist(tierra);
		em.getTransaction().commit();
	}

	/**
	 * Método que se encarga de crear y persistir los planetas en la BDD, a
	 * partir de toda su información dada.
	 * 
	 * @param nombrePlaneta
	 *            Recibe el nombre del planeta.
	 * @param esNAN
	 *            Recibe si el planeta es enano.
	 * @param velocidadPlaneta
	 *            Recibe la velocidad del planeta enano.
	 * @param distanciaPlaneta
	 *            Recibe la distancia del planeta enano.
	 * @param diametroPlaneta
	 *            Recibe el diámetro del planeta enano.
	 * @return Retonra {@code true} si el planeta ha persistido correctamente en
	 *         la BDD.
	 */
	public boolean crearPlaneta(String nombrePlaneta, boolean esNAN,
			String velocidadPlaneta, String distanciaPlaneta,
			String diametroPlaneta) {

		boolean ok = false;
		Planeta pla = new Planeta(nombrePlaneta, esNAN);

		if (esNAN == true) {
			pla.setVelocidad(velocidadPlaneta);
			pla.setDistancia(distanciaPlaneta);
			pla.setDiamtero(diametroPlaneta);

			em.getTransaction().begin();
			em.persist(pla);
			em.getTransaction().commit();
		} else {
			em.getTransaction().begin();
			em.persist(pla);
			em.getTransaction().commit();
		}

		return ok;
	}

	/**
	 * Método para comprobar la existencia de un planeta.
	 * 
	 * @param nombrePlaneta
	 *            Recibe el nombre del planeta a buscar.
	 * @return Retorna {@code true} si el planeta existe.
	 */
	public boolean comprobarSiExistePlaneta(String nombrePlaneta) {

		TypedQuery<Planeta> query = em.createQuery(
				"SELECT p.nombrePlaneta FROM Planeta p"
						+ " WHERE p.nombrePlaneta = '" + nombrePlaneta + "'",
				Planeta.class);

		if (query.getResultList().size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método que lista los planetas a partir de un satélite dado.
	 * 
	 * @param nombreSatelite
	 *            Recibe el nombre del satélite.
	 * @return Retorna una lista de planeta.
	 */
	public List<Planeta> listarPlanetas(String nombreSatelite) {
		TypedQuery<Planeta> q = em.createQuery(
				"SELECT p FROM Planeta p INNER JOIN Satelite s "
						+ "on (p.nombrePlaneta = s.nombrePlaneta) "
						+ "WHERE s.nombreSatelite = '" + nombreSatelite + "'",
				Planeta.class);

		return q.getResultList();
	}

	/**
	 * Método que cierra la conexión contra la base de datos.
	 */
	public void cerrarConexion() {
		em.close();
	}

	/**
	 * Método que implementa la interface planeta, para encontrar todos los
	 * planetas existentes.
	 */
	@Override
	public List<Planeta> findAll() {
		TypedQuery<Planeta> query = em.createQuery(
				"SELECT p.nombrePlaneta FROM Planeta p", Planeta.class);

		return query.getResultList();
	}
}
