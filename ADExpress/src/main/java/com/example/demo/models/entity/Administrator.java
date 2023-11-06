package com.example.demo.models.entity;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Entity
@Table(name = "administrator_office")
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADMINISTRATOR_SEQ")
    @SequenceGenerator(sequenceName = "administrator_id", allocationSize = 1, name = "ADMINISTRATOR_SEQ")
    @Column(name = "administrator_id")
    private Long adminId;
    @OneToOne(cascade = CascadeType.ALL) // for relationship users_acc -> users_info (one to one)
    @JoinColumn(name = "administrator_acc_id",referencedColumnName="user_account_id"/*, insertable=false, updatable=false*/)
    private User_account accountAdmin;
    @ManyToOne(fetch = FetchType.LAZY) // for relationship cities -> users_info (many to one)
    @JoinColumn(name = "office_id", referencedColumnName = "office_id", insertable = false, updatable = false)
    @CreatedDate
    private Office office_administrator;

    public Administrator(Long administrator_id, User_account accountAdmin) {
        this.adminId = administrator_id;
        this.accountAdmin = accountAdmin;
    }

    public Administrator() {

    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long administrator_id) {
        this.adminId = administrator_id;
    }

    public User_account getAccountAdmin() {
        return accountAdmin;
    }

    public void setAccountAdmin(User_account accountAdmin) {
        this.accountAdmin = accountAdmin;
    }

    public Office getOffice_administrator() {
        return office_administrator;
    }

    public void setOffice_administrator(Office office_administrator) {
        this.office_administrator = office_administrator;
    }
}
