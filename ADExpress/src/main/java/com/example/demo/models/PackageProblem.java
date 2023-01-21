package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name="package_problems")
public class PackageProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long package_problem_id;
    @OneToOne(cascade = CascadeType.ALL) // for relationship users_acc -> users_info (one to one)
    @JoinColumn(name = "package_id"/*,referencedColumnName="user_account_id", insertable=false, updatable=false*/)
    private Packages packages_problem;
    @Column(name="message",nullable = false)
    private String message;

    public PackageProblem(Long package_problem_id, Packages packages_problem, String message) {
        this.package_problem_id = package_problem_id;
        this.packages_problem = packages_problem;
        this.message = message;
    }

    public PackageProblem() {
    }

    public Long getPackage_problem_id() {
        return package_problem_id;
    }

    public void setPackage_problem_id(Long package_problem_id) {
        this.package_problem_id = package_problem_id;
    }

    public Packages getPackages_problem() {
        return packages_problem;
    }

    public void setPackages_problem(Packages packages_problem) {
        this.packages_problem = packages_problem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
