package com.dianer.domain.editor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


public class Model extends AbstractModel {

	private byte[] thumbnail;
	
	public Model() {
		super();
	}
	
	public byte[] getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}
	
}