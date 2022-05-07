package com.sip.creauters.batchprocessing.batchprocess.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
  
@Entity
@Table(name = "batch_process")
public class BatchProcess {  
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
    
    @Column(name = "name", nullable = false)
    private String name;  
    
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;
    
    @Column(name = "is_running", nullable = false)
    private boolean isRunning = false;

    public BatchProcess() {
    }

    public BatchProcess(Long id, String name, boolean completed, boolean isRunning) {
        this.id = id;
        this.name = name;
        this.isCompleted = completed;
        this.isRunning = isRunning;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsCompleted() { 
        return this.isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        this.isCompleted = completed;
    }
    
    public boolean getIsRunning() { 
        return this.isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
