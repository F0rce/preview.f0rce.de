package de.f0rce.preview.applist.views.previewapplist;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@PageTitle("Preview App List")
@Route(value = "")
@Theme(themeFolder = "previewapplist", variant = Lumo.DARK)
public class PreviewAppListView extends Div implements AfterNavigationObserver {

	Grid<PreviewApp> grid = new Grid<>();

	public PreviewAppListView() {
		this.addClassName("preview-app-list-view");
		this.setSizeFull();
		this.grid.setHeight("100%");
		this.grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
		this.grid.addComponentColumn(app -> this.createCard(app));
		this.add(this.grid);
	}

	private HorizontalLayout createCard(PreviewApp previewApp) {
		HorizontalLayout card = new HorizontalLayout();
		card.addClassName("card");
		card.setSpacing(false);
		card.getThemeList().add("spacing-s");

		Image image = new Image();
		image.setSrc(previewApp.getImage());

		VerticalLayout description = new VerticalLayout();
		description.addClassName("description");
		description.setSpacing(false);
		description.setPadding(false);

		HorizontalLayout header = new HorizontalLayout();
		header.addClassName("header");
		header.setSpacing(false);
		header.getThemeList().add("spacing-s");

		Span name = new Span(previewApp.getName());
		name.addClassName("name");
		Span date = new Span(previewApp.getVersion());
		date.addClassName("version");
		header.add(name, date);

		Span post = new Span(previewApp.getPost());
		post.addClassName("post");

		description.add(header, post);
		card.add(image, description);
		return card;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {

		// Set some data when this view is displayed.
		List<PreviewApp> persons = Arrays.asList( //
				createApp(
						"https://static.vaadin.com/directory/user402520/icon/file6703307351609158308_1617024863123file456055440023195234_1579352673914ace-field-widget-screenshot-1_0.png",
						"Ace", "1.3.3", "Fast and Lightweight Ace Editor (https://ace.c9.io/) for Vaadin 14+",
						"https://preview.f0rce.de/ace"));

		this.grid.setItems(persons);

		this.grid.addItemClickListener(e -> {
			UI.getCurrent().getPage().setLocation(e.getItem().getUrl());
		});
	}

	private static PreviewApp createApp(String image, String name, String version, String post, String url) {
		PreviewApp app = new PreviewApp();
		app.setImage(image);
		app.setName(name);
		app.setVersion(version);
		app.setPost(post);
		app.setUrl(url);

		return app;
	}

}
