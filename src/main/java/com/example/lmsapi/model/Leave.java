package com.example.lmsapi.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "leaves")
public class Leave {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "emp_id")
    private Long empId;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "leaves_available", nullable = true)
    private int leavesAvailable;

    @Column(name = "leaves_used", nullable = true)
    private int leavesUsed;

    public Leave() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getLeavesAvailable() {
        return leavesAvailable;
    }

    public void setLeavesAvailable(int leavesAvailable) {
        this.leavesAvailable = leavesAvailable;
    }

    public int getLeavesUsed() {
        return leavesUsed;
    }

    public void setLeavesUsed(int leavesUsed) {
        this.leavesUsed = leavesUsed;
    }
}
