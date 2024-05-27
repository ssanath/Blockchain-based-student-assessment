package com.sanath.blockchain;
//import com.sanath.blockchain.BlockchainDemo;
import org.springframework.web.bind.annotation.*;

//import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    //private final BlockchainDemo blockchaindemo = new BlockchainDemo();
    private final Blockchain blockchain = new Blockchain();
    private final submissionChain submissions = new submissionChain();
    @PostMapping("/submit")
    public String submit(@RequestBody Submission submission) {
        submissions.addSubmission(submission);
        blockchain.assessSubmission(submission);
        return "Submission processed";
    }

    @GetMapping("/chain")
    public List<Block> getBlockchain() {
        return blockchain.getChain();
    }

     @GetMapping("/user")
    public List<Submission> getUserSubmissions() {
        return submissions.getChain();
    }
}