package org.needit.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.needit.model.Annonce;

/**
 * Backing bean for Annonce entities.
 * <p/>
 * This class provides CRUD functionality for all Annonce entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class AnnonceBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Annonce entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Annonce annonce;

	public Annonce getAnnonce() {
		return this.annonce;
	}

	public void setAnnonce(Annonce annonce) {
		this.annonce = annonce;
	}

	@Inject
	private Conversation conversation;

	@PersistenceContext(unitName = "needit-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public String create() {

		this.conversation.begin();
		this.conversation.setTimeout(1800000L);
		return "create?faces-redirect=true";
	}

	public void retrieve() {

		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		if (this.conversation.isTransient()) {
			this.conversation.begin();
			this.conversation.setTimeout(1800000L);
		}

		if (this.id == null) {
			this.annonce = this.example;
		} else {
			this.annonce = findById(getId());
		}
	}

	public Annonce findById(Long id) {

		return this.entityManager.find(Annonce.class, id);
	}

	/*
	 * Support updating and deleting Annonce entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.annonce);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.annonce);
				return "view?faces-redirect=true&id=" + this.annonce.getId();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public String delete() {
		this.conversation.end();

		try {
			Annonce deletableEntity = findById(getId());

			this.entityManager.remove(deletableEntity);
			this.entityManager.flush();
			return "search?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching Annonce entities with pagination
	 */

	private int page;
	private long count;
	private List<Annonce> pageItems;

	private Annonce example = new Annonce();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Annonce getExample() {
		return this.example;
	}

	public void setExample(Annonce example) {
		this.example = example;
	}

	public String search() {
		this.page = 0;
		return null;
	}

	public void paginate() {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Annonce> root = countCriteria.from(Annonce.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Annonce> criteria = builder.createQuery(Annonce.class);
		root = criteria.from(Annonce.class);
		TypedQuery<Annonce> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Annonce> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String titre = this.example.getTitre();
		if (titre != null && !"".equals(titre)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("titre")),
					'%' + titre.toLowerCase() + '%'));
		}
		String description = this.example.getDescription();
		if (description != null && !"".equals(description)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("description")),
					'%' + description.toLowerCase() + '%'));
		}
		String etat_item = this.example.getEtat_item();
		if (etat_item != null && !"".equals(etat_item)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("etat_item")),
					'%' + etat_item.toLowerCase() + '%'));
		}
		String photo_item_1 = this.example.getPhoto_item_1();
		if (photo_item_1 != null && !"".equals(photo_item_1)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("photo_item_1")),
					'%' + photo_item_1.toLowerCase() + '%'));
		}
		String photo_item_2 = this.example.getPhoto_item_2();
		if (photo_item_2 != null && !"".equals(photo_item_2)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("photo_item_2")),
					'%' + photo_item_2.toLowerCase() + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<Annonce> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Annonce entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Annonce> getAll() {

		CriteriaQuery<Annonce> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Annonce.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Annonce.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final AnnonceBean ejbProxy = this.sessionContext
				.getBusinessObject(AnnonceBean.class);

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				return ejbProxy.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((Annonce) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Annonce add = new Annonce();

	public Annonce getAdd() {
		return this.add;
	}

	public Annonce getAdded() {
		Annonce added = this.add;
		this.add = new Annonce();
		return added;
	}
}
