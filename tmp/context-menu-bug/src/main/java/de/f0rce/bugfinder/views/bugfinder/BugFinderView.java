package de.f0rce.bugfinder.views.bugfinder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.vaadin.firitin.components.DynamicFileDownloader;

import com.helger.commons.csv.CSVWriter;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@PWA(name = "Bug Finder", shortName = "Bug Finder", enableInstallPrompt = false)
@Theme(themeFolder = "bugfinder", variant = Lumo.DARK)
@PageTitle("Bug Finder")
@Route(value = "")
public class BugFinderView extends Div {

	private ArrayList<Person> items = new ArrayList<>();
	private int version = 0;

	public BugFinderView() {
		addClassNames("bug-finder-view");

		ArrayList<Person> personList = new ArrayList<>();

		for (int i = 1; i < 100001; i++) {
			personList.add(new Person(i, "David", "Dodlek", 19, new Address("12346", "GitHub"), "123-456-89" + i));
		}

		Grid<Person> grid = new Grid<>(Person.class);

		grid.setItems(personList);
		this.items = personList;

		grid.removeColumnByKey("id");
		grid.setColumns("firstName", "lastName", "age", "address", "phoneNumber");

		GridContextMenu<Person> gcm = grid.addContextMenu();

		gcm.setDynamicContentHandler(new SerializablePredicate<Person>() {

			@Override
			public boolean test(Person t) {
				gcm.removeAll();
				version++;
				gcm.add(createWorkingFileDownloader(grid));
				gcm.add(createWorkingAnchor(grid));

				GridMenuItem<Person> mI = gcm.addItem(createNotWorkingFileDownloader(grid));
				gcm.addItem(createNotWorkingAnchor(grid));
				return true;
			}
		});

		add(grid);
	}

	private Anchor createNotWorkingAnchor(Grid<Person> g) {
		return new Anchor(new StreamResource("ATestExport_" + UUID.randomUUID().toString().substring(0, 15) + ".csv",
				() -> createInputStream(g)), "Export CSV (Anchor) - Wierd Behaviour (" + version + ")");
	}

	private DynamicFileDownloader createNotWorkingFileDownloader(Grid<Person> g) {
		return new DynamicFileDownloader("Export CSV (DFD) - Wierd Behaviour (" + version + ")",
				"DFDTestExport_" + UUID.randomUUID().toString().substring(0, 15) + ".csv", output -> {
					try {
						byte[] bytes = IOUtils.toByteArray(createInputStream(g));
						output.write(bytes);
					} catch (Exception e) {
						Notification.show(e.getMessage());
					}
				});
	}

	private Anchor createWorkingAnchor(Grid<Person> g) {
		return new Anchor(new StreamResource("ATestExport_" + UUID.randomUUID().toString().substring(0, 15) + ".csv",
				() -> createInputStream(g)), "Export CSV (Anchor) - Working (" + version + ")");
	}

	private DynamicFileDownloader createWorkingFileDownloader(Grid<Person> g) {
		return new DynamicFileDownloader("Export CSV (DFD) - Working (" + version + ")",
				"DFDTestExport_" + UUID.randomUUID().toString().substring(0, 15) + ".csv", output -> {
					try {
						byte[] bytes = IOUtils.toByteArray(createInputStream(g));
						output.write(bytes);
					} catch (Exception e) {
						Notification.show(e.getMessage());
					}
				});
	}

	private InputStream createInputStream(Grid<Person> g) {
		StringWriter stringWriter = new StringWriter();
		CSVWriter csvWriter = new CSVWriter(stringWriter);
		try {
			ArrayList<String> arrColKeys = new ArrayList<>();
			for (Column<Person> col : g.getColumns()) {
				if (isNullOrEmpty(col.getKey()) || !col.isVisible()) {
					continue;
				}
				arrColKeys.add(col.getKey());
			}
			csvWriter.writeNext(arrColKeys); // header
			this.items.forEach(item -> {
				ArrayList<String> arrValues = new ArrayList<>();
				for (String colKey : arrColKeys) {
					arrValues.add(getFormattedValue(item, colKey));
				}
				csvWriter.writeNext(arrValues);
			});
			return IOUtils.toInputStream(stringWriter.toString(), "UTF-8");
		} catch (Exception e) {
			return null;
		} finally {
			try {
				csvWriter.close();
			} catch (IOException e) {
			}
		}
	}

	private static boolean isNullOrEmpty(String inString) {
		return trimLength(inString) == 0;
	}

	private static int trimLength(final String s) {
		return (s == null) ? 0 : s.trim().length();
	}

	private static String getFormattedValue(Person inItem, String inColKey) {
		Object value;
		try {
			value = PropertyUtils.getProperty(inItem, inColKey);
			if (value == null) {
				return "";
			} else if (value instanceof Address) {
				return value.toString();
			} else {
				return value.toString();
			}
		} catch (Exception e) {
		}
		return "";
	}

}
