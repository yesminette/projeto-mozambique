package br.ciar.domain.informativos.fotografia;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Configurable;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import br.ciar.domain.informativos.fotografia.Galeria;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
public class Foto implements Serializable {

    @PersistenceContext
    transient EntityManager entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_foto")
    private Integer id;

    @Size(max = 255)
    private String legenda;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date data_foto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_galeria")
    private Galeria galeria;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLegenda() {
        return this.legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public Date getData_foto() {
        return this.data_foto;
    }

    public void setData_foto(Date data_foto) {
        this.data_foto = data_foto;
    }

    public Galeria getGaleria() {
        return this.galeria;
    }

    public void setGaleria(Galeria galeria) {
        this.galeria = galeria;
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
            Foto attached = Foto.findFoto(this.id);
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
    public Foto merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        Foto merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static EntityManager entityManager() {
        EntityManager em = new Foto().entityManager;
        if (em == null) {
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        }
        return em;
    }

    public static long countFotoes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Foto o", Long.class).getSingleResult();
    }

    public static List<Foto> findAllFotoes() {
        return entityManager().createQuery("SELECT o FROM Foto o", Foto.class).getResultList();
    }

    public static Foto findFoto(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Foto.class, id);
    }

    public static List<Foto> findFotoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Foto o", Foto.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Data_foto: ").append(getData_foto()).append(", ");
        sb.append("Galeria: ").append(getGaleria()).append(", ");
        sb.append("Legenda: ").append(getLegenda());
        return sb.toString();
    }
}
