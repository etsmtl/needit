package org.needit.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Annonce implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private String titre;

	@Column
	private String description;

	@Column
	private String etat_item;

	@Column
	private boolelean disponiblite;

	@Column
	private String photo_item_1;

	@Column
	private String photo_item_2;

	@Column
	private boolelean animaux;

	@Column
	private boolelean fumeur;

	@Column
	private String secteur;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Annonce)) {
			return false;
		}
		Annonce other = (Annonce) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEtat_item() {
		return etat_item;
	}

	public void setEtat_item(String etat_item) {
		this.etat_item = etat_item;
	}

	public boolelean getDisponiblite() {
		return disponiblite;
	}

	public void setDisponiblite(boolelean disponiblite) {
		this.disponiblite = disponiblite;
	}

	public String getPhoto_item_1() {
		return photo_item_1;
	}

	public void setPhoto_item_1(String photo_item_1) {
		this.photo_item_1 = photo_item_1;
	}

	public String getPhoto_item_2() {
		return photo_item_2;
	}

	public void setPhoto_item_2(String photo_item_2) {
		this.photo_item_2 = photo_item_2;
	}

	public boolelean getAnimaux() {
		return animaux;
	}

	public void setAnimaux(boolelean animaux) {
		this.animaux = animaux;
	}

	public boolelean getFumeur() {
		return fumeur;
	}

	public void setFumeur(boolelean fumeur) {
		this.fumeur = fumeur;
	}

	public String getSecteur() {
		return secteur;
	}

	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (titre != null && !titre.trim().isEmpty())
			result += "titre: " + titre;
		if (description != null && !description.trim().isEmpty())
			result += ", description: " + description;
		if (etat_item != null && !etat_item.trim().isEmpty())
			result += ", etat_item: " + etat_item;
		if (photo_item_1 != null && !photo_item_1.trim().isEmpty())
			result += ", photo_item_1: " + photo_item_1;
		if (photo_item_2 != null && !photo_item_2.trim().isEmpty())
			result += ", photo_item_2: " + photo_item_2;
		if (secteur != null && !secteur.trim().isEmpty())
			result += ", secteur: " + secteur;
		return result;
	}
}