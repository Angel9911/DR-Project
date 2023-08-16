package com.example.demo.models.entity;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Entity
@Table(name = "administrator_office")
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADMINISTRATOR_SEQ")
    @SequenceGenerator(sequenceName = "administrator_id", allocationSize = 1, name = "ADMINISTRATOR_SEQ")
    private Long administrator_id;
    @OneToOne(cascade = CascadeType.ALL) // for relationship users_acc -> users_info (one to one)
    @JoinColumn(name = "administrator_acc_id",referencedColumnName="user_account_id"/*, insertable=false, updatable=false*/)
    private User_account user_account_administrator;
    @ManyToOne(fetch = FetchType.LAZY) // for relationship cities -> users_info (many to one)
    @JoinColumn(name = "office_id", referencedColumnName = "office_id", insertable = false, updatable = false)
    @CreatedDate
    private Office office_administrator;

    public Administrator(Long administrator_id, User_account user_account_administrator) {
        this.administrator_id = administrator_id;
        this.user_account_administrator = user_account_administrator;
    }

    public Administrator() {

    }

    public Long getAdministrator_id() {
        return administrator_id;
    }

    public void setAdministrator_id(Long administrator_id) {
        this.administrator_id = administrator_id;
    }

    public User_account getUser_account_administrator() {
        return user_account_administrator;
    }

    public void setUser_account_administrator(User_account user_account_administrator) {
        this.user_account_administrator = user_account_administrator;
    }

    public Office getOffice_administrator() {
        return office_administrator;
    }

    public void setOffice_administrator(Office office_administrator) {
        this.office_administrator = office_administrator;
    }
}
