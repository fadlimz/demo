package com.fadli.demo.base.parentClasses;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "VERSION")
    private Long version;

    @PrePersist
    private void prePersist() {
        this.id = UUID.randomUUID().toString();
        this.version = 0L;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
