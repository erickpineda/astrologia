package net.erickpineda.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import net.erickpineda.models.Satelite;

public class SateliteServiceImpl implements SateliteService {
	/**
	 * Interface para interactuar con el persistent context.
	 */
	private EntityManager em;
	/**
	 * Nombre de la base de datos.
	 */
	private static final String BDD = "astrologia";

	/**
	 * Constructor para el servicio de satélites.
	 */
	public SateliteServiceImpl() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(BDD);

		em = emf.createEntityManager();
		sateliteEjemplo();
	}

	/**
	 * Crea por defecto el satélite Luna.
	 */
	private void sateliteEjemplo() {
		Satelite luna = new Satelite();
		luna.setNombrePlaneta("Tierra");
		luna.setNombreSatelite("luna");
		luna.setAreaDeSuperficie("38 millones de km²");
		luna.setMasa("7,349 × 10²² kg");
		luna.setDensidad("3,34 g/cm³");
		luna.setDiametro("3474 km");
		luna.setGravedad("1,62 m/s²");

		em.getTransaction().begin();
		em.persist(luna);
		em.getTransaction().commit();
	}

	/**
	 * Método que se encarga de crear y persistir los satélites en la BDD, a
	 * partir de toda su información dada.
	 * 
	 * @param nombreSatelite
	 *            Recibe el nombre del satélite.
	 * @param nombrePlaneta
	 *            Recibe el nombre del planeta.
	 * @param areaDeSuperficie
	 *            Recibe el área de superficie.
	 * @param masa
	 *            Recibe la masa del satélite.
	 * @param densidad
	 *            Recibe la densidad del satélite.
	 * @param diametro
	 *            Recibe el diámetro del satélite.
	 * @param gravedad
	 *            Recibe la gravedad del satélite.
	 * @return Retorna {@code true} si se ha persistido el satélite en la BDD,
	 *         sino retorna {@code false}.
	 */
	public boolean creaSatelite(String nombreSatelite, String nombrePlaneta,
			String areaDeSuperficie, String masa, String densidad,
			String diametro, String gravedad) {

		if (comprobarSiExisteSatelite(nombreSatelite) == false) {

			Satelite sat = new Satelite(nombreSatelite, nombrePlaneta);
			sat.setAreaDeSuperficie(areaDeSuperficie);
			sat.setMasa(masa);
			sat.setDensidad(densidad);
			sat.setDiametro(diametro);
			sat.setGravedad(gravedad);

			em.getTransaction().begin();
			em.persist(sat);
			em.getTransaction().commit();
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Método que lista satélites a partir de un satélite dado.
	 * 
	 * @param nombrePlaneta
	 *            Parámetro del nombre del planeta a buscar.
	 * @return Retorna una lista de satélites, sobre un planeta dado.
	 */
	public List<Satelite> listarSatelites(String nombrePlaneta) {

		TypedQuery<Satelite> query = em.createQuery("SELECT s FROM Satelite s"
				+ " WHERE s.nombrePlaneta = '" + nombrePlaneta + "'",
				Satelite.class);

		return query.getResultList();
	}

	/**
	 * Método que comprueba si existe un satélite dado.
	 * 
	 * @param nombreSatelite
	 *            Nombre del satélite a buscar si existe.
	 * @return Retorna {@code true} si existe el satélite.
	 */
	public boolean comprobarSiExisteSatelite(String nombreSatelite) {

		TypedQuery<Satelite> query = em.createQuery("SELECT s FROM Satelite s "
				+ "WHERE s.nombreSatelite = '" + nombreSatelite + "'",
				Satelite.class);

		if (query.getResultList().size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método que cierra la conexión a la BDD.
	 */
	public void cerrarConexion() {
		em.close();
	}

	/**
	 * Método que implementa la interface satélite, para encontrar todos los
	 * satélites existentes.
	 */
	@Override
	public List<Satelite> findAll() {
		TypedQuery<Satelite> query = em.createQuery(
				"SELECT p.nombreSatelite FROM Satelite p", Satelite.class);

		return query.getResultList();
	}
}
