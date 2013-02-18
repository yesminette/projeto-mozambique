package br.ciar.domain.ocorrencias;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Configurable;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import br.ciar.domain.ocorrencias.Ocorrencia;
import javax.persistence.ManyToOne;
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
public class Comentario implements Serializable {

    @PersistenceContext
    transient EntityManager entityManager;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_comentario")
    private Integer id;
    @Size(max = 255)
    private String conteudo;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date hora;
    @NotNull
    @Size(max = 255)
    private String nome;
    @NotNull
    @Size(max = 30)
    private String enderecoip;
    @ManyToOne
    @JoinColumn(name = "id_ocorrencia")
    private Ocorrencia ocorrencia;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getHora() {
        return this.hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEnderecoip() {
        return this.enderecoip;
    }

    public void setEnderecoip(String enderecoip) {
        this.enderecoip = enderecoip;
    }

    public Ocorrencia getOcorrencia() {
        return this.ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
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
            Comentario attached = Comentario.findComentario(this.id);
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
    public Comentario merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        Comentario merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static EntityManager entityManager() {
        EntityManager em = new Comentario().entityManager;
        if (em == null) {
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        }
        return em;
    }

    public static long countComentarios() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Comentario o", Long.class).getSingleResult();
    }

    public static List<Comentario> findAllComentarios() {
        return entityManager().createQuery("SELECT o FROM Comentario o", Comentario.class).getResultList();
    }

    public static Comentario findComentario(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Comentario.class, id);
    }

    public static List<Comentario> findComentarioEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Comentario o", Comentario.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Conteudo: ").append(getConteudo()).append(", ");
        sb.append("Enderecoip: ").append(getEnderecoip()).append(", ");
        sb.append("Hora: ").append(getHora()).append(", ");
        sb.append("Nome: ").append(getNome()).append(", ");
        sb.append("Ocorrencia: ").append(getOcorrencia());
        return sb.toString();
    }
}