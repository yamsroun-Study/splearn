package yamsroun.splearnself.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.proxy.HibernateProxy;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@MappedSuperclass
@ToString
public abstract class AbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(onMethod_ = {@Nullable})
    private Long id;

    @SuppressWarnings("squid:S620")
    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer() //NOSONAR
            .getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer() //NOSONAR
            .getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode(); //NOSONAR
    }
}
