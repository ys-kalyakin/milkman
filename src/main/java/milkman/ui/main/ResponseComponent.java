package milkman.ui.main;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.jfoenix.controls.JFXTabPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import milkman.domain.ResponseAspect;
import milkman.domain.ResponseContainer;
import milkman.ui.plugin.RequestAspectsPlugin;
import milkman.ui.plugin.ResponseAspectEditor;
import milkman.ui.plugin.UiPluginManager;

@Singleton
public class ResponseComponent implements Initializable {

	private final UiPluginManager plugins;

	@FXML JFXTabPane tabs;

	@FXML Node spinner;

	
	@Inject
	public ResponseComponent(UiPluginManager plugins) {
		this.plugins = plugins;
	}


	public void display(ResponseContainer response) {
		clear();
		hideSpinner();
		for (RequestAspectsPlugin plugin : plugins.loadRequestAspectPlugins()) {
			for (ResponseAspect aspect : response.getAspects()) {
				for (ResponseAspectEditor tabController : plugin.getResponseTabs()) {
					if (tabController.canHandleAspect(aspect)) {
						Tab aspectTab = tabController.getRoot(aspect);
						aspectTab.setClosable(false);
						tabs.getTabs().add(aspectTab);
					}
				}
			}
		}
	}


	public void clear() {
		tabs.getTabs().clear();
	}
	
	public void showSpinner() {
		spinner.setVisible(true);
	}


	public void hideSpinner() {
		spinner.setVisible(false);
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hideSpinner();
	}
}
