package com.example.lmsapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "leaves")
public class Leave {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "emp_id")
    private Long empId;

    @NotNull
    @Column(name = "from_date")
    private Date fromDate;

    @NotNull
    @Column(name = "to_date")
    private Date toDate;

    @NotNull
    @Column(name = "reason")
    private String reason;

    @Column(name = "leaves_available")
    private Integer leavesAvailable;

    @Column(name = "leaves_used")
    private Integer leavesUsed;

    @Column(name = "leave_status", nullable = true)
    private String leaveStatus = "Pending";

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

    public Integer getLeavesAvailable() {
        return leavesAvailable;
    }

    public void setLeavesAvailable(int leavesAvailable) {
        this.leavesAvailable = leavesAvailable;
    }

    public Integer getLeavesUsed() {
        return leavesUsed;
    }

    public void setLeavesUsed(int leavesUsed) {
        this.leavesUsed = leavesUsed;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
}
