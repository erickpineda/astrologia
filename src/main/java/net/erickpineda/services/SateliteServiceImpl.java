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
	 * Constructor para el servicio de sat�lites.
	 */
	public SateliteServiceImpl() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(BDD);

		em = emf.createEntityManager();
		sateliteEjemplo();
	}

	/**
	 * Crea por defecto el sat�lite Luna.
	 */
	private void sateliteEjemplo() {
		Satelite luna = new Satelite();
		luna.setNombrePlaneta("Tierra");
		luna.setNombreSatelite("luna");
		luna.setAreaDeSuperficie("38 millones de km�");
		luna.setMasa("7,349 � 10�� kg");
		luna.setDensidad("3,34 g/cm�");
		luna.setDiametro("3474 km");
		luna.setGravedad("1,62 m/s�");

		em.getTransaction().begin();
		em.persist(luna);
		em.getTransaction().commit();
	}

	/**
	 * M�todo que se encarga de crear y persistir los sat�lites en la BDD, a
	 * partir de toda su informaci�n dada.
	 * 
	 * @param nombreSatelite
	 *            Recibe el nombre del sat�lite.
	 * @param nombrePlaneta
	 *            Recibe el nombre del planeta.
	 * @param areaDeSuperficie
	 *            Recibe el �rea de superficie.
	 * @param masa
	 *            Recibe la masa del sat�lite.
	 * @param densidad
	 *            Recibe la densidad del sat�lite.
	 * @param diametro
	 *            Recibe el di�metro del sat�lite.
	 * @param gravedad
	 *            Recibe la gravedad del sat�lite.
	 * @return Retorna {@code true} si se ha persistido el sat�lite en la BDD,
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
	 * M�todo que lista sat�lites a partir de un sat�lite dado.
	 * 
	 * @param nombrePlaneta
	 *            Par�metro del nombre del planeta a buscar.
	 * @return Retorna una lista de sat�lites, sobre un planeta dado.
	 */
	public List<Satelite> listarSatelites(String nombrePlaneta) {

		TypedQuery<Satelite> query = em.createQuery("SELECT s FROM Satelite s"
				+ " WHERE s.nombrePlaneta = '" + nombrePlaneta + "'",
				Satelite.class);

		return query.getResultList();
	}

	/**
	 * M�todo que comprueba si existe un sat�lite dado.
	 * 
	 * @param nombreSatelite
	 *            Nombre del sat�lite a buscar si existe.
	 * @return Retorna {@code true} si existe el sat�lite.
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
	 * M�todo que cierra la conexi�n a la BDD.
	 */
	public void cerrarConexion() {
		em.close();
	}

	/**
	 * M�todo que implementa la interface sat�lite, para encontrar todos los
	 * sat�lites existentes.
	 */
	@Override
	public List<Satelite> findAll() {
		TypedQuery<Satelite> query = em.createQuery(
				"SELECT p.nombreSatelite FROM Satelite p", Satelite.class);

		return query.getResultList();
	}
}
