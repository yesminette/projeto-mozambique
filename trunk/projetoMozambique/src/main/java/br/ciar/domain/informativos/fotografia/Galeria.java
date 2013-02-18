package br.ciar.domain.informativos.fotografia;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Configurable;
import br.ciar.domain.informativos.Noticia;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class Galeria implements Serializable {

    @PersistenceContext
    transient EntityManager entityManager;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_galeria")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy="galeria")
    private Noticia noticia;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "galeria")
    private Set<Foto> fotos = new HashSet<Foto>();

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Noticia getNoticia() {
        return this.noticia;
    }

    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }

    public Set<Foto> getFotos() {
        return this.fotos;
    }

    public void setFotos(Set<Foto> fotos) {
        this.fotos = fotos;
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.persist(this);
    }

    @Transactional
    public void remove() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Galeria attached = Galeria.findGaleria(this.id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.flush();
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.clear();
    }

    @Transactional
    public Galeria merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        Galeria merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static EntityManager entityManager() {
        EntityManager em = new Galeria().entityManager;
        if (em == null) {
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        }
        return em;
    }

    public static long countGalerias() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Galeria o", Long.class).getSingleResult();
    }

    public static List<Galeria> findAllGalerias() {
        return entityManager().createQuery("SELECT o FROM Galeria o", Galeria.class).getResultList();
    }

    public static Galeria findGaleria(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Galeria.class, id);
    }

    public static List<Galeria> findGaleriaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Galeria o", Galeria.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fotos: ").append(getFotos() == null ? "null" : getFotos().size()).append(", ");
        sb.append("Noticia: ").append(getNoticia());
        return sb.toString();
    }
}
