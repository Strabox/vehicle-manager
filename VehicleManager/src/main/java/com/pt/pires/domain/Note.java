package com.pt.pires.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.pt.pires.domain.exceptions.InvalidNoteException;

/**
 * Represents a simple note with a text description
 * @author André
 *
 */
@Entity
public class Note {
	
	@GeneratedValue
	@Id
	private long id;
	
	@Column
	private String description;
	
	public Note(String description) throws InvalidNoteException {
		setDescription(description);
	}
	
	public Note() { }	//Needed for JPA/JSON
	
	
	/* === Getters and Setters === */
	
	public long getId( ) {
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) throws InvalidNoteException {
		if(description.isEmpty()) {
			throw new InvalidNoteException();
		}
		this.description = description;
	}
	
}
