package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> Item. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DynamicInsert
// TODO: 6. Dynamic Insert
public class Item {

    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 이름.
     */
    private String name;

    /**
     * 설명.
     */
    private String description;

    /**
     * Owner.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    /**
     * manager.
     */
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    /**
     * status.
     */
    @Column(nullable = false, columnDefinition = "varchar(20) default 'PENDING'")
    private String status;

    /**
     * 생성자.
     *
     * @param name 이름
     * @param description 설명
     * @param manager manager
     * @param owner owner
     */
    public Item(String name, String description, User manager, User owner) {
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.owner = owner;
    }
}
