package de.f0rce.preview.applist.views.previewapplist;

import com.vaadin.flow.component.dialog.Dialog;

public class PreviewApp {

	private String image;
	private String name;
	private String version;
	private String post;
	private String url;
	private Dialog links; // for later use

	public PreviewApp() {
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
