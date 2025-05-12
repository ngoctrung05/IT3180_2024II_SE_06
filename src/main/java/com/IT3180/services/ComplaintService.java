package com.IT3180.services;

import com.IT3180.model.Complaint;
import com.IT3180.repository.ComplaintRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintService {
    @Autowired
    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository){
        this.complaintRepository = complaintRepository;
    }

    public void saveComplaint(Complaint complaint){
        complaintRepository.save(complaint);
    }

    public List<Complaint> getAllComplaints(){
        return complaintRepository.findAll();
    }

    public List<Complaint> getComplaintByType(String type){ return complaintRepository.findByType(type);}

    public boolean delete(Long id){
        if(complaintRepository.existsById(id)){
            complaintRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void changeComplaintType(Complaint complaint, String type){
        complaint.setType(type);
        complaintRepository.save(complaint);
    }
}
