package com.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.common.enums.TableStatus;
import jakarta.persistence.OneToMany;
import java.util.List;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "tables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableEntity extends BaseEntity {
    @Column(name = "table_number", unique = true)
    private Integer tableNumber;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "status")
    private TableStatus status;
    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    private List<ReservationEntity> reservations;
    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;
}
