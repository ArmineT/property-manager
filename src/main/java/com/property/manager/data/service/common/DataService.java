package com.property.manager.data.service.common;

import com.property.manager.data.entity.common.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Date;

public interface DataService<T extends BaseEntity, ID extends Serializable> {
    JpaRepository<T, ID> repository();


    default T save(T object) {
        if (object.getCreatedAt() == null) {
            object.setCreatedAt(new Date());
        }
        return repository().save(object);
    }
}