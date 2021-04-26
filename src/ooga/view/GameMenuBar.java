package ooga.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Populates the system default menu bar on MacOS devices.
 *
 * Detects the operating system, and if the system is MacOS, creates a menu bar with various
 * menus and options such as open, save, or open new window. Many of these options can
 * have keyboard combinations attached to them.
 *
 * This class extends {@code GameMenuBar} and will determine whether the system is Mac at
 * construction. Menus can then be added using the {@code String} names of the menu stored
 * in a {@code ResourceBundle}.
 */
public class GameMenuBar extends MenuBar {
  private final ResourceBundle menubarBundle;
  private static final String DEFAULT_RESOURCE_PACKAGE = "ooga.view.resources.";
  private static final String MENUBAR_BUTTONS_BUNDLE = "menuBar";
  private EventHandler<ActionEvent> handler;
  private final ArrayList<String> menusToMake;

  /**
   * Constructs an instance of a {@code GameMenuBar}, which has all menus described
   * in the {@code menuBar.properties} file. This constructor also needs an
   * {@code EventHandler} to connect buttons to their functionality.
   * @param handler an {@code EventHandler} for buttons to take effect.
   */
  public GameMenuBar(EventHandler<ActionEvent> handler){
    String os = System.getProperty("os.name");
    if (os != null && os.startsWith("Mac")) {
      Platform.runLater(() -> this.useSystemMenuBarProperty().set(true)) ;
    }

    menubarBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + MENUBAR_BUTTONS_BUNDLE);
    this.handler = handler;
    menusToMake = new ArrayList<String>(
        Arrays.asList(menubarBundle.getString("allMenus").split(",")));

    for (String menuName : menusToMake) {
      this.getMenus().add(makeMenuFromProperties(menuName));
    }
  }


  /**
   * Creates a {@code Menu} with all corresponding {@code MenuItem}s from a properties
   * file.
   *
   * An {@code String} input is needed for the name of the menu. This method will then
   * look in the properties file for all menu items for this menu, and add them as children
   * to this menu. This will invoke {@code makeMenuButton} to create menu item buttons as well
   * as their {@code EventHandler}.
   *
   * @param menuName name of the top level menu to create
   * @return a {@code Menu} object complete with menu items connected to event handlers.
   */
  private Menu makeMenuFromProperties(String menuName){
    String fileMenu = menubarBundle.getString(menuName + ".menu");
    Menu menu = null;
    LinkedList<String> menuItems = new LinkedList<String>(Arrays.asList(fileMenu.split(",")));
    menu = new Menu(menuName);
    while (menuItems.size() != 0) {
      MenuItem newMenuItem = makeMenuButton(menuItems.remove());
      menu.getItems().add(newMenuItem);
    }
    return menu;
  }

  /**
   * Creates a menu button using data from a resource bundle.
   *
   * The ID of the {@code MenuItem} created will also be used by reflection later to
   * invoke methods.
   *
   * @param property the property name of the button being created
   * @return a {@code MenuItem} with a label, an ID, and an event handler
   */
  private MenuItem makeMenuButton(String property) {
    MenuItem result = new MenuItem();
    String label = menubarBundle.getString(property);
    result.setText(label);
    result.setOnAction(handler);
    result.setId(property);
    return result;
  }

}
