package br.ciar.domain.ocorrencias;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Configurable;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import br.ciar.domain.informativos.Review;
import br.ciar.utils.DataUtil;
import java.util.ArrayList;
import java.util.Calendar;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Configurable
public class Ocorrencia implements Serializable {

    @PersistenceContext
    transient EntityManager entityManager;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_ocorrencia")
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date fim;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date inicio;
    @Column(name = "local_ocorrencia")
    @Size(max = 255)
    private String localOcorrencia;
    @Column(columnDefinition = "TEXT")
    private String mapa;
    @Size(max = 255)
    private String nome;
    @OneToOne
    @JoinColumn(name = "review_id_informativo")
    private Review review;
    @OneToMany(mappedBy = "ocorrencia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy(value = "hora desc")
    private List<Comentario> comentarios = new ArrayList<Comentario>();

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getFim() {
        return this.fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Date getInicio() {
        return this.inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public String getLocalOcorrencia() {
        return localOcorrencia;
    }

    public void setLocalOcorrencia(String localOcorrencia) {
        this.localOcorrencia = localOcorrencia;
    }

    public String getMapa() {
        return this.mapa;
    }

    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Review getReview() {
        return this.review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public List<Comentario> getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
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
            Ocorrencia attached = Ocorrencia.findOcorrencia(this.id);
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
    public Ocorrencia merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        Ocorrencia merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static EntityManager entityManager() {
        EntityManager em = new Ocorrencia().entityManager;
        if (em == null) {
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        }
        return em;
    }

    public static long countOcorrencias() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Ocorrencia o", Long.class).getSingleResult();
    }

    public static List<Ocorrencia> findAllOcorrencias() {
        return entityManager().createQuery("SELECT o FROM Ocorrencia o", Ocorrencia.class).getResultList();
    }

    public static List<Ocorrencia> findAllOcorrenciasOrdered(String campo, String order) {
        String ordenacao = (campo.isEmpty() || order.isEmpty()) ? " inicio DESC " : campo + " " + order;
        return entityManager().createQuery("SELECT o FROM Ocorrencia o ORDER BY " + ordenacao, Ocorrencia.class).getResultList();
    }

    public static Ocorrencia findOcorrencia(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Ocorrencia.class, id);
    }

    public static List<Ocorrencia> findOcorrenciaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Ocorrencia o ORDER BY o.inicio DESC, o.fim DESC", Ocorrencia.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Ocorrencia> getOcorrencias(int limite) {
        return findOcorrenciaEntries(0, limite);
    }

    public static List<Ocorrencia> getOcorrencias(int limite, int mes, int ano) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, DataUtil.getMes(mes), 1);

        Date dataIni = calendar.getTime();

        calendar.set(ano, DataUtil.getMes(mes), calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date dataFim = calendar.getTime();

        String query = "SELECT o FROM Ocorrencia o WHERE o.inicio BETWEEN :inicio AND :fim OR o.fim BETWEEN :inicio AND :fim ORDER BY o.inicio DESC, o.fim DESC";

        return entityManager().createQuery(query, Ocorrencia.class).setParameter("inicio", dataIni).setParameter("fim", dataFim).setFirstResult(0).setMaxResults(limite).getResultList();
    }

    public static List<Comentario> getComentarios(int idOcorrencia, int limite) {
        return entityManager().createQuery("SELECT o FROM Comentario o WHERE o.ocorrencia.id=" + idOcorrencia + " ORDER BY o.hora DESC", Comentario.class).setFirstResult(0).setMaxResults(limite).getResultList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comentarios: ").append(getComentarios() == null ? "null" : getComentarios().size()).append(", ");
        sb.append("Descricao: ").append(getDescricao()).append(", ");
        sb.append("Fim: ").append(getFim()).append(", ");
        sb.append("Inicio: ").append(getInicio()).append(", ");
        sb.append("Local_ocorrencia: ").append(getLocalOcorrencia()).append(", ");
        sb.append("Mapa: ").append(getMapa()).append(", ");
        sb.append("Nome: ").append(getNome()).append(", ");
        sb.append("Review: ").append(getReview());
        return sb.toString();
    }
}
