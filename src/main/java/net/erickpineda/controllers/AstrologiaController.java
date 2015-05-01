package net.erickpineda.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import net.erickpineda.models.Planeta;
import net.erickpineda.models.Satelite;
import net.erickpineda.services.PlanetaServiceImpl;
import net.erickpineda.services.SateliteServiceImpl;

public class AstrologiaController implements Initializable {
	/**
	 * Recoge el nombre del planeta a crear.
	 */
	@FXML
	private TextField nombrePlaneta;
	/**
	 * Bot�n que activa si un planeta es enano o no.
	 */
	@FXML
	private ToggleButton NAN;
	/**
	 * Si el planeta es enano, se especifica su velocidad.
	 */
	@FXML
	private TextField velocidadPlaneta;
	/**
	 * Si el planeta es enano, se especifica la distancia del planeta.
	 */
	@FXML
	private TextField distanciaPlaneta;
	/**
	 * Si el planeta es enano, se especifica el di�metro del planeta.
	 */
	@FXML
	private TextField diametroPlaneta;
	/**
	 * Recoge el nombre del sat�lite a crear.
	 */
	@FXML
	private TextField nombreSatelite;
	/**
	 * Recoge el �rea de superficie del sat�lite a crear.
	 */
	@FXML
	private TextField areaSuperficieSatelite;
	/**
	 * Recoge la densidad del sat�lite a crear.
	 */
	@FXML
	private TextField densidadSatelite;
	/**
	 * Recoge la masa del sat�lite a crear.
	 */
	@FXML
	private TextField masaSatelite;
	/**
	 * Muestra la lista de planetas existentes, en el apartado Crear Sat�lites.
	 */
	@FXML
	private ComboBox<Planeta> comboboxPlaneta;
	/**
	 * Recoge la gravedad del sat�lite a crear.
	 */
	@FXML
	private TextField gravedadSatelite;
	/**
	 * Recoge el di�metro del sat�litea a crear.
	 */
	@FXML
	private TextField diametroSatelite;
	/**
	 * Muestra la lista de planetas existentes, en el apartado de buscar.
	 */
	@FXML
	private ComboBox<Planeta> comboboxBuscarPlaneta;
	/**
	 * Muestra la lista de sat�lites existentes, en el apartado de buscar.
	 */
	@FXML
	private ComboBox<Satelite> comboboxBuscarSatelite;
	/**
	 * Tabla que mostrar� los resultados de planetas o sat�lites a buscar.
	 */
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView myTable;
	/**
	 * Crea un servicio para hacer consultas sobre la BDD.
	 */
	private PlanetaServiceImpl plaSrv = new PlanetaServiceImpl();
	/**
	 * Crea un servicio para hacer consultas sobre la BDD.
	 */
	private SateliteServiceImpl satSrv = new SateliteServiceImpl();
	/**
	 * Lista de planetas.
	 */
	private List<Planeta> listaPlanetas;
	/**
	 * Lista de sat�lites.
	 */
	private List<Satelite> listaSatelites;
	/**
	 * Comprueba si el bot�n {@code ToggleButton NAN} est� activado.
	 */
	private boolean isNANOk = false;

