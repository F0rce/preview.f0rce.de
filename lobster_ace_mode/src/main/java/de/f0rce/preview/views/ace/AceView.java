package de.f0rce.preview.views.ace;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import de.f0rce.ace.AceEditor;
import de.f0rce.ace.enums.AceMode;
import de.f0rce.ace.enums.AceTheme;

@PageTitle("ace")
@Route(value = "")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class AceView extends VerticalLayout {

	public AceView() {
		this.addClassName("ace-view");

		ContextMenu contextMenu = new ContextMenu();

		// Ace

		AceEditor ace = new AceEditor();
		ace.setTheme(AceTheme.idle_fingers);
		ace.setMode(AceMode.lobster_logs);
		ace.setLiveAutocompletion(true);
		ace.setAutoComplete(true);

		ace.setValue("paste log here");

		contextMenu.setTarget(ace);
		contextMenu.addItem("Version: 1.3.4-Lobster");

		// Comboboxes

		HorizontalLayout layoutComboBoxes = new HorizontalLayout();

		ComboBox<AceTheme> themesComboBox = new ComboBox<>();
		themesComboBox.setItems(AceTheme.values());
		themesComboBox.setLabel("Themes");
		themesComboBox.setValue(ace.getTheme());

		themesComboBox.addValueChangeListener(event -> {
			if (!event.isFromClient())
				return;

			if (event.getValue() != null) {
				ace.setTheme(event.getValue());
			}
		});

		ComboBox<AceMode> modesComboBox = new ComboBox<>();
		modesComboBox.setItems(AceMode.lobster_logs);
		modesComboBox.setLabel("Modes");
		modesComboBox.setValue(ace.getMode());

		modesComboBox.addValueChangeListener(event -> {
			if (!event.isFromClient())
				return;

			if (event.getValue() != null) {
				ace.setMode(event.getValue());
			}
		});

		layoutComboBoxes.add(themesComboBox, modesComboBox);

		this.add(layoutComboBoxes);
		this.addAndExpand(ace);
	}

}
