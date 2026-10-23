package pe.edu.upeu.sysventas.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import pe.edu.upeu.sysventas.config.AppContext;
import pe.edu.upeu.sysventas.dto.MenuMenuItenTO;
import pe.edu.upeu.sysventas.dto.SessionManager;
import pe.edu.upeu.sysventas.service.IMenuMenuItemDao;
import pe.edu.upeu.sysventas.utils.UtilsX;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;

@RequiredArgsConstructor
public class MainguiController {
    @FXML
    BorderPane bp;
    @FXML
    MenuBar menuBar;

    /*@FXML
    MenuItem menuItem1, menuItem2;*/

    @FXML
    TabPane tabPane;

    private final IMenuMenuItemDao mmiDao;
    private Parent parent;
    Stage stage;
    Preferences userPrefs = Preferences.userRoot();
    UtilsX util = new UtilsX();
    Properties myresources = new Properties();
    List<MenuMenuItenTO> lista;

    @FXML private Menu menuEstilo = new Menu("Cambiar Estilo");
    ComboBox<String> comboBoxEstilo = new ComboBox<>(
            FXCollections.observableArrayList(
                    "Estilo por Defecto", "Estilo Oscuro",
                    "Estilo Azul", "Estilo Verde", "Estilo Rosado"));

    CustomMenuItem customItem = new CustomMenuItem(comboBoxEstilo);
    @FXML private Menu menuIdioma = new Menu("Idioma");
    ComboBox<String> comboBoxIdioma = new ComboBox<>(
            FXCollections.observableArrayList("Español", "Ingles"));
    CustomMenuItem customItemIdioma = new CustomMenuItem(comboBoxIdioma);


    @FXML
    public void initialize(){
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        pause.setOnFinished(e -> {
            if (tabPane.getScene() != null && tabPane.getScene().getWindow() != null) {
                stage = (Stage) tabPane.getScene().getWindow();
            }
        });
        pause.play();
        graficarMenus();
        bp.setCenter(tabPane);

    }

    private List<MenuMenuItenTO> listaAccesos() {
        myresources = util.detectLanguage(userPrefs.get("IDIOMAX", "es"));
        return mmiDao.listaAccesos(SessionManager.getInstance().getUserPerfil(), myresources);
    }

    private void cambiarEstilo() {
        String estiloSeleccionado = comboBoxEstilo.getSelectionModel().getSelectedItem();
        Scene escena = bp.getScene();
        escena.getStylesheets().clear();
        switch (estiloSeleccionado) {
            case "Estilo Oscuro":
                escena.getStylesheets().add(getClass().getResource("/css/estilo-oscuro.css").toExternalForm()); break;
            case "Estilo Azul":
                escena.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm()); break;
            case "Estilo Verde":
                escena.getStylesheets().add(getClass().getResource("/css/estilo-verde.css").toExternalForm()); break;
            case "Estilo Rosado":
                escena.getStylesheets().add(getClass().getResource("/css/estilo-rosado.css").toExternalForm()); break;
            default: break;
        }
    }


    private void cambiarIdioma() {
        String idiomaSeleccionado = comboBoxIdioma.getSelectionModel().getSelectedItem();
        switch (idiomaSeleccionado) {
            case "Español": userPrefs.put("IDIOMAX", "es"); break;
            case "Ingles": userPrefs.put("IDIOMAX", "en"); break;
            default: userPrefs.put("IDIOMAX", "es"); break;
        }
        graficarMenus();
    }


    private void graficarMenus() {
        lista = listaAccesos();
        int[] mmi = mmiDao.contarMenuMunuItem(lista);
        Menu[] menu = new Menu[mmi[0]];
        MenuItem[] menuItem = new MenuItem[mmi[1]];
        menuBar = new MenuBar();
        MenuItemListener d = new MenuItemListener();
        //MenuListener m = new MenuListener();
        String menuN = "";
        int menui = 0, menuitem = 0;
        char conti = 'N';
        for (MenuMenuItenTO mmix : lista) {
            if (!mmix.getMenunombre().equals(menuN)) {
                menu[menui] = new Menu(mmix.getMenunombre());
                menu[menui].setId("m" + mmix.getIdNombreObj());
                //menu[menui].setOnShowing(m::menuSelected);
                if (!mmix.getMenuitemnombre().equals("")) {
                    menuItem[menuitem] = new MenuItem(mmix.getMenuitemnombre());
                    menuItem[menuitem].setId("mi" + mmix.getIdNombreObj());
                    menuItem[menuitem].setOnAction(d::handle);
                    menu[menui].getItems().add(menuItem[menuitem]);
                    menuitem++;
                }
                menuBar.getMenus().add(menu[menui]);
                menuN = mmix.getMenunombre();
                conti = 'N';
                menui++;
            } else {
                conti = 'S';
            }
            if (!mmix.getMenuitemnombre().equals("") && mmix.getMenunombre().equals(menuN) && conti == 'S') {
                menuItem[menuitem] = new MenuItem(mmix.getMenuitemnombre());
                menuItem[menuitem].setId("mi" + mmix.getIdNombreObj());
                menuItem[menuitem].setOnAction(d::handle);
                menu[menui - 1].getItems().add(menuItem[menuitem]);
                menuitem++;
            }
        }
        comboBoxEstilo.setOnAction(e -> cambiarEstilo());
        customItem.setHideOnClick(false);
        menuEstilo.getItems().clear();
        menuEstilo.getItems().add(customItem);
        menuBar.getMenus().addAll(menuEstilo);
        comboBoxIdioma.setOnAction(e -> cambiarIdioma());
        customItemIdioma.setHideOnClick(false);
        menuIdioma.getItems().clear();
        menuIdioma.getItems().add(customItemIdioma);
        menuBar.getMenus().addAll(menuIdioma);
        bp.setTop(menuBar);
    }

    class MenuItemListener{

        Map<String, String[]> menuConfig;
        MenuItemListener() {
            menuConfig = mmiDao.accesosAutorizados(lista);
        }

        private void redireccionar(String fxmlPath) {
            tabPane.getTabs().clear();
            try {
                AppContext ctx = AppContext.getInstance();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
                fxmlLoader.setControllerFactory(clazz -> ctx.getBean(clazz));
                parent = fxmlLoader.load();
                Scene scene = new Scene(parent);
                stage.sizeToScene();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("SysVentas SysCenterLife");
                stage.setResizable(false);
                stage.show();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }



        public void handle(ActionEvent e){
            String id=((MenuItem)e.getSource()).getId();
            if(menuConfig.containsKey(id)){
                String[] items=menuConfig.get(id);
                if(items[2].equals("C")){
                    //Platform.exit();
                    //System.exit(0);
                    redireccionar(items[0]);
                }else{
                    abrirTabPaneFXML(items[0],items[1]);
                }
            }
        }

        private void abrirTabPaneFXML(String fxmlPath, String tittle){
            try {
                AppContext context = AppContext.getInstance();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
                fxmlLoader.setControllerFactory(context::getBean);
                Parent root = fxmlLoader.load();

                ScrollPane scrollPane = new ScrollPane(root);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);
                Tab newTab = new Tab(tittle, scrollPane);
                tabPane.getTabs().clear();
                tabPane.getTabs().add(newTab);
            }catch (IOException ex){
                throw new RuntimeException(ex);
            }
        }
    }

}
