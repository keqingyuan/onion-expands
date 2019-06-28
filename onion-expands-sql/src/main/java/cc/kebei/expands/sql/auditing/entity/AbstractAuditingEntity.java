package cc.kebei.expands.sql.auditing.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by qingyuan on 2019/6/25.
 */
public abstract class AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String createdBy;

    private Timestamp createdDate = Timestamp.from(Instant.now());

    private String lastModifiedBy;

    private Timestamp lastModifiedDate = Timestamp.from(Instant.now());

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