	// La conexi�n al servidor de BDD, debe estar abierta.
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		desplegarPlanetas();
		desplegarSatelites();
	}

	/**
	 * Ventana emergente, para mostrar informaci�n de su creador.
	 */
	@FXML
	public void acercaDe(ActionEvent event) {
		infoAcerca();
	}

	/**
	 * M�todo que se ejecuta cuando se hace clic en el bot�n de crear planeta,
	 * en el apartado de crear planetas. Ejecuta el m�todo
	 * {@code comprobarCrearPlaneta()} quien se encarga de las comprobaciones,
	 * de si existe el planeta etc.
	 * 
	 * @param event
	 *            Evento que se dispar� cuando se hace clic en el bot�n.
	 */
	@FXML
	public void crearPlaneta(ActionEvent event) {
		// Para el nombre del planeta se ha de cumplir la restricci�n
		if (nombrePlaneta.getText().matches("[a-zA-Z0-9���� ����]{1,255}")) {
			comprobarCrearPlaneta();
		} else {
			mensajeError("�Cu�l es el nombre del Planeta?.");
		}
		desplegarPlanetas();
		desplegarSatelites();
	}

	/**
	 * M�todo que har� las comprobaciones necesarias antes de crear el planeta.
	 * Cuando un planeta es definido como enano (NAN), se deben rellenar otros
	 * campos obligatorios.
	 */
	private void comprobarCrearPlaneta() {
		if (isNANOk == true
				&& plaSrv.comprobarSiExistePlaneta(nombrePlaneta.getText()) == false) {

			// Si es enano, todos los campos para rellenar son obligatorios
			if (!velocidadPlaneta.getText().isEmpty()
					&& !distanciaPlaneta.getText().isEmpty()
					&& !diametroPlaneta.getText().isEmpty()) {

				plaSrv.crearPlaneta(nombrePlaneta.getText(), isNANOk,
						velocidadPlaneta.getText(), distanciaPlaneta.getText(),
						diametroPlaneta.getText());

				ventanaOK("planeta " + nombrePlaneta.getText());
				clear();
				isNANOk = false;

			} else {
				mensajeError("Termina de rellenar los campos.");
			}
			// Los nombres de los planetas son �nicos
		} else if (plaSrv.comprobarSiExistePlaneta(nombrePlaneta.getText()) == true) {
			mensajeError("El planeta " + nombrePlaneta.getText()
					+ " ya existe.");
		} else {
			// Si no es enano, los dem�s valores ser�n null
			plaSrv.crearPlaneta(nombrePlaneta.getText(), isNANOk, null, null,
					null);
			ventanaOK("planeta");
			clear();
		}
	}

	/**
	 * M�todo que se ejecuta cuando se hace clic en el bot�n de crear sat�lite,
	 * en el apartado de crear sat�lites. Ejecuta el m�todo
	 * {@code comprobarCrearSatelite()} quien se encarga de las comprobaciones,
	 * de si existe el sat�lite etc.
	 * 
	 * @param event
	 *            Evento que se dispar� cuando se hace clic en el bot�n.
	 */
	@FXML
	public void crearSatelite(ActionEvent event) {
		// Para el nombre del sat�lite se ha de cumplir la restricci�n
		if (nombreSatelite.getText().matches("[a-zA-Z0-9���� ����]{1,255}")) {
			comprobarCrearSatelite();
		} else {
			mensajeError("�Cual es el nombre del sat�lite?.");
		}
		desplegarPlanetas();
		desplegarSatelites();
	}

	/**
	 * M�todo que har� las comprobaciones necesarias antes de crear el sat�lite.
	 * Todos sus campos son obligatorios.
	 */
	private void comprobarCrearSatelite() {
		if (comboboxPlaneta.getValue() != null) {
			if (plaSrv.comprobarSiExistePlaneta(comboboxPlaneta.getEditor()
					.getText()) == true) {

				// Comprobar� si el planeta que se est� escribiendo o desplegado
				// en el combobox existe y luego crear� el sat�lite, en funci�n
				// de si todo es correcto.
				if (!nombreSatelite.getText().isEmpty()
						&& !comboboxPlaneta.getEditor().getText().isEmpty()
						&& !areaSuperficieSatelite.getText().isEmpty()
						&& !masaSatelite.getText().isEmpty()
						&& !densidadSatelite.getText().isEmpty()
						&& !diametroSatelite.getText().isEmpty()
						&& !gravedadSatelite.getText().isEmpty()) {

					if (satSrv.creaSatelite(nombreSatelite.getText(),
							comboboxPlaneta.getEditor().getText(),
							areaSuperficieSatelite.getText(),
							masaSatelite.getText(), densidadSatelite.getText(),
							diametroSatelite.getText(),
							gravedadSatelite.getText()) == true) {

						ventanaOK("satelite " + nombreSatelite.getText());
						clear();
					} else {
						mensajeError("El sat�lite ya existe.");
					}

				} else {
					mensajeError("Termina de rellenar los campos.");
				}

			} else {
				mensajeError("El planeta no es correcto o no existe");
			}
		} else {
			mensajeError("Selecciona el planeta.");
		}
	}

	/**
	 * M�todo que se ejecuta cuando se hace clic en el bot�n de mostrar
	 * sat�lites, del apartado buscar.
	 * 
	 * @param event
	 *            Evento que se dispara cuando se hace clic en el bot�n de
	 *            mostrar sat�lites.
	 */
	@FXML
	public void mostrarSatelites(ActionEvent event) {
		// Relleno la lista de sat�lites, a partir de la consulta a la BDD, que
		// efect�a el servicio de sat�lites.
		listaSatelites = satSrv.listarSatelites(comboboxBuscarPlaneta
				.getEditor().getText());

		// Antes de mostrar la informaci�n, comprueba que el planeta insertado
		// es correcto.
		if (plaSrv.comprobarSiExistePlaneta(comboboxBuscarPlaneta.getEditor()
				.getText()) == true) {

			mostrarTablaS(listaSatelites);

			if (myTable.getColumns().isEmpty()) {
				mensajeError("El planeta especificado, no tiene sat�lites.");
			}
			clear();

		} else {
			mensajeError("El planeta no existe.");
		}
	}

	/**
	 * M�todo que se ejecuta cuando se hace clic en el bot�n de mostrar planeta,
	 * del apartado buscar.
	 * 
	 * @param event
	 *            Evento que se dispara cuando se hace clic en el bot�n de
	 *            mostrar planeta.
	 */
	@FXML
	public void mostrarPlaneta(ActionEvent event) {
		// Relleno la lista de planetas, a partir de la consulta a la BDD, que
		// efect�a el servicio de planetas.
		listaPlanetas = plaSrv.listarPlanetas(comboboxBuscarSatelite
				.getEditor().getText());

		// Antes de mostrar la informaci�n, comprueba que el sat�lite insertado
		// es correcto.
		if (satSrv.comprobarSiExisteSatelite(comboboxBuscarSatelite.getEditor()
				.getText()) == true) {

			mostrarTablaP(listaPlanetas);
			clear();
		} else {
			mensajeError("El satelite no existe.");
		}
	}

	/**
	 * M�todo que muestra la informaci�n del/los sat�lite/s, a partir de un
	 * planeta en espec�fico.
	 * 
	 * @param lista
	 *            Lista de sat�lites que recibe como par�metro, para mostrar en
	 *            la tabla.
	 */
	@SuppressWarnings("unchecked")
	private void mostrarTablaS(List<Satelite> lista) {
		// Primero borro las columnas si las hay
		myTable.getColumns().clear();

		ObservableList<Satelite> ol = FXCollections.observableArrayList(lista);
		// Comprobar que el planeta insertado es correcto
		if (!comboboxBuscarPlaneta.getEditor().getText().isEmpty()) {
			setColumnasSatelites(lista);
		}

		myTable.setItems(ol);
	}

	/**
	 * M�todo que muestra la informaci�n del/los planeta/s, a partir de un
	 * sat�lite en espec�fico.
	 * 
	 * @param lista
	 *            Lista de planetas que recibe como par�metro, para mostrar en
	 *            la tabla.
	 */
	@SuppressWarnings("unchecked")
	private void mostrarTablaP(List<Planeta> lista) {
		// Primero borro las columnas si las hay
		myTable.getColumns().clear();

		ObservableList<Planeta> ol = FXCollections.observableArrayList(lista);
		// Comprobar que el sat�lite insertado es correcto
		if (!comboboxBuscarSatelite.getEditor().getText().isEmpty()) {
			setColumnasPlanetas(lista);
		}

		myTable.setItems(ol);
	}

	/**
	 * Cambia la morfologia de la tabla que se despliega a partir de una lista
	 * de sat�lites.
	 * 
	 * @param lista
	 *            Lista de sat�lites que recibe como par�metro, para mostrar en
	 *            la tabla.
	 */
	@SuppressWarnings("unchecked")
	private void setColumnasSatelites(List<Satelite> lista) {
		// Se definen las columnas
		TableColumn<Satelite, String> col1 = new TableColumn<Satelite, String>(
				"Planeta");
		TableColumn<Satelite, String> col2 = new TableColumn<Satelite, String>(
				"Satelite");
		TableColumn<Satelite, String> col3 = new TableColumn<Satelite, String>(
				"Masa");
		TableColumn<Satelite, String> col4 = new TableColumn<Satelite, String>(
				"Densidad");
		TableColumn<Satelite, String> col5 = new TableColumn<Satelite, String>(
				"Area de superficie");
		TableColumn<Satelite, String> col6 = new TableColumn<Satelite, String>(
				"Diametro");
		TableColumn<Satelite, String> col7 = new TableColumn<Satelite, String>(
				"Gravedad");

		for (int i = 0; i < lista.size(); i++) {
			// Se itera la lista para mostrar el resultado, en cada columna
			myTable.getColumns().setAll(col1, col2, col3, col4, col5, col6,
					col7);

			col1.setCellValueFactory(new PropertyValueFactory<Satelite, String>(
					"nombrePlaneta"));
			col2.setCellValueFactory(new PropertyValueFactory<Satelite, String>(
					"nombreSatelite"));
			col3.setCellValueFactory(new PropertyValueFactory<Satelite, String>(
					"masa"));
			col4.setCellValueFactory(new PropertyValueFactory<Satelite, String>(
					"densidad"));
			col5.setCellValueFactory(new PropertyValueFactory<Satelite, String>(
					"areaDeSuperficie"));
			col6.setCellValueFactory(new PropertyValueFactory<Satelite, String>(
					"diametro"));
			col7.setCellValueFactory(new PropertyValueFactory<Satelite, String>(
					"gravedad"));
		}
	}

	/**
	 * Cambia la morfologia de la tabla que se despliega a partir de una lista
	 * de planetas.
	 * 
	 * @param lista
	 *            Lista de planetas que recibe como par�metro, para mostrar en
	 *            la tabla.
	 */
	@SuppressWarnings("unchecked")
	private void setColumnasPlanetas(List<Planeta> lista) {
		// Se definen las columnas
		TableColumn<Planeta, String> col1 = new TableColumn<Planeta, String>(
				"Planeta");
		TableColumn<Planeta, String> col2 = new TableColumn<Planeta, String>(
				"Es enano");
		TableColumn<Planeta, String> col3 = new TableColumn<Planeta, String>(
				"Diametro");
		TableColumn<Planeta, String> col4 = new TableColumn<Planeta, String>(
				"Distancia");
		TableColumn<Planeta, String> col5 = new TableColumn<Planeta, String>(
				"Velocidad");

		for (int i = 0; i < lista.size(); i++) {
			// Se itera la lista para mostrar el resultado, en cada columna
			myTable.getColumns().setAll(col1, col2, col3, col4, col5);

			col1.setCellValueFactory(new PropertyValueFactory<Planeta, String>(
					"nombrePlaneta"));
			col2.setCellValueFactory(new PropertyValueFactory<Planeta, String>(
					"nan"));
			col3.setCellValueFactory(new PropertyValueFactory<Planeta, String>(
					"diamtero"));
			col4.setCellValueFactory(new PropertyValueFactory<Planeta, String>(
					"distancia"));
			col5.setCellValueFactory(new PropertyValueFactory<Planeta, String>(
					"velocidad"));
		}
	}

	/**
	 * M�todo que se ejecuta cuando se hace clic en el bot�n
	 * {@code ToggleButton NAN}.
	 * 
	 * @param event
	 *            Evento que se dispara al hacer clic.
	 */
	@FXML
	public void isNAN(ActionEvent event) {
		comprobarSiEsNAN();
	}

	/**
	 * Cuando se crea un planeta y se le define que es enano, todos los campos a
	 * rellenar ser�n obligatorios.
	 * 
	 * @return Retorna true si el bot�n {@code ToggleButton NAN} est�
	 *         seleccionado o false si no lo est�.
	 */
	private boolean comprobarSiEsNAN() {
		if (NAN.isSelected()) {
			velocidadPlaneta.setDisable(false);
			distanciaPlaneta.setDisable(false);
			diametroPlaneta.setDisable(false);
			isNANOk = true;
		} else {
			velocidadPlaneta.setDisable(true);
			distanciaPlaneta.setDisable(true);
			diametroPlaneta.setDisable(true);
			isNANOk = false;
		}
		return isNANOk;
	}

	/**
	 * M�todo que rellena una lista de planetas, a partir de una consulta a la
	 * BDD que hace el servicio de planetas.
	 * 
	 * @return Retorna una lista de planetas, para desplegarlos luego en un
	 *         combobox.
	 */
	private List<Planeta> desplegarPlanetas() {
		listaPlanetas = plaSrv.findAll();

		ObservableList<Planeta> opcionesPlanetas = FXCollections
				.observableArrayList(listaPlanetas);

		comboboxPlaneta.setItems(opcionesPlanetas);
		comboboxBuscarPlaneta.setItems(opcionesPlanetas);
		return opcionesPlanetas;
	}

	/**
	 * M�todo que rellena una lista de sat�lites, a partir de una consulta a la
	 * BDD que hace el servicio de sat�lites.
	 * 
	 * @return Retorna una lista de sat�lites, para desplegarlos luego en un
	 *         combobox.
	 */
	private List<Satelite> desplegarSatelites() {
		listaSatelites = satSrv.findAll();

		ObservableList<Satelite> opcionesSatelites = FXCollections
				.observableArrayList(listaSatelites);

		comboboxBuscarSatelite.setItems(opcionesSatelites);
		return opcionesSatelites;
	}

	/**
	 * M�todo que se dedica a vaciar variables como psic�pata, para
	 * reutilizarlos.
	 */
	private void clear() {
		nombrePlaneta.clear();
		NAN.setSelected(false);
		velocidadPlaneta.clear();
		distanciaPlaneta.clear();
		diametroPlaneta.clear();
		listaPlanetas.clear();
		listaSatelites.clear();
		nombreSatelite.clear();
		areaSuperficieSatelite.clear();
		masaSatelite.clear();
		densidadSatelite.clear();
		diametroSatelite.clear();
		gravedadSatelite.clear();
	}

	/**
	 * M�todo que muestra un Dialog de error.
	 * 
	 * @param msj
	 *            Par�metro para personalizar la salida del error en forma de
	 *            string.
	 */
	private void mensajeError(String msj) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error de entrada");
		alert.setHeaderText("Error");
		alert.setContentText(msj);

		alert.showAndWait();
	}

	/**
	 * M�todo que muestra un Dialog de informaci�n.
	 * 
	 * @param msj
	 *            Par�metro para personalizar la salida de informaci�n en forma
	 *            de string.
	 */
	private void ventanaOK(String msj) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(" Creado correctamente");
		alert.setHeaderText(null);
		alert.setContentText("�Felicidades! el " + msj
				+ " se ha creado correctamente");

		alert.showAndWait();
	}

	/**
	 * Ventana de di�logo que mustra informaci�n de su todopoderoso creador.
	 */
	private void infoAcerca() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Creador");
		alert.setHeaderText("Hola a quien lea esto");

		alert.setContentText("Mi nombre es Erick Pineda, m�s conocido"
				+ " en los bajos mundos como Jonh y en otras asignaturas"
				+ " como Jonh Erick, casualmente llamado solo Pineda;"
				+ " Destacando el hecho de que uniendolos, es mi nombre"
				+ " casi completo.");

		alert.showAndWait();
	}

	/**
	 * 
	 * @param event
	 *            Evento que se dispara, cuando se hace clic en salir.
	 */
	@FXML
	public void salir(ActionEvent event) {
		plaSrv.cerrarConexion();
		satSrv.cerrarConexion();
		System.exit(0);
	}
}
