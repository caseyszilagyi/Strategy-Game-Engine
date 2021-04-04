package ooga.view;

import java.util.ResourceBundle;

public abstract class AbstractViewPaneFactory {

  private ResourceBundle resources;
  public AbstractViewPaneFactory(ResourceBundle resources){
    this.resources = resources;
  }

  public abstract ViewPane createPane();

}
